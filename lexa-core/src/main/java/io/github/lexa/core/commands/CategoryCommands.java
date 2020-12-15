package io.github.lexa.core.commands;/*
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

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.appform.functionmetrics.MonitoredFunction;
import io.github.jelastic.core.models.query.Query;
import io.github.jelastic.core.models.query.filter.Filter;
import io.github.jelastic.core.models.query.filter.FilterType;
import io.github.jelastic.core.models.query.filter.general.EqualsFilter;
import io.github.jelastic.core.models.query.filter.general.InFilter;
import io.github.jelastic.core.models.query.paged.PageWindow;
import io.github.jelastic.core.models.source.GetSourceRequest;
import io.github.jelastic.core.repository.ElasticRepository;
import io.github.lexa.core.exceptions.LexaCode;
import io.github.lexa.core.exceptions.LexaException;
import io.github.lexa.core.schema.CategoryTree;
import io.github.lexa.core.schema.mapping.MappingDefinition;
import io.github.lexa.core.schema.profiles.CategoryProfile;
import io.github.lexa.core.schema.profiles.EntityStatus;
import io.github.lexa.core.schema.profiles.EntityType;
import io.github.lexa.core.utils.LexaUtils;
import io.github.lexa.core.utils.SerDe;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Singleton
public class CategoryCommands extends JElasticCommands{

    public CategoryCommands(ElasticRepository elasticRepository) {
        super(elasticRepository);
    }

    @MonitoredFunction
    public CategoryProfile getProfile(String categoryGroup, String categoryId) {
        GetSourceRequest<CategoryProfile> getSourceRequest = GetSourceRequest.<CategoryProfile>builder()
                .indexName(LexaUtils.getSchemaIndex())
                .mappingType(LexaUtils.getSchemaMappingType())
                .referenceId(LexaUtils.getCategoryKey(
                        categoryGroup,
                        categoryId))
                .klass(CategoryProfile.class)
                .build();

        Optional<CategoryProfile> categoryProfile = this.getElasticRepository().get(getSourceRequest);
        return categoryProfile.orElse(null);
    }

    @MonitoredFunction
    public CategoryProfile categoryCheck(String categoryGroup, String categoryId) {
        val sourceProfile = getProfile(categoryGroup, categoryId);

        if (null == sourceProfile || sourceProfile.getEntityType() != EntityType.CATEGORY) {
            throw LexaException.error(LexaCode.CATEGORY_DOES_NOT_EXIST,
                    Collections.singletonMap(LexaUtils.Keys.MESSAGE_STRING, "Category definition doesn't exist."));
        }

        return sourceProfile;
    }

    @MonitoredFunction
    public void createCategory(CategoryProfile inCategoryProfile) throws Exception {
        this.save(
                LexaUtils.getSchemaIndex(),
                LexaUtils.getSchemaMappingType(),
                LexaUtils
                        .getCategoryKey(inCategoryProfile.getCategoryGroup(), inCategoryProfile.getCategoryId()),
                SerDe.mapper().writeValueAsString(inCategoryProfile)
        );
    }

    @MonitoredFunction
    public List<CategoryProfile> getProfiles(List<String> categoryGroups) {
        Filter inFilter = new InFilter(LexaUtils.Fields.SERVICE_TYPE, new ArrayList<>(categoryGroups));

        EqualsFilter equalsFilter = new EqualsFilter(
                FilterType.EQUALS,
                LexaUtils.Fields.ENTITY_TYPE,
                EntityType.CATEGORY.name()
        );

        Query simpleQuery = Query.builder()
                .queryName("getProfiles")
                .filters(Sets.newHashSet(inFilter, equalsFilter))
                .sorters(Sets.newTreeSet())
                .pageWindow(PageWindow.builder()
                        .pageNumber(0)
                        .pageSize(1000)
                        .build())
                .build();

        return this.query(
                LexaUtils.getSchemaIndex(),
                LexaUtils.getSchemaMappingType(),
                simpleQuery,
                CategoryProfile.class
        );
    }

    @MonitoredFunction
    public void toggleCategoryStatus(String categoryGroup, String categoryId,
                                     EntityStatus entityStatus) {
        this.updateField(
                LexaUtils.getSchemaIndex(),
                LexaUtils.getCategoryKey(categoryGroup, categoryId),
                LexaUtils.Keys.STATUS_ATTRIBUTE,
                entityStatus.name()
        );

        this.updateField(
                LexaUtils.getSchemaIndex(),
                LexaUtils.getCategoryKey(categoryGroup, categoryId),
                LexaUtils.Keys.UPDATED_AT_ATTRIBUTE,
                System.currentTimeMillis()
        );
    }

    @MonitoredFunction
    public CategoryProfile get(String categoryGroup, String categoryId) {
        Optional<CategoryProfile> optionalProfile = this.get(
                LexaUtils.getCategoryKey(categoryGroup, categoryId),
                LexaUtils.getSchemaIndex(),
                LexaUtils.getSchemaMappingType(),
                CategoryProfile.class
        );

        return optionalProfile.orElse(null);
    }

    @MonitoredFunction
    public void updateCategory(CategoryProfile inCategoryProfile) throws Exception {
        this.save(
                LexaUtils.getSchemaIndex(),
                LexaUtils.getSchemaMappingType(),
                LexaUtils
                        .getCategoryKey(inCategoryProfile.getCategoryGroup(), inCategoryProfile.getCategoryId()),
                SerDe.mapper().writeValueAsString(inCategoryProfile)
        );
    }

    @MonitoredFunction
    public void createIndex(String categoryGroup, String categoryId) {
        this.createIndex(
                LexaUtils.getCategorySourceIndex(categoryGroup, categoryId)
        );
    }

    @MonitoredFunction
    public void createIndexTemplate(String categoryGroup, String categoryId,
                                    MappingDefinition mappingDefinition) {
        this.createIndexTemplate(
                LexaUtils.getTemplateName(categoryGroup, categoryId),
                LexaUtils.getCategorySourceIndex(categoryGroup, categoryId),
                LexaUtils.getCategorySourceType(categoryGroup, categoryId),
                mappingDefinition.getMappingSchema(),
                null
        );
    }

    @MonitoredFunction
    public void createMapping(String categoryGroup, String categoryId,
                              MappingDefinition mappingDefinition) {
        this.createMapping(
                LexaUtils.getCategorySourceIndex(categoryGroup, categoryId),
                LexaUtils.getCategorySourceType(categoryGroup, categoryId),
                mappingDefinition.getMappingSchema()
        );
    }

    @MonitoredFunction
    public CategoryTree getCategoryGroup(String categoryGroups){
        List<CategoryProfile> allProfiles = getProfiles(Lists.newArrayList(categoryGroups));
        return CategoryTree.build(categoryGroups, allProfiles);
    }

}
