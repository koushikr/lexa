package io.github.lexa.jelastic.query;

import io.github.jelastic.core.models.query.Query;
import io.github.jelastic.core.models.query.paged.PageWindow;

import java.util.List;

/**
 * Copyright 2020 Koushik R <rkoushik.14@gmail.com>.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

public interface QueryService {

    /**
     * Get the list of sources given a serviceType {@link String}, a categoryId, a query object
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
    (String manifestId, List<String> ids, Class<T> klass, String commandName) throws Exception;


    <T> List<T> getSources(String manifestId, PageWindow pageWindow, Class<T> klass,
                           String commandName,
                           Query.QueryBuilder queryBuilder) throws Exception;
}
