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

import io.github.lexa.core.commands.CategoryCommands;
import io.github.lexa.core.commands.IngestionCommands;
import io.github.lexa.core.commands.ManifestCommands;
import io.github.lexa.core.exceptions.LexaCode;
import io.github.lexa.core.exceptions.LexaException;
import io.github.lexa.core.schema.ingestion.CategoryIngestionRequest;
import io.github.lexa.core.schema.ingestion.IngestionRequest;
import io.github.lexa.core.schema.ingestion.ManifestIngestionRequest;
import io.github.lexa.core.schema.profiles.EntityTypeVisitor;
import io.github.lexa.core.services.IngestionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import javax.inject.Singleton;
import java.util.Collections;

@Slf4j
@Singleton
@AllArgsConstructor
public class IngestionServiceImpl implements IngestionService {

    private final IngestionCommands ingestionCommands;
    private final CategoryCommands categoryCommands;
    private final ManifestCommands manifestCommands;

    @Override
    public void ingest(IngestionRequest ingestionRequest) throws Exception {
        ingestionRequest.getEntityType().accept(new EntityTypeVisitor<Void>() {
            @Override
            public Void visitCategory() throws Exception {
                CategoryIngestionRequest categoryElementIngestion = (CategoryIngestionRequest) ingestionRequest;
                val categoryProfile = categoryCommands.getProfile(categoryElementIngestion.getServiceType(),
                        categoryElementIngestion.getRootCategoryId());
                if (null == categoryProfile || !categoryProfile.isMapped()) {
                    throw LexaException.error(LexaCode.CATEGORY_DOES_NOT_EXIST,
                            Collections.singletonMap("message", "Category definition doesn't exist."));
                }
                ingestionCommands.saveCategoryEntity(categoryElementIngestion);
                return null;
            }

            @Override
            public Void visitManifest() throws Exception {
                ManifestIngestionRequest manifestElementIngestion = (ManifestIngestionRequest) ingestionRequest;
                val manifestProfile = manifestCommands.getManifestProfile(manifestElementIngestion.getManifestId());

                if (null == manifestProfile || !manifestProfile.isMapped()) {
                    throw LexaException.error(LexaCode.MANIFEST_SCHEMA_MISSING,
                            Collections.singletonMap("message", "Manifest definition doesn't exist."));
                }
                ingestionCommands.saveManifestEntity(manifestElementIngestion);
                return null;
            }
        });
    }

}
