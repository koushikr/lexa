package io.github.lexa.core.services.impl;/*
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

import io.github.jelastic.core.models.query.Query;
import io.github.jelastic.core.models.query.paged.PageWindow;
import io.github.lexa.core.commands.SearchCommands;
import io.github.lexa.core.services.QueryService;
import io.github.lexa.core.utils.LexaUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilder;

import javax.inject.Singleton;
import java.util.List;

@Slf4j @Singleton
@AllArgsConstructor
public class QueryServiceImpl implements QueryService{

    private final SearchCommands searchCommands;

    @Override
    public <T> List<T> getSources(String serviceType, String categoryId, Query query, Class<T> klass) throws Exception {
        return searchCommands.search(
                LexaUtils.getCategorySourceIndex(serviceType, categoryId),
                LexaUtils.getCategorySourceType(serviceType, categoryId),
                query,
                klass
        );
    }

    @Override
    public <T> List<T> getSources(String manifestId, Query query, Class<T> klass) throws Exception {
        return searchCommands.search(
                LexaUtils.getManifestSourceIndex(manifestId),
                LexaUtils.getManifestSourceType(manifestId),
                query,
                klass
        );
    }

    @Override
    public <T> List<T> getSources(String manifestId, List<String> ids, Class<T> klass) {
        return searchCommands.searchByIds(
                LexaUtils.getManifestSourceIndex(manifestId),
                LexaUtils.getManifestSourceType(manifestId),
                ids,
                klass
        );
    }

    @Override
    public <T> List<T> getSources(String manifestId, QueryBuilder queryBuilder, PageWindow pageWindow, Class<T> klass) {
        return searchCommands.search(
                LexaUtils.getManifestSourceIndex(manifestId),
                queryBuilder,
                pageWindow,
                klass
        );
    }

    @Override
    public <T> List<T> getSourcesByIndex(String index, String mappingType, Query query, Class<T> klass) throws Exception {
        return searchCommands.search(index, mappingType, query, klass);
    }

    @Override
    public <T> List<T> getSourcesByIndex(String index, String mappingType, List<String> ids, Class<T> klass) {
        return searchCommands.searchByIds(index, mappingType, ids, klass);
    }
}
