package com.programmingskillz.samplejerseywebapp.business.constraint;

import org.apache.commons.validator.routines.ISBNValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Durim Kryeziu
 */
public class IsbnValidator implements ConstraintValidator<ValidIsbn, String> {

    @Override
    public void initialize(ValidIsbn validIsbn) {

    }

    @Override
    public boolean isValid(String isbnCode, ConstraintValidatorContext constraintValidatorContext) {

        return ISBNValidator.getInstance().isValid(isbnCode);
    }
}