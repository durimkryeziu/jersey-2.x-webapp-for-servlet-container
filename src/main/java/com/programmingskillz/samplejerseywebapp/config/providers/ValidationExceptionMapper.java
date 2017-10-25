package com.programmingskillz.samplejerseywebapp.config.providers;

import com.programmingskillz.samplejerseywebapp.business.exception.ErrorResponse;
import org.glassfish.jersey.server.validation.ValidationError;
import org.glassfish.jersey.server.validation.internal.ValidationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author Durim Kryeziu
 */
@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ValidationExceptionMapper.class);

  @Override
  public Response toResponse(ValidationException exception) {

    LOGGER.error("{}:", exception.getClass().getTypeName(), exception);
    LOGGER.debug("Constructing Error Response for: [{}]", exception.toString());

    if (exception instanceof ConstraintViolationException) {
      ConstraintViolationException violationException = (ConstraintViolationException) exception;

      Response.Status status = ValidationHelper.getResponseStatus(violationException);

      List<ValidationError>
          validationErrors =
          ValidationHelper.constraintViolationToValidationErrors(violationException);

      return Response.status(status)
          .entity(new GenericEntity<List<ValidationError>>(validationErrors) {
          })
          .type(MediaType.APPLICATION_JSON)
          .build();
    } else {
      Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;

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
}
