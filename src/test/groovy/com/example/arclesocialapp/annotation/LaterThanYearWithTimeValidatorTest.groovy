package com.example.arclesocialapp.annotation

import com.example.arclesocialapp.validator.LaterThanYearWithTimeValidator
import spock.lang.Specification
import spock.lang.Subject

import javax.validation.ConstraintValidatorContext
import java.time.LocalDateTime

class LaterThanYearWithTimeValidatorTest extends Specification {

    @Subject
    LaterThanYearWithTimeValidator validator

    ConstraintValidatorContext validatorContext

    def setup() {
        validator = new LaterThanYearWithTimeValidator()
        validatorContext = Mock()
    }

    def 'test should return true when date is later than given year'() {
        given: 'year setting'
            validator.setValue(2010)

        expect: 'passing correct date'
            validator.isValid(date, validatorContext)

        where: 'correct parameters'
            date << [LocalDateTime.of(2010, 6, 8, 16, 30),
                     LocalDateTime.of(2011, 8, 23, 12, 0),
                     LocalDateTime.of(2012, 12, 5, 21, 40)]
    }

    def 'test should return false when date is before given year'() {
        given: 'year setting'
            validator.setValue(2010)

        expect: 'passing incorrect date'
            !validator.isValid(date, validatorContext)

        where: 'incorrect parameters'
            date << [LocalDateTime.of(2009, 12, 31, 18, 0),
                     LocalDateTime.of(2009, 2, 19, 19, 20),
                     LocalDateTime.of(1990, 4, 2, 14, 15)]
    }

    def 'test should return false when date is null'() {
        given: 'year setting'
            validator.setValue(2010)

        expect: 'passing null'
            !validator.isValid(null, validatorContext)
    }
}
