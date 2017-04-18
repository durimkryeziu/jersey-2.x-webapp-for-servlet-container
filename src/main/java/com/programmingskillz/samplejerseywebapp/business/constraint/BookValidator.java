package com.programmingskillz.samplejerseywebapp.business.constraint;

import com.programmingskillz.samplejerseywebapp.business.domain.Book;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Durim Kryeziu
 */
public class BookValidator implements ConstraintValidator<ValidBookToUpdate, Book> {

    @Override
    public void initialize(ValidBookToUpdate validBookToUpdate) {

    }

    @Override
    public boolean isValid(Book book, ConstraintValidatorContext constraintValidatorContext) {

        if (book == null) {
            return true; // Bean Validation specification recommends to consider null values as being valid
        }

        return book.getId() != null;
    }
}