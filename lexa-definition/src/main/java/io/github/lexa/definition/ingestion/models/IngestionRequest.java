package io.github.lexa.definition.ingestion.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.lexa.definition.schema.profiles.EntityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.sql.Timestamp;

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
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "entityType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CategoryIngestionRequest.class, name = "CATEGORY"),
        @JsonSubTypes.Type(value = ManifestIngestionRequest.class, name = "MANIFEST")
})
public abstract class IngestionRequest {

    private final Timestamp updatedAt = new Timestamp(System.currentTimeMillis());

    /**
     * Is a unique key.
     */
    @NonNull
    @javax.validation.constraints.NotEmpty
    private String id;

    private EntityType entityType;

    /**
     * @desc If you want versions on your data, please make sure your manifestSchema has a key
     * version in it and is made a part of the primary key.
     * <p>
     * As the data is scoped to a object, it is hard to put a hard bound rule on versions. But
     * versions are the functional spokes responsibility
     */
    @NonNull
    private Object data;
}
