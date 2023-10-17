package com.example.arclesocialapp.validation

import com.example.arclesocialapp.data.TestDataProvider
import com.example.arclesocialapp.domain.Happening
import com.example.arclesocialapp.domain.Spot
import com.example.arclesocialapp.enumeration.HappeningStatus
import spock.lang.Specification
import spock.lang.Subject

import javax.validation.Validation
import javax.validation.Validator
import java.time.LocalDateTime

class HappeningValidationTest extends Specification {

    @Subject
    Happening happening

    Validator validator

    def setup() {
        happening = TestDataProvider.getHappening()
        validator = Validation.buildDefaultValidatorFactory().getValidator()
    }

    def 'test should trigger violation when happening spot is null'() {
        given: 'spot setting'
            happening.setSpot(null)

        when: 'passing incorrect spot'
            def violations = validator.validate(happening)

        then: 'violations should be triggered'
            violations.size() > 0
    }

    def 'test should not trigger any violation when happening spot is correct'() {
        given: 'spot setting'
            happening.setSpot(Spot.builder().build())

        when: 'passing correct spot'
            def violations = validator.validate(happening)

        then: 'no violations'
            violations.isEmpty()
    }

    def 'test should trigger violation when start time is too early or null'() {
        given: 'start time setting'
            happening.setStartTime(startTime)

        when: 'passing incorrect start time'
            def violations = validator.validate(happening)

        then: 'violations should be triggered'
            violations.size() > 0

        where: 'incorrect parameters'
            startTime << [null,
                          LocalDateTime.of(2019, 12, 31, 20, 0),
                          LocalDateTime.of(2014, 3, 21, 16, 45)]
    }

    def 'test should not trigger any violation when start time is correct'() {
        given: 'start time setting'
            happening.setStartTime(startTime)

        when: 'passing correct start time'
            def violations = validator.validate(happening)

        then: 'no violations'
            violations.isEmpty()

        where: 'correct parameters'
            startTime << [LocalDateTime.of(2020, 1, 1, 10, 0),
                          LocalDateTime.of(2021, 6, 24, 19, 15),
                          LocalDateTime.of(2022, 2, 11, 13, 0)]
    }

    def 'test should trigger violation when description is too long or empty'() {
        given: 'description setting'
            happening.setDescription(description)

        when: 'passing incorrect description'
            def violations = validator.validate(happening)

        then: 'violations should be triggered'
            violations.size() > 0

        where: 'incorrect parameters'
            description << [null
                            ,
                            ""
                            ,
                            " "
                            ,
                            "This event is all about the ladies, and we're bringing together a fabulous crowd of" +
                            " fun, fearless, and fantastic women. But this isn't just your average girls' night out." +
                            " It's a unique party featuring a fleet of beautiful cars that'll have your heart racing." +
                            " We're talking about sleek, vintage classics and high-performance modern machines." +
                            " You'll have the chance to admire these automotive marvels up close and personal," +
                            " and if you're lucky, even take a ride in one. It's the perfect blend of elegance" +
                            " and exhilaration. There won't be any alcohol at this event, but we've got a delightful" +
                            " spread of mocktails, gourmet snacks, and desserts that'll satisfy your taste buds." +
                            " The dance floor is yours to own, with the latest beats and your favorite tunes to keep" +
                            " you grooving. So, if you're looking for a night of girl power, stylish cars, delectable" +
                            " treats, and loads of fun, this is the place to be. Join us for an unforgettable evening" +
                            " celebrating the awesomeness of women and the beauty of extraordinary automobiles."]
    }

    def 'test should not trigger any violation when description is correct'() {
        given: 'description setting'
            happening.setDescription(description)

        when: 'passing correct description'
            def violations = validator.validate(happening)

        then: 'no violations'
            violations.isEmpty()

        where: 'correct parameters'
            description << ["There won't be any alcohol at this gathering, but we've got a delightful array of" +
                            " artisanal, non-alcoholic cocktails and a spread of delectable finger foods that'll" +
                            " satisfy every craving. It's all about indulging taste buds and savoring the moment."
                            ,
                            "Great party my guys!"]
    }

    def 'test should trigger violation when happening status is null'() {
        given: 'status setting'
            happening.setStatus(null)

        when: 'passing incorrect status'
            def violations = validator.validate(happening)

        then: 'violations should be triggered'
            violations.size() > 0
    }

    def 'test should not trigger any violation when happening status is correct'() {
        given: 'status setting'
            happening.setStatus(status)

        when: 'passing correct status'
            def violations = validator.validate(happening)

        then: 'no violations'
            violations.isEmpty()

        where: 'correct parameters'
            status << [HappeningStatus.SCHEDULED, HappeningStatus.ONGOING, HappeningStatus.FINISHED]
    }
}
