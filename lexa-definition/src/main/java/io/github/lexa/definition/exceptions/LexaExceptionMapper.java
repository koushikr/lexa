package io.github.lexa.definition.exceptions;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

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
@Provider
@Slf4j
public class LexaExceptionMapper implements ExceptionMapper<LexaException> {
    @Override
    public Response toResponse(LexaException e) {
        log.error("ERROR", e);
        return Response.serverError()
                .status(e.getErrorCode().responseCode())
                .entity(ImmutableMap.of("error", e.getLocalizedMessage() != null ? e.getLocalizedMessage()
                        : "There is an error trying to process your request."))
                .build();
    }
}
