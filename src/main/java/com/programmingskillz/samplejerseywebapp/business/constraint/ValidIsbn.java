package com.programmingskillz.samplejerseywebapp.business.constraint;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Durim Kryeziu
 */
@NotNull(message = "{book.isbn.null}")
@Size(min = 10, max = 17, message = "{book.isbn.size}")
@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = IsbnValidator.class)
@Documented
public @interface ValidIsbn {

  String message() default "{ValidIsbn.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
