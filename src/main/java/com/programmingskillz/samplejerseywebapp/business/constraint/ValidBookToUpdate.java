package com.programmingskillz.samplejerseywebapp.business.constraint;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

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
