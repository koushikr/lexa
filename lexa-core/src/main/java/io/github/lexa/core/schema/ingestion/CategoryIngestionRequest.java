package io.github.lexa.core.schema.ingestion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.lexa.core.schema.profiles.EntityType;
import lombok.*;

import javax.validation.constraints.NotEmpty;

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
@Getter
@Setter
@AllArgsConstructor
@ToString
public class CategoryIngestionRequest extends IngestionRequest{

    private String serviceType;

    //x. Is the parent categoryId in the title. Read more like a group
    @NonNull
    @NotEmpty
    private String rootCategoryId;

    @Builder
    public CategoryIngestionRequest(String id, String serviceType, String rootCategoryId,
                                    Object data) {
        super(id, EntityType.CATEGORY, data);

        this.serviceType = serviceType;
        this.rootCategoryId = rootCategoryId;
    }
}
