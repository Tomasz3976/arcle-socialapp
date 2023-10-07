package com.example.arclesocialapp.annotation

import org.mockito.Mock
import spock.lang.Specification

import javax.validation.ConstraintValidatorContext
import java.time.LocalDate

class LaterThanYearValidatorTest extends Specification {

    @Mock
    private ConstraintValidatorContext validatorContext

    private LaterThanYearValidator validator

    void setup() {
        validator = new LaterThanYearValidator()
    }

    def "test should return true if date is later than given year"() {
        given:
            validator.setValue(1950)

        expect:
            validator.isValid(date, validatorContext)

        where:
            date << [LocalDate.of(1950, 4, 12),
                     LocalDate.of(1951, 2, 25),
                     LocalDate.of(1952, 11, 6)]
    }

    def "test should return false if date is before given year"() {
        given:
            validator.setValue(1950)

        expect:
            !validator.isValid(date, validatorContext)

        where:
            date << [LocalDate.of(1949, 12, 31),
                     LocalDate.of(1949, 3, 11),
                     LocalDate.of(1929, 1, 29)]
    }
}
