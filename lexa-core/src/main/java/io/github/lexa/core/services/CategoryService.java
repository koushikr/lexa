package io.github.lexa.core.services;

import io.github.jforrest.Edge;
import io.github.lexa.core.schema.mapping.MappingDefinition;
import io.github.lexa.core.schema.profiles.CategoryProfile;
import io.github.lexa.core.schema.profiles.EntityStatus;

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

public interface CategoryService {

    /**
     * Creates a mappedIndex using the {@link String} and categoryId. Checks for the existing
     * mapping, if the mapping already exists, error out.
     *
     * @param categoryGroup a valid {@link String} categoryGroup instance
     * @param categoryId  the categoryId against which the index has to be created
     */
    void createCategoryContainment(String categoryGroup, String categoryId) throws Exception;

    /**
     * Create a categoryProfile {@link CategoryProfile} with the given inCategoryProfile.
     * <p>
     * Preconditions include.
     * <p>
     * a) Making sure that the category is not already created b) Setting all the outgoing categories
     * for the category to empty
     * <p>
     * Error handling
     * <p>
     * a) Throws CatalogueAlreadyExists exception
     *
     * @param inCategoryProfile {@link CategoryProfile} Input categoryProfile
     * @return the categoryProfile {@link CategoryProfile} crated.
     */
    CategoryProfile createCategory(CategoryProfile inCategoryProfile) throws Exception;

    /**
     * Creates a mapping given a categoryGroup {@link String} a categoryId and a mappingDefinition
     * {@link MappingDefinition}. Errors out if attempted to create a mapping for a non core
     * categoryId
     *
     * @param categoryGroup       {@link String} the categoryGroup for the core request
     * @param categoryId        {String}  The categoryId against which (core) the mapping ought to be
     *                          created.
     * @param mappingDefinition {@link MappingDefinition} mapping schema associated with the category
     * @param remap             {boolean} remap to indicate if the mapping object ought to be changed or
     *                          otherwise.
     */
    void createMapping(String categoryGroup, String categoryId,
                       MappingDefinition mappingDefinition, boolean remap) throws Exception;

    /**
     * Updates the already created category with a newProfile {@link CategoryProfile}. Throws an error
     * if the category doesn't already exist
     *
     * @param newProfile {@link CategoryProfile} the newProfile to be updated with
     * @return the updated categoryProfile {@link CategoryProfile}
     */
    CategoryProfile updateCategory(CategoryProfile newProfile) throws Exception;

    /**
     * To link two categories under a categoryGroup.
     *
     * @param categoryGroup  {@link String} the categoryGroup (roof) under which the categories are
     *                     present.
     * @param categoryEdge {@link Edge} source-destination pair
     */
    void link(String categoryGroup, Edge categoryEdge) throws Exception;

    /**
     * To unlink two categories under a categoryGroup.
     *
     * @param categoryGroup  {@link String} the categoryGroup (root) under which the categories are
     *                     present.
     * @param categoryEdge {@link Edge} source-destination pair
     */
    void unLink(String categoryGroup, Edge categoryEdge) throws Exception;

    /**
     * To toggle the category status.
     *
     * @param categoryGroup  {@link String} the categoryGroup (roof) under which the categories are
     *                     present.
     * @param categoryId   {String}  The categoryId against which (core) the toggle has to happen.
     * @param entityStatus {@link EntityStatus} entity status denoting the toggle value
     */
    void toggleCategoryStatus(String categoryGroup, String categoryId, EntityStatus entityStatus)
            throws Exception;
}
