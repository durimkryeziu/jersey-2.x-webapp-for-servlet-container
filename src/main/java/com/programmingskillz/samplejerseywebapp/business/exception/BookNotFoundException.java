package com.programmingskillz.samplejerseywebapp.business.exception;

/**
 * @author Durim Kryeziu
 */
public class BookNotFoundException extends RuntimeException {

  public BookNotFoundException(String message) {
    super(message);
  }
}
