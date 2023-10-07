package com.example.arclesocialapp.annotation;

import lombok.Setter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

@Setter
public class LaterThanYearValidator implements ConstraintValidator<LaterThanYear, LocalDate> {

    private int value;

    @Override
    public void initialize(LaterThanYear constraintAnnotation) {
        this.value = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
        return date.getYear() >= value;
    }
}
