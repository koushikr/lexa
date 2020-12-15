package io.github.lexa.core.schema.profiles;

/*
  Copyright 2020 Koushik R <rkoushik.14@gmail.com>.
  <p>
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  <p>
  http://www.apache.org/licenses/LICENSE-2.0
  <p>
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Sets;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Builder
public class CategoryProfile {

    @NonNull
    @Length(max = 32)
    private String categoryId;

    private String categoryGroup;

    @Builder.Default
    private long priority = 0;

    @NonNull
    private Set<String> outgoingCategories;

    @Builder.Default
    private EntityType entityType = EntityType.CATEGORY;

    private EntityStatus status;

    private Object data;

    private boolean mapped;

    @Length(max = 255)
    private String entityName;

    @Length(max = 255)
    private String displayName;

    @Builder.Default
    private long updatedAt = System.currentTimeMillis();

    /**
     * A function to add an outgoingCategoryId to the current categoryProfile
     */
    @JsonIgnore
    public void addOutgoingCategory(String categoryId) {
        if (Objects.isNull(outgoingCategories) || outgoingCategories.isEmpty()) {
            this.outgoingCategories = Sets.newHashSet(categoryId);
        } else {
            this.getOutgoingCategories().add(categoryId);
        }
    }

    /**
     * A function to remove the outgoingCategoryId from the categoryProfile
     */
    @JsonIgnore
    public void removeOutgoingCategory(String categoryId) {
        if (Objects.isNull(outgoingCategories) || outgoingCategories.isEmpty()) {
            return;
        }

        this.outgoingCategories = this.outgoingCategories
                .stream()
                .filter(
                        each -> !each.equalsIgnoreCase(categoryId))
                .collect(Collectors.toSet());
    }

    public long getUpdatedAt() {
        if (this.updatedAt == 0) {
            return System.currentTimeMillis();
        }

        return updatedAt;
    }
}

