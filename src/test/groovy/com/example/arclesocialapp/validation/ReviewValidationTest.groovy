package com.example.arclesocialapp.validation

import com.example.arclesocialapp.data.TestDataProvider
import com.example.arclesocialapp.domain.Review
import spock.lang.Specification
import spock.lang.Subject

import javax.validation.Validation
import javax.validation.Validator

class ReviewValidationTest extends Specification {

    @Subject
    Review review

    Validator validator

    def setup() {
        review = TestDataProvider.getReview()
        validator = Validation.buildDefaultValidatorFactory().getValidator()
    }

    def 'test should trigger violation when author id is negative, zero or null'() {
        given: 'author id setting'
            def id = authorId
            review.setAuthorId(id)

        when: 'passing incorrect author id'
            def violations = validator.validate(review)

        then: 'violations should be triggered'
            violations.size() > 0

        where: 'incorrect parameters'
            authorId << [null, -15L, -7L, 0L]
    }

    def 'test should not trigger any violation when author id is correct'() {
        given: 'author id setting'
            def id = authorId
            review.setAuthorId(id)

        when: 'passing correct author id'
            def violations = validator.validate(review)

        then: 'no violations'
            violations.isEmpty()

        where: 'correct parameters'
            authorId << [57L, 8L]
    }

    def 'test should trigger violation when rating is too low, too high or null'() {
        given: 'rating setting'
            def rating = reviewRating
            review.setRating(rating)

        when: 'passing incorrect rating'
            def violations = validator.validate(review)

        then: 'violations should be triggered'
            violations.size() > 0

        where: 'incorrect parameters'
            reviewRating << [null, -3 as Byte, 0 as Byte, 11 as Byte]
    }

    def 'test should not trigger any violation when rating is correct'() {
        given: 'rating setting'
            def rating = reviewRating
            review.setRating(rating)

        when: 'passing correct rating'
            def violations = validator.validate(review)

        then: 'no violations'
            violations.isEmpty()

        where: 'correct parameters'
            reviewRating << [1 as Byte, 6 as Byte, 10 as Byte]
    }

    def 'test should trigger violation when description is too long or empty'() {
        given: 'description setting'
            review.setDescription(description)

        when: 'passing incorrect description'
            def violations = validator.validate(review)

        then: 'violations should be triggered'
            violations.size() > 0

        where: 'incorrect parameters'
            description << [null
                            ,
                            ""
                            ,
                            " "
                            ,
                            "Paul's energy is simply contagious, and it's impossible not to get caught up" +
                            " in the excitement when he's around. He knows how to turn an ordinary gathering" +
                            " into an extraordinary experience. His charisma is magnetic, drawing everyone in" +
                            " and making them feel like they're part of something special. What impressed me the" +
                            " most was Mike's ability to make people feel at ease."]
    }

    def 'test should not trigger any violation when description is correct'() {
        given: 'description setting'
            review.setDescription(description)

        when: 'passing correct description'
            def violations = validator.validate(review)

        then: 'no violations'
            violations.isEmpty()

        where: 'correct parameters'
            description << ["If you're ever in need of a party host who can create good" +
                            " moments and keep the good times rolling, Jacob is your guy."
                            ,
                            "This guy is crazy!"]
    }
}
