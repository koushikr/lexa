package io.github.lexa.dropwizard;

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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.github.jelastic.core.config.JElasticConfiguration;
import io.github.jelastic.core.elastic.ElasticClient;
import io.github.jelastic.core.health.EsClientHealth;
import io.github.jelastic.core.managers.QueryManager;
import io.github.jelastic.core.repository.ElasticRepository;
import io.github.jelastic.core.utils.MapperUtils;
import io.github.lexa.core.commands.CategoryCommands;
import io.github.lexa.core.commands.IngestionCommands;
import io.github.lexa.core.commands.ManifestCommands;
import io.github.lexa.core.commands.SearchCommands;
import io.github.lexa.core.services.*;
import io.github.lexa.core.services.impl.*;
import lombok.NoArgsConstructor;
import lombok.val;

@NoArgsConstructor
public abstract class LexaBundle<T extends Configuration> implements ConfiguredBundle<T> {

    private CategoryService categoryService;
    private CategoryTreeService categoryTreeService;
    private IngestionService ingestionService;
    private ManifestService manifestService;
    private QueryService queryService;

    public abstract JElasticConfiguration getElasticConfiguration(T configuration);

    /**
     * Sets the objectMapper properties and initializes elasticClient, along with its health check
     *
     * @param configuration {@link T}               The typed config against which the said TrouperBundle is initialized
     * @param environment   {@link Environment}     The dropwizard environment object.
     */
    @Override
    public void run(T configuration, Environment environment) throws Exception {
        environment.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        environment.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
        environment.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        environment.getObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        environment.getObjectMapper().configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        environment.getObjectMapper().configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);

        MapperUtils.init(environment.getObjectMapper());

        val JElasticConfiguration = getElasticConfiguration(configuration);
        val elasticClient = new ElasticClient(JElasticConfiguration);
        val elasticRepository = new ElasticRepository(elasticClient, new QueryManager(), JElasticConfiguration);

        //Create commands
        val categoryCommands = new CategoryCommands(elasticRepository);
        val ingestionCommands = new IngestionCommands(elasticRepository);
        val manifestCommands = new ManifestCommands(elasticRepository);
        val searchCommands = new SearchCommands(elasticRepository);

        //Create services
        categoryService = new CategoryServiceImpl(categoryCommands);
        categoryTreeService = new CategoryTreeServiceImpl(categoryCommands);
        ingestionService = new IngestionServiceImpl(ingestionCommands, categoryCommands, manifestCommands);
        manifestService = new ManifestServiceImpl(manifestCommands);
        queryService = new QueryServiceImpl(searchCommands);

        environment.healthChecks().register("jelastic-health-check", new EsClientHealth(elasticClient, JElasticConfiguration));
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {

    }

}
