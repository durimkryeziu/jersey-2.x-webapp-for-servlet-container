package com.programmingskillz.providers;

import com.programmingskillz.exceptions.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;
import java.util.Map;

/**
 * @author Durim Kryeziu
 */
@Provider
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebApplicationExceptionMapper.class);

    @Override
    public Response toResponse(WebApplicationException exception) {

        LOGGER.error("WebApplicationException:", exception);
        LOGGER.debug("Constructing Error Response for: [{}]", exception.toString());
        ErrorResponse errorResponse = new ErrorResponse();

        Response exceptionResponse = exception.getResponse();
        Response.StatusType statusInfo = exceptionResponse.getStatusInfo();

        errorResponse.setCode(statusInfo.getStatusCode());
        errorResponse.setStatus(statusInfo.getReasonPhrase());
        errorResponse.setMessage(exception.getMessage());

        Response.ResponseBuilder responseBuilder = Response.status(statusInfo)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON);

        MultivaluedMap<String, Object> headers = exceptionResponse.getHeaders();
        if (headers.size() > 0) {
            LOGGER.debug("Adding headers:");
            for (Map.Entry<String, List<Object>> entry : headers.entrySet()) {
                String headerKey = entry.getKey();
                List<Object> headerValues = entry.getValue();
                LOGGER.debug("  {} -> {}", headerKey, headerValues);
                if (headerValues.size() == 1) {
                    responseBuilder.header(headerKey, headerValues.get(0));
                } else {
                    responseBuilder.header(headerKey, headerValues);
                }
            }
        }

        return responseBuilder.build();
    }
}