package com.example.arclesocialapp.validator;

import com.example.arclesocialapp.annotation.LaterThanYear;
import lombok.Setter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

@Setter
public class LaterThanYearWithTimeValidator implements ConstraintValidator<LaterThanYear, LocalDateTime> {

    private int value;

    @Override
    public void initialize(LaterThanYear constraintAnnotation) {
        this.value = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(LocalDateTime dateTime, ConstraintValidatorContext context) {
        if (dateTime == null) return false;
        return dateTime.getYear() >= value;
    }
}
