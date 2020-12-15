package io.github.lexa.core.exceptions;

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
public enum  LexaCode implements LexaError{

    SER_DE_ERROR {
        @Override
        public int responseCode() {
            return 400;
        }
    },

    CATEGORY_SCHEMA_EXISTS {
        @Override
        public int responseCode() {
            return 400;
        }
    },

    CATEGORY_ALREADY_EXISTS {
        @Override
        public int responseCode() {
            return 400;
        }
    },

    CATEGORY_SCHEMA_MISSING {
        @Override
        public int responseCode() {
            return 400;
        }
    },

    CATEGORY_DOES_NOT_EXIST {
        @Override
        public int responseCode() {
            return 400;
        }
    },

    CYCLIC_CATEGORY_ERROR {
        @Override
        public int responseCode() {
            return 400;
        }
    },

    MANIFEST_SCHEMA_MISSING {
        @Override
        public int responseCode() {
            return 400;
        }
    },

    MANIFEST_DOES_NOT_EXIST {
        @Override
        public int responseCode() {
            return 400;
        }
    },

    MANIFEST_SCHEMA_EXISTS {
        @Override
        public int responseCode() {
            return 400;
        }
    },

    MANIFEST_ALREADY_EXISTS {
        @Override
        public int responseCode() {
            return 400;
        }
    }
}
