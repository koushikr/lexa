package io.github.lexa.core.services;

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

import io.github.jelastic.core.models.query.Query;
import io.github.jelastic.core.models.query.paged.PageWindow;
import org.elasticsearch.index.query.QueryBuilder;

import java.util.List;

public interface QueryService {

    /**
     * Get the list of sources given a serviceType {@link String}, a categoryId, a query object
     * {@link io.github.jelastic.core.models.query.Query} and the corresponding klass T.
     *
     * @param serviceType {@link String} The serviceType associated with the search
     * @param categoryId  {String}  The categoryId associated with the search
     * @param query       {@link Query} The query object against which the search ought to be performed
     * @param klass       {@link Class} A generic class object against which the response would be
     *                    marshalled.
     * @param <T>         {@link T} A type parameter indicating the classType
     * @return the list of {@link T} the typed objects that get returned from ES.
     */
    <T> List<T> getSources(String serviceType, String categoryId, Query query, Class<T> klass)
            throws Exception;

    /**
     * Get the list of sources given a manifestId, a query object {@link Query} and the corresponding
     * klass T.
     *
     * @param manifestId {String}  The manifestId associated with the search
     * @param query      {@link Query} The query object against which the search ought to be performed
     * @param klass      {@link Class} A generic class object against which the response would be
     *                   marshalled.
     * @param <T>        {@link T} A type parameter indicating the classType
     * @return the list of {@link T} the typed objects that get returned from ES.
     */
    <T> List<T> getSources(String manifestId, Query query, Class<T> klass) throws Exception;

    /**
     * Get the list of sources given a manifestId, ids and the corresponding klass T.
     *
     * @param manifestId {String}  The manifestId associated with the search
     * @param ids        {String} The ids against which the search ought to be performed
     * @param klass      {@link Class} A generic class object against which the response would be
     *                   marshalled.
     * @param <T>        {@link T} A type parameter indicating the classType
     * @return the list of {@link T} the typed objects that get returned from ES.
     */
    <T> List<T> getSources
    (String manifestId, List<String> ids, Class<T> klass) throws Exception;

    /**
     * Get the list of sources given a manifestId, ids and the corresponding klass T.
     *
     * @param manifestId {String}  The manifestId associated with the search
     * @param queryBuilder {@link QueryBuilder} The queryBuilder associated with the search
     * @param pageWindow {@link PageWindow} The pageWindow associated with the request
     * @param klass      {@link Class} A generic class object against which the response would be
     *                   marshalled.
     * @param <T>        {@link T} A type parameter indicating the classType
     * @return the list of {@link T} the typed objects that get returned from ES.
     */
    <T> List<T> getSources
    (String manifestId, QueryBuilder queryBuilder, PageWindow pageWindow, Class<T> klass) throws Exception;

    /**
     * Get the list of sources given a index, a mapping type, a query object
     * {@link io.github.jelastic.core.models.query.Query} and the corresponding klass T.
     *
     * @param index {@link String} The index associated with the search
     * @param mappingType  {String}  The mappingType associated with the search
     * @param query       {@link Query} The query object against which the search ought to be performed
     * @param klass       {@link Class} A generic class object against which the response would be
     *                    marshalled.
     * @param <T>         {@link T} A type parameter indicating the classType
     * @return the list of {@link T} the typed objects that get returned from ES.
     */
    <T> List<T> getSourcesByIndex(String index, String mappingType, Query query, Class<T> klass)
            throws Exception;
    /**
     * Get the list of sources given a manifestId, ids, corresponfing mapping type and the corresponding klass T.
     *
     * @param index {String}  The index associated with the search
     * @param mappingType {String} The mappingType associated with the search
     * @param ids        {String} The ids against which the search ought to be performed
     * @param klass      {@link Class} A generic class object against which the response would be
     *                   marshalled.
     * @param <T>        {@link T} A type parameter indicating the classType
     * @return the list of {@link T} the typed objects that get returned from ES.
     */
    <T> List<T> getSourcesByIndex(String index, String mappingType, List<String> ids, Class<T> klass)
            throws Exception;
}
