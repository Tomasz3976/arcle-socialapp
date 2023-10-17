package com.example.arclesocialapp.annotation;

import com.example.arclesocialapp.validator.LaterThanYearValidator;
import com.example.arclesocialapp.validator.LaterThanYearWithTimeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {LaterThanYearValidator.class, LaterThanYearWithTimeValidator.class})
public @interface LaterThanYear {

    String message() default "Date must be later than year {value}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int value();
}
