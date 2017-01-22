package com.programmingskillz.providers;

import com.programmingskillz.exceptions.BookNotFoundException;
import com.programmingskillz.exceptions.ErrorResponse;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author Durim Kryeziu
 */
@Provider
public class BookNotFoundExceptionMapper implements ExceptionMapper<BookNotFoundException> {

    @Override
    public Response toResponse(BookNotFoundException exception) {
        Response.Status status = Response.Status.NOT_FOUND;

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(status.getStatusCode());
        errorResponse.setStatus(status.getReasonPhrase());
        errorResponse.setMessage(exception.getMessage());

        return Response.status(status)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}