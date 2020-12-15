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
import io.github.jelastic.core.repository.ElasticRepository;
import io.github.lexa.core.schema.mapping.MappingDefinition;
import io.github.lexa.core.schema.profiles.ManifestProfile;
import io.github.lexa.core.utils.LexaUtils;
import io.github.lexa.core.utils.SerDe;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.util.Optional;

@Slf4j
@Singleton
public class ManifestCommands extends JElasticCommands{

    public ManifestCommands(ElasticRepository elasticRepository) {
        super(elasticRepository);
    }

    @MonitoredFunction
    public ManifestProfile getManifestProfile(String manifestId) throws Exception {
        Optional<ManifestProfile> optionalManifest = this.get(
                manifestId,
                LexaUtils.getSchemaIndex(),
                LexaUtils.getSchemaMappingType(),
                ManifestProfile.class
        );

        return optionalManifest.orElse(null);
    }

    @MonitoredFunction
    public void saveManifest(ManifestProfile inManifestProfile) throws Exception {
        this.save(
                LexaUtils.getSchemaIndex(),
                LexaUtils.getSchemaMappingType(),
                inManifestProfile.getManifestId(),
                SerDe.mapper().writeValueAsString(inManifestProfile)
        );
    }

    @MonitoredFunction
    public void createIndexTemplate(String manifestId, MappingDefinition mappingDefinition)
            throws Exception {
        this.createIndexTemplate(
                LexaUtils.getTemplateName(null, manifestId),
                LexaUtils.getManifestSourceIndex(manifestId),
                LexaUtils.getManifestSourceType(manifestId),
                mappingDefinition.getMappingSchema(),
                mappingDefinition.getAnalysis()
        );
    }

    @MonitoredFunction
    public void createMapping(String manifestId, MappingDefinition mappingDefinition)
            throws Exception {
        this.createMapping(
                LexaUtils.getManifestSourceIndex(manifestId),
                LexaUtils.getManifestSourceType(manifestId),
                mappingDefinition.getMappingSchema()
        );
    }

    @MonitoredFunction
    public void createManifestIndex(String manifestId) throws Exception {
        this.createIndex(LexaUtils.getManifestSourceIndex(manifestId));
    }
}
