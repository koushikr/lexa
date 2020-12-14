package io.github.lexa.definition.services;

import io.github.lexa.definition.schema.CategoryTree;
import io.github.lexa.definition.schema.profiles.CategoryProfile;

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

public interface CategoryTreeService {
    /**
     * Get a serviceTree {@link String} given a categoryGroup {@link String} and a categoryId
     *
     * @param categoryGroup {@link String} the categoryGroup (roof) under which the categories are
     *                    present.
     * @param lastSeen    {long} The lastSeen value indicating what was the last known timestamp.
     * @return the corresponding {@link CategoryTree}
     */
    CategoryTree getCategoryTree(String categoryGroup, long lastSeen);

    /**
     * A plural interface to fetch the serviceTrees {@link String} given a list of categoryGroups
     *
     * @param categoryGroups List of categoryGroups {@link String} indicating category roofs.
     * @param lastSeen     {long} The lastSeen value indicating what was the last known timestamp.
     * @return the list of {@link String}
     */
    List<CategoryTree> getAllCategoryTrees(List<String> categoryGroups, long lastSeen) throws Exception;

    /**
     * Get the categoryProfile given a categoryGroup and a categoryId
     *
     * @param categoryGroup {@link String} the categoryGroup (roof) under which the categories are
     *                    present.
     * @param categoryId  {String}  The categoryId against which the get has to happen
     * @return the related {@link CategoryProfile}
     */
    CategoryProfile getProfile(String categoryGroup, String categoryId) throws Exception;


    /**
     * Get children for the given categoryGroup and categoryId.
     *
     * @param categoryGroup {@link String} the categoryGroup (roof) under which the categories are
     *                    present.
     * @param categoryId  {String}  The categoryId against which the get has to happen
     * @return the list of children! {@link List<CategoryProfile}
     */
    List<CategoryProfile> getChildren(String categoryGroup, String categoryId) throws Exception;
}
