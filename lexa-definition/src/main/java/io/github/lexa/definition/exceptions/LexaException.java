package io.github.lexa.definition.exceptions;

import lombok.Getter;

import java.util.Collections;
import java.util.Map;

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
@Getter
public class LexaException extends RuntimeException{

    private final LexaError errorCode;
    private final Map<String, Object> context;

    private LexaException(LexaError errorCode, Map<String, Object> context) {
        super();
        this.errorCode = errorCode;
        this.context = context;
    }

    private LexaException(Throwable cause, LexaError errorCode) {
        super(cause);
        this.errorCode = errorCode;
        this.context = Collections.singletonMap("message", cause.getLocalizedMessage());
    }

    private LexaException(Throwable cause, String message, LexaError errorCode) {
        super(cause);
        this.errorCode = errorCode;
        this.context = Collections.singletonMap("message", message);
    }

    private LexaException(LexaError errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.context = Collections.singletonMap("message", message);
    }

    public static LexaException propagate(LexaError errorCode, Throwable t) {
        return new LexaException(t, errorCode);
    }

    public static LexaException propagate(LexaError errorCode, String message,
                                                      Throwable t) {
        return new LexaException(t, message, errorCode);
    }

    public static LexaException error(LexaError errorCode, Map<String, Object> context) {
        return new LexaException(errorCode, context);
    }

    public static LexaException error(LexaError errorCode, String message) {
        return new LexaException(errorCode, message);
    }

}
