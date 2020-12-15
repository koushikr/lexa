package io.github.lexa.core.utils;
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

import io.github.jelastic.core.models.helper.MapElement;
import io.github.lexa.core.exceptions.LexaCode;
import io.github.lexa.core.exceptions.LexaException;
import io.github.lexa.core.schema.ingestion.CategoryIngestionRequest;
import io.github.lexa.core.schema.ingestion.ManifestIngestionRequest;
import io.github.lexa.core.schema.profiles.CategoryProfile;

import java.io.InputStream;
import java.util.Collections;
import java.util.Objects;

import static io.github.lexa.core.utils.ResourceUtils.getNormalizedValue;

public class LexaUtils {

    private LexaUtils() {
        throw new IllegalStateException("Lexa Utility Class");
    }

    public static <T> T getSource(String path, Class<T> klass) {
        try {
            InputStream inputStream = ResourceUtils.getResource(path);
            return SerDe.mapper().readValue(inputStream, klass);
        } catch (Exception e) {
            throw LexaException.error(LexaCode.CATEGORY_SCHEMA_MISSING,
                    Collections.singletonMap("message", "Category Schema doesn't exist."));
        }
    }

    public static String getSchemaIndex() {
        return LexaUtils.Keys.SCHEMA_INDEX;
    }

    public static String getSchemaMappingType() {
        return LexaUtils.Keys.SCHEMA_MAPPING;
    }

    public static MapElement getSchemaMappingSource() {
        return getSource(LexaUtils.Keys.SCHEMA_FILE_PATH, MapElement.class);
    }

    public static CategoryProfile copy(
            CategoryProfile existingProfile, CategoryProfile newProfile) {
        return CategoryProfile.builder()
                .categoryId(existingProfile.getCategoryId())
                .categoryGroup(existingProfile.getCategoryGroup())
                .entityType(existingProfile.getEntityType())
                .entityName(newProfile.getEntityName())
                .displayName(newProfile.getDisplayName())
                .outgoingCategories(existingProfile.getOutgoingCategories())
                .status(newProfile.getStatus())
                .data(newProfile.getData())
                .mapped(newProfile.isMapped())
                .priority(newProfile.getPriority())
                .updatedAt(System.currentTimeMillis())
                .build();
    }

    public static String getCategoryKey(String serviceType, String categoryId) {
        return getNormalizedValue(String.join("_", serviceType, categoryId));
    }

    public static String getTemplateName(String serviceType, String categoryId) {
        return getNormalizedValue(String.join(".", Objects.isNull(serviceType)
                ? LexaUtils.Keys.DATA
                : serviceType.toLowerCase(), categoryId.toLowerCase())
        );
    }

    public static String getCategorySourceIndex(String serviceType, String categoryId) {
        return getNormalizedValue(String.join(".",
                LexaUtils.Keys.SOURCE_INDEX, serviceType,
                categoryId));
    }

    public static String getCategorySourceType(String serviceType, String categoryId) {
        return getNormalizedValue(String.join(".",
                LexaUtils.Keys.SOURCE_MAPPING, serviceType,
                categoryId));
    }

    public static String getCategorySourceType(CategoryIngestionRequest categoryIngestionRequest) {
        return getCategorySourceType(categoryIngestionRequest.getServiceType(),
                categoryIngestionRequest.getRootCategoryId());
    }

    public static String getManifestSourceIndex(String manifestId) {
        return getNormalizedValue(String.join(".",
                LexaUtils.Keys.SOURCE_INDEX, manifestId));
    }

    public static String getManifestSourceType(String manifestId) {
        return getNormalizedValue(String.join(".",
                LexaUtils.Keys.SOURCE_MAPPING, manifestId));
    }

    public static String getManifestSourceType(ManifestIngestionRequest manifestIngestionRequest) {
        return getManifestSourceType(manifestIngestionRequest.getManifestId());
    }

    public static final class Keys {

        public static final String STATUS_ATTRIBUTE = "status";
        public static final String UPDATED_AT_ATTRIBUTE = "updatedAt";
        public static final String ZONE_ID = "Asia/Kolkata";
        public static final String MESSAGE_STRING = "message";
        static final String SCHEMA_INDEX = "catalogue.schemas";
        static final String SCHEMA_MAPPING = "catalogue.schemas.mapping";
        static final String SCHEMA_FILE_PATH = "categories/category.json";
        public static final String SOURCE_INDEX = "catalogue.sources";
        static final String SOURCE_MAPPING = "catalogue.sources.mapping";
        static final String DATA = "data";

        private Keys() {
            throw new IllegalStateException("Keys Utility Class");
        }
    }

    public static final class Fields {

        public static final String SERVICE_TYPE = "categoryGroup";
        public static final String ENTITY_TYPE = "entityType";

        private Fields() {
            throw new IllegalStateException("Fields Utility Class");
        }
    }
}
