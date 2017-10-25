package com.programmingskillz.samplejerseywebapp.config.providers;

import com.programmingskillz.samplejerseywebapp.business.exception.BookNotFoundException;
import com.programmingskillz.samplejerseywebapp.business.exception.ErrorResponse;
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
public class BookNotFoundExceptionMapper implements ExceptionMapper<BookNotFoundException> {

  private static final Logger LOGGER = LoggerFactory.getLogger(BookNotFoundExceptionMapper.class);

  @Override
  public Response toResponse(BookNotFoundException exception) {

    LOGGER.error("BookNotFoundException:", exception);
    LOGGER.debug("Constructing Error Response for: [{}]", exception.toString());
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
