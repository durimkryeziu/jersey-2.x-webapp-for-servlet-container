package com.programmingskillz.providers;

import com.programmingskillz.exception.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author Durim Kryeziu
 */
@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericExceptionMapper.class);

    @Override
    public Response toResponse(Throwable exception) {

        LOGGER.error("{}:", exception.getClass().getTypeName(), exception);
        LOGGER.debug("Constructing Error Response for: [{}]", exception.toString());
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(status.getStatusCode());
        errorResponse.setStatus(status.getReasonPhrase());

        StringBuilder sb = new StringBuilder().append(exception.getMessage());

        // try-with-resources mechanism suppresses exceptions thrown as a result of closing resources
        // if there was also an exception thrown in the try block
        for (Throwable t : exception.getSuppressed()) {
            sb.append("\n").append(t.getMessage());
        }

        errorResponse.setMessage(sb.toString());

        return Response.status(status)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}