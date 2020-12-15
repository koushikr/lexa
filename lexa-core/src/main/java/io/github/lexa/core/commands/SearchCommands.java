package io.github.lexa.core.commands;

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

import io.appform.functionmetrics.MonitoredFunction;
import io.github.jelastic.core.models.query.Query;
import io.github.jelastic.core.models.query.paged.PageWindow;
import io.github.jelastic.core.repository.ElasticRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilder;

import javax.inject.Singleton;
import java.util.List;

@Slf4j
@Singleton
public class SearchCommands extends JElasticCommands{

    public SearchCommands(ElasticRepository elasticRepository) {
        super(elasticRepository);
    }

    @MonitoredFunction
    public <T> List<T> search(String index, String type, Query query, Class<T> klass)
            throws Exception {
        return this.query(index, type, query, klass);
    }

    @MonitoredFunction
    public <T> List<T> searchByIds(String index, String type, List<String> ids,
                                   Class<T> klass) {
        return this.queryByIds(index, type, ids, klass);
    }

    @MonitoredFunction
    public <T> List<T> search(String index, QueryBuilder queryBuilder, PageWindow pageWindow,
                              Class<T> klass) {
        return this.query(index, queryBuilder, pageWindow, klass);
    }
}
