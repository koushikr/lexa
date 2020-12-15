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

import io.github.lexa.core.commands.ManifestCommands;
import io.github.lexa.core.exceptions.LexaCode;
import io.github.lexa.core.exceptions.LexaException;
import io.github.lexa.core.schema.mapping.MappingDefinition;
import io.github.lexa.core.schema.profiles.EntityStatus;
import io.github.lexa.core.schema.profiles.EntityType;
import io.github.lexa.core.schema.profiles.ManifestProfile;
import io.github.lexa.core.services.ManifestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import javax.inject.Singleton;
import java.util.Collections;

import static io.github.lexa.core.utils.LexaUtils.Keys.MESSAGE_STRING;

@Slf4j @Singleton
@AllArgsConstructor
public class ManifestServiceImpl implements ManifestService{

    private final ManifestCommands manifestCommands;

    @Override
    public void createManifestContainment(String manifestId) throws Exception {
        val existingProfile = getProfile(manifestId);

        if (null == existingProfile) {
            throw LexaException.error(LexaCode.MANIFEST_DOES_NOT_EXIST,
                    Collections.singletonMap(MESSAGE_STRING, "Manifest definition doesn't exist."));
        }

        if (existingProfile.isMapped()) {
            throw LexaException.error(LexaCode.MANIFEST_SCHEMA_EXISTS,
                    Collections.singletonMap(MESSAGE_STRING, "Manifest schema exists already."));
        }

        manifestCommands.createManifestIndex(manifestId);
    }

    @Override
    public ManifestProfile createManifest(ManifestProfile inManifestProfile) throws Exception {
        val existingProfile = getProfile(inManifestProfile.getManifestId());

        if (null != existingProfile) {
            throw LexaException.error(LexaCode.MANIFEST_ALREADY_EXISTS,
                    Collections.singletonMap(MESSAGE_STRING, "Manifest definition already exists."));
        }

        inManifestProfile.setMapped(false);
        inManifestProfile.setStatus(EntityStatus.ACTIVE);

        manifestCommands.saveManifest(existingProfile);
        return inManifestProfile;

    }

    @Override
    public void map(String manifestId, MappingDefinition mappingDefinition, boolean remap) throws Exception {
        val existingProfile = getProfile(manifestId);

        if (null == existingProfile || existingProfile.getEntityType() != EntityType.MANIFEST) {
            throw LexaException.error(LexaCode.MANIFEST_DOES_NOT_EXIST,
                    Collections.singletonMap(MESSAGE_STRING, "Manifest definition doesn't exist."));
        }

        if (remap) {
            manifestCommands.createIndexTemplate(manifestId, mappingDefinition);
            manifestCommands.createMapping(manifestId, mappingDefinition);
            return;
        }

        if (existingProfile.isMapped()) {
            return;
        }

        manifestCommands.createIndexTemplate(manifestId, mappingDefinition);
        createManifestContainment(manifestId);

        existingProfile.setMapped(true);
        manifestCommands.saveManifest(existingProfile);
    }

    @Override
    public ManifestProfile getProfile(String manifestId) throws Exception {
        return manifestCommands.getManifestProfile(manifestId);
    }

    @Override
    public void markProfileMapped(String manifestId) throws Exception {
        val existingProfile = getProfile(manifestId);
        if (null == existingProfile || existingProfile.getEntityType() != EntityType.MANIFEST) {
            throw LexaException.error(LexaCode.MANIFEST_DOES_NOT_EXIST,
                    Collections.singletonMap("message", "Manifest definition doesn't exist."));
        }
        if (existingProfile.isMapped()) {
            return;
        }
        existingProfile.setMapped(true);
        manifestCommands.saveManifest(existingProfile);
    }


}
