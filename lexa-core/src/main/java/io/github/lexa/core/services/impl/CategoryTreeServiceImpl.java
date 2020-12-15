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

import io.github.lexa.core.commands.CategoryCommands;
import io.github.lexa.core.schema.CategoryTree;
import io.github.lexa.core.schema.profiles.CategoryProfile;
import io.github.lexa.core.services.CategoryTreeService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j @Getter
@Singleton
@AllArgsConstructor
public class CategoryTreeServiceImpl implements CategoryTreeService{

    private final CategoryCommands categoryCommands;

    @Override
    public CategoryTree getCategoryGroup(String categoryGroup, long lastSeen) {
        CategoryTree serviceTree = categoryCommands.getCategoryGroup(categoryGroup);
        return serviceTree.getUpdatedAt() > lastSeen ? serviceTree : null;
    }

    @Override
    public List<CategoryTree> getCategoryGroups(List<String> categoryGroups, long lastSeen) {
        return categoryGroups.stream()
                .parallel()
                .map(serviceType -> getCategoryGroup(serviceType, lastSeen))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryProfile getProfile(String categoryGroup, String categoryId) {
        return categoryCommands.getProfile(categoryGroup, categoryId);
    }

    @Override
    public List<CategoryProfile> getChildren(String categoryGroup, String categoryId) {
        CategoryTree categoryTree = getCategoryGroup(categoryGroup, 0);

        if (null == categoryTree) {
            return new ArrayList<>();
        }

        CategoryProfile masterCategory = getProfile(categoryGroup, categoryId);

        return masterCategory.getOutgoingCategories()
                .stream().map(categoryTree::getCategoryProfile)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
