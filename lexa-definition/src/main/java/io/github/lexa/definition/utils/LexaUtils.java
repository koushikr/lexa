package io.github.lexa.definition.utils;

import io.github.lexa.definition.exceptions.LexaCode;
import io.github.lexa.definition.exceptions.LexaException;

import java.io.InputStream;
import java.util.Collections;

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
public class LexaUtils {

    private LexaUtils(){
        throw new IllegalStateException("No utils classes shoudl be required");
    }

    public static <T> T getSource(String path, Class<T> klass) {
        try {
            InputStream inputStream = ResourceUtils.getResource(path);
            return SerDe.mapper().readValue(inputStream, klass);
        } catch (Exception e) {
            throw LexaException.error(LexaCode.SER_DE_ERROR,
                    Collections.singletonMap("message", "Can't SerDe from path into the klass!"));
        }
    }

}
