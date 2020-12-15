package io.github.lexa.core.services;

import io.github.lexa.core.schema.mapping.MappingDefinition;
import io.github.lexa.core.schema.profiles.ManifestProfile;

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

public interface ManifestService {

    /**
     * Creates a mappedIndex using the manifestId. Checks for the existing mapping, if the mapping
     * already exists, error out.
     *
     * @param manifestId the manifestId against which the index has to be created
     */
    void createManifestContainment(String manifestId) throws Exception;

    /**
     * Create a manifestProfile {@link ManifestProfile} with the given inManifestProfile.
     * <p>
     * Preconditions include.
     * <p>
     * a) Making sure that the manifest is not already created
     * <p>
     * Error handling
     * <p>
     * a) Throws ManifestLAreadyExists exception
     *
     * @param inManifestProfile {@link ManifestProfile} Input manifestProfile
     * @return the manifestProfile {@link ManifestProfile} crated.
     */
    ManifestProfile createManifest(ManifestProfile inManifestProfile) throws Exception;

    /**
     * Creates a mapping given a manifestId and a mappingDefinition {@link MappingDefinition}.
     *
     * @param manifestId        {String}  The manifestId against which the mapping ought to be created.
     * @param mappingDefinition {@link MappingDefinition} mapping schema associated with the manifest
     * @param remap             {boolean} remap to indicate if the mapping object ought to be changed or
     *                          otherwise.
     */
    void map(String manifestId, MappingDefinition mappingDefinition, boolean remap) throws Exception;

    /**
     * Get the categoryProfile given a manifestId
     *
     * @param manifestId {String}  The manifestId against which the get has to happen
     * @return the related {@link ManifestProfile}
     */
    ManifestProfile getProfile(String manifestId) throws Exception;

    /**
     * Mark the given manifest profile as mapped.
     *
     * @param manifestId {String}  The manifestId of profile
     *
     */
    void markProfileMapped(String manifestId) throws Exception;

}
