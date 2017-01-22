package com.programmingskillz.providers;

import com.programmingskillz.exceptions.ErrorResponse;

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

    @Override
    public Response toResponse(WebApplicationException exception) {
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
        for (Map.Entry<String, List<Object>> entry : headers.entrySet()) {
            List<Object> headerValues = entry.getValue();
            if (headerValues.size() == 1) {
                responseBuilder.header(entry.getKey(), headerValues.get(0));
            } else {
                responseBuilder.header(entry.getKey(), headerValues);
            }
        }

        return responseBuilder.build();
    }
}