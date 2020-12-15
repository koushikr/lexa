package io.github.lexa.core.services.impl;

/*
 * Copyright 2020 Koushik R <rkoushik.14@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import io.github.jforrest.Edge;
import io.github.lexa.core.commands.CategoryCommands;
import io.github.lexa.core.exceptions.LexaCode;
import io.github.lexa.core.exceptions.LexaException;
import io.github.lexa.core.schema.mapping.MappingDefinition;
import io.github.lexa.core.schema.profiles.CategoryProfile;
import io.github.lexa.core.schema.profiles.EntityStatus;
import io.github.lexa.core.schema.profiles.EntityType;
import io.github.lexa.core.services.CategoryService;
import io.github.lexa.core.utils.LexaUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import javax.inject.Singleton;
import java.util.Collections;
import java.util.HashSet;

@Slf4j @Singleton
public class CategoryServiceImpl implements CategoryService{

    private final CategoryCommands categoryCommands;

    public CategoryServiceImpl(CategoryCommands categoryCommands){
        this.categoryCommands = categoryCommands;
    }

    private void markMapped(CategoryProfile existingProfile) throws Exception {
        existingProfile.setMapped(true);
        categoryCommands.updateCategory(existingProfile);
    }

    @Override
    public void createCategoryContainment(String categoryGroup, String categoryId) {
        val existingProfile = categoryCommands.categoryCheck(categoryGroup, categoryId);

        if (existingProfile.isMapped()) {
            throw LexaException.error(LexaCode.CATEGORY_SCHEMA_EXISTS,
                    Collections.singletonMap(LexaUtils.Keys.MESSAGE_STRING, "Category schema already exists."));
        }

        categoryCommands.createIndex(categoryGroup, categoryId);
    }

    @Override
    public CategoryProfile createCategory(CategoryProfile inCategoryProfile) throws Exception {
        val existingProfile = categoryCommands.getProfile(inCategoryProfile.getCategoryGroup(),
                inCategoryProfile.getCategoryId());

        if (null != existingProfile) {
            throw LexaException.error(LexaCode.CATEGORY_ALREADY_EXISTS,
                    Collections.singletonMap(LexaUtils.Keys.MESSAGE_STRING, "Category definition already exists."));
        }

        inCategoryProfile.setMapped(false);
        inCategoryProfile.setOutgoingCategories(new HashSet<>());

        categoryCommands.createCategory(inCategoryProfile);

        return inCategoryProfile;
    }

    @Override
    public CategoryProfile updateCategory(CategoryProfile newProfile) throws Exception {
        val existingProfile = categoryCommands.categoryCheck(newProfile.getCategoryGroup(), newProfile.getCategoryId());
        CategoryProfile updatedProfile = LexaUtils.copy(existingProfile, newProfile);

        categoryCommands.updateCategory(updatedProfile);

        return updatedProfile;
    }

    @Override
    public void createMapping(String categoryGroup, String categoryId, MappingDefinition mappingDefinition, boolean remap) throws Exception {
        if (remap) {
            categoryCommands.createIndexTemplate(categoryGroup, categoryId, mappingDefinition);
            categoryCommands.createMapping(categoryGroup, categoryId, mappingDefinition);
            return;
        }

        val existingProfile = categoryCommands.categoryCheck(categoryGroup, categoryId);
        if (existingProfile.isMapped()) {
            return;
        }

        categoryCommands.createIndexTemplate(categoryGroup, categoryId, mappingDefinition);
        createCategoryContainment(categoryGroup, categoryId);

        markMapped(existingProfile);
    }

    @Override
    public void link(String categoryGroup, Edge categoryEdge) throws Exception {
        val sourceProfile = categoryCommands.categoryCheck(categoryGroup, categoryEdge.getSource());
        categoryCommands.categoryCheck(categoryGroup, categoryEdge.getDestination());

        val categoryGraph = categoryCommands.getCategoryGroup(categoryGroup);
        if (categoryGraph.cycleExists(categoryEdge)) {
            throw LexaException.error(LexaCode.CYCLIC_CATEGORY_ERROR,
                    Collections.singletonMap(LexaUtils.Keys.MESSAGE_STRING, "The addition of this link will cause a cyclic dependency."));
        }

        sourceProfile.addOutgoingCategory(categoryEdge.getDestination());
        categoryCommands.updateCategory(sourceProfile);
    }

    @Override
    public void unLink(String categoryGroup, Edge categoryEdge) throws Exception {
        val sourceProfile = categoryCommands.categoryCheck(categoryGroup, categoryEdge.getSource());
        categoryCommands.categoryCheck(categoryGroup, categoryEdge.getDestination());

        sourceProfile.removeOutgoingCategory(categoryEdge.getDestination());
        categoryCommands.updateCategory(sourceProfile);
    }

    @Override
    public void toggleCategoryStatus(String categoryGroup, String categoryId, EntityStatus entityStatus) {
        val categoryProfile = categoryCommands.getProfile(categoryGroup, categoryId);

        if (null == categoryProfile || categoryProfile.getEntityType() != EntityType.CATEGORY) {
            throw LexaException.error(LexaCode.CATEGORY_DOES_NOT_EXIST,
                    Collections.singletonMap(LexaUtils.Keys.MESSAGE_STRING, "Category definition doesn't exist."));
        }

        categoryCommands.toggleCategoryStatus(categoryGroup, categoryId, entityStatus);
    }
}
