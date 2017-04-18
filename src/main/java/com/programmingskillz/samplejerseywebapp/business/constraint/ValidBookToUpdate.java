package com.programmingskillz.samplejerseywebapp.business.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Durim Kryeziu
 */
@Target(PARAMETER)
@Retention(RUNTIME)
@Constraint(validatedBy = BookValidator.class)
@Documented
public @interface ValidBookToUpdate {

    String message() default "{ValidBookToUpdate.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}