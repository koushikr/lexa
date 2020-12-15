package io.github.lexa.core.commands;

import io.appform.functionmetrics.MonitoredFunction;
import io.github.jelastic.core.models.helper.MapElement;
import io.github.jelastic.core.models.index.IndexProperties;
import io.github.jelastic.core.models.mapping.CreateMappingRequest;
import io.github.jelastic.core.models.query.Query;
import io.github.jelastic.core.models.query.paged.PageWindow;
import io.github.jelastic.core.models.search.IdSearchRequest;
import io.github.jelastic.core.models.search.SearchRequest;
import io.github.jelastic.core.models.source.EntitySaveRequest;
import io.github.jelastic.core.models.source.GetSourceRequest;
import io.github.jelastic.core.models.source.UpdateEntityRequest;
import io.github.jelastic.core.models.source.UpdateFieldRequest;
import io.github.jelastic.core.models.template.CreateTemplateRequest;
import io.github.jelastic.core.repository.ElasticRepository;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
@Getter
@Slf4j
public abstract class JElasticCommands {

    private final ElasticRepository elasticRepository;

    public JElasticCommands(ElasticRepository elasticRepository){
        this.elasticRepository = elasticRepository;
    }

    @MonitoredFunction(method = "getBySearchRequest")
    protected <T> List<T> query(
            String index, String type, Query query, Class<T> klass
    ) {
        SearchRequest<T> searchRequest = SearchRequest.<T>builder()
                .index(index)
                .type(type)
                .query(query)
                .klass(klass)
                .build();

        return this.getElasticRepository().search(
                searchRequest
        );
    }

    @MonitoredFunction(method = "getByQuery")
    protected <T> List<T> query(String index, QueryBuilder queryBuilder, PageWindow pageWindow, Class<T> klass) {
        return this.getElasticRepository().search(
                index,
                queryBuilder,
                pageWindow,
                klass
        );
    }

    @MonitoredFunction
    protected <T> List<T> queryByIds(String index, String type, List<String> ids, Class<T> klass) {
        IdSearchRequest<T> idSearchRequest = IdSearchRequest.<T>builder()
                .ids(ids)
                .index(index)
                .type(type)
                .klass(klass)
                .build();

        return this.getElasticRepository().searchByIds(
                idSearchRequest
        );
    }

    @MonitoredFunction
    protected void updateField(String indexName, String referenceId, String field,
                               Object value) {
        UpdateFieldRequest updateFieldRequest = UpdateFieldRequest.builder()
                .indexName(indexName)
                .referenceId(referenceId)
                .fieldValueMap(Collections.singletonMap(field, value))
                .build();

        elasticRepository.updateField(updateFieldRequest);
    }

    @MonitoredFunction
    protected <T> Optional<T> get(String referenceId, String indexName, String mappingType,
                                  Class<T> klass) {
        GetSourceRequest<T> getSourceRequest = GetSourceRequest.<T>builder()
                .referenceId(referenceId)
                .indexName(indexName)
                .mappingType(mappingType)
                .klass(klass)
                .build();

        return this.getElasticRepository().get(
                getSourceRequest
        );
    }

    @MonitoredFunction
    protected void save(String indexName, String mappingType, String referenceId, String value) {
        EntitySaveRequest entitySaveRequest = EntitySaveRequest.builder()
                .indexName(indexName)
                .mappingType(mappingType)
                .referenceId(referenceId)
                .value(value)
                .build();

        this.getElasticRepository().save(
                entitySaveRequest
        );
    }

    @MonitoredFunction
    protected void update(String indexName, String mappingType, String referenceId, String value) {
        UpdateEntityRequest updateEntityRequest = UpdateEntityRequest.builder()
                .indexName(indexName)
                .mappingType(mappingType)
                .referenceId(referenceId)
                .value(value)
                .build();

        this.getElasticRepository().update(
                updateEntityRequest
        );
    }

    @MonitoredFunction
    protected void createIndexTemplate(String templateIdentifier, String indexName,
                                       String mappingType, MapElement mappingSource,
                                       MapElement analysis) {

        CreateTemplateRequest createTemplateRequest = CreateTemplateRequest.builder()
                .templateName(templateIdentifier)
                .indexPattern(indexName)
                .indexProperties(
                        IndexProperties.builder()
                                .enableRequestCache(true)
                                .noOfReplicas(1)
                                .noOfShards(1)
                                .build()
                )
                .analysis(analysis)
                .mappingType(mappingType)
                .mappingSource(mappingSource)
                .build();

        this.getElasticRepository().createTemplate(
                createTemplateRequest
        );
    }

    @MonitoredFunction
    protected void createMapping(String indexName, String mappingType, MapElement mapElement) {
        CreateMappingRequest createMappingRequest = CreateMappingRequest.builder()
                .indexName(indexName)
                .mappingSource(mapElement)
                .mappingType(mappingType)
                .build();
        this.getElasticRepository().createMapping(
                createMappingRequest
        );
    }

    @MonitoredFunction
    protected void createIndex(String indexName) {
        this.getElasticRepository().createIndex(
                indexName
        );
    }

    @MonitoredFunction
    public void shiftAlias(String newIndex, String aliasName) {
        this.getElasticRepository().reAlias(
                newIndex, aliasName
        );
    }
}
