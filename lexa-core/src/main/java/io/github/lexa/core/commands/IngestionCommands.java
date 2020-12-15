package io.github.lexa.core.commands;/*
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

import com.fasterxml.jackson.core.JsonProcessingException;
import io.appform.functionmetrics.MonitoredFunction;
import io.github.jelastic.core.repository.ElasticRepository;
import io.github.lexa.core.schema.ingestion.CategoryIngestionRequest;
import io.github.lexa.core.schema.ingestion.ManifestIngestionRequest;
import io.github.lexa.core.utils.LexaUtils;
import io.github.lexa.core.utils.SerDe;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;

@Slf4j
@Singleton
public class IngestionCommands extends JElasticCommands{

    public IngestionCommands(ElasticRepository elasticRepository) {
        super(elasticRepository);
    }

    @MonitoredFunction
    public void saveManifestEntity(ManifestIngestionRequest manifestElement) throws JsonProcessingException {
        this.save(
                LexaUtils.getManifestSourceIndex(manifestElement.getManifestId()),
                LexaUtils.getManifestSourceType(manifestElement),
                manifestElement.getIdentifier(),
                SerDe.mapper().writeValueAsString(manifestElement.getData())
        );
    }

    @MonitoredFunction
    public void saveCategoryEntity(CategoryIngestionRequest categoryElement) throws JsonProcessingException {
        this.save(
                LexaUtils.getCategorySourceIndex(categoryElement.getServiceType(),
                        categoryElement.getRootCategoryId()),
                LexaUtils.getCategorySourceType(categoryElement),
                categoryElement.getIdentifier(),
                SerDe.mapper().writeValueAsString(categoryElement.getData())
        );
    }
}
