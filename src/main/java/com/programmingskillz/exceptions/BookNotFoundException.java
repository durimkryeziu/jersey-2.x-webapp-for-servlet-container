package com.programmingskillz.exceptions;

/**
 * @author Durim Kryeziu
 */
public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(String message) {
        super(message);
    }
}