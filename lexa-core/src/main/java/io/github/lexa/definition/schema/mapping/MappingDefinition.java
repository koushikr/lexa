package io.github.lexa.definition.schema.mapping;

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

import io.github.jelastic.core.models.helper.MapElement;
import lombok.*;

/*
 *
 * The definition will be used to create the appropriate indexes for the manifest
 * It is also possible to on-board a manifest without a definition. It is upto the user
 * to make sure that she on-boards the manifest properly. If an ingestion is done against
 * a manifest and if it isn't created then we don't let the ingestion happen in the first place.
 *
 * The definition in its purest form will also be saved in elastic search, in case
 * you want to quickly check what it stands for and all of these values would be cached.
 *
 * Once the manifest is onboarded with the definition we could ingest entities onto the manifest
 * based on its definition.
 *
 * Each definition holds onto a mapping key through which we can identify what is going to be the
 * primary key of that entity. Will use that during ingestion. Every ingestion requires us to specify
 * either the definitionId, or the serviceType and categoryId.
 *
 *    mappingSchema : {
 *       "properties" :{
 *                  "id: {
 *                        "type": "string",
 *                       "store": "yes",
 *                       "index": "not_analyzed"
 *                     },
 *                   "name": {
 *                      "type": "string",
 *                      "store": "yes",
 *                      "index": "not_analyzed"
 *                  },
 *                  "data": {
 *                  "type": "nested",
 *                  "properties": {
 *                      "name": {
 *                          "type": "string",
 *                          "store": "yes",
 *                          "index": "no"
 *                      },
 *                      "applicableField": {
 *                          "type": "string",
 *                          "store": "yes",
 *                          "index": "no"
 *                      },
 *                       "expression": {
 *                        "type": "string",
 *                          "store": "yes",
 *                          "index": "no"
 *                      }
 *                  }
 *              }
 *    }
 *
 * */

/**
 * The reason why mappingDefinition is an object, but not just mapExtension outside is tomorrow in
 * the definition, I'd also like to take the template definition and other things, which I'll create
 * in a new index, template index and serve the same from there.
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MappingDefinition {

    @NonNull
    @javax.validation.constraints.NotEmpty
    private MapElement mappingSchema;

    private MapElement analysis;

    @SneakyThrows
    public boolean validate() {
        if (!mappingSchema.containsKey("properties")) {
            throw new RuntimeException("INVALID_MAPPING_SCHEMA : properties field can't be empty.");
        }

        return true;
    }

}
