package com.example.arclesocialapp.annotation

import spock.lang.Specification
import spock.lang.Subject

import javax.validation.ConstraintValidatorContext
import java.time.LocalDate

class LaterThanYearValidatorTest extends Specification {

    @Subject
    LaterThanYearValidator validator

    ConstraintValidatorContext validatorContext

    def setup() {
        validator = new LaterThanYearValidator()
        validatorContext = Mock()
    }

    def 'test should return true when date is later than given year'() {
        given: 'year setting'
            validator.setValue(1950)

        expect: 'passing correct date'
            validator.isValid(date, validatorContext)

        where: 'correct parameters'
            date << [LocalDate.of(1950, 4, 12),
                     LocalDate.of(1951, 2, 25),
                     LocalDate.of(1952, 11, 6)]
    }

    def 'test should return false when date is before given year'() {
        given: 'year setting'
            validator.setValue(1950)

        expect: 'passing incorrect date'
            !validator.isValid(date, validatorContext)

        where: 'incorrect parameters'
            date << [LocalDate.of(1949, 12, 31),
                     LocalDate.of(1949, 3, 11),
                     LocalDate.of(1929, 1, 29)]
    }

    def 'test should return false when date is null'() {
        given: 'year setting'
            validator.setValue(1950)

        expect: 'passing null'
            !validator.isValid(null, validatorContext)
    }
}
