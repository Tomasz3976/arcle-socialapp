package com.example.arclesocialapp.validation

import com.example.arclesocialapp.domain.User
import com.example.arclesocialapp.enumeration.CharacterTrait
import com.example.arclesocialapp.data.TestDataProvider
import spock.lang.Specification
import spock.lang.Subject

import javax.validation.Validation
import javax.validation.Validator
import java.time.LocalDate

class UserValidationTest extends Specification {

    @Subject
    User user

    Validator validator

    def setup() {
        user = TestDataProvider.getUser()
        validator = Validation.buildDefaultValidatorFactory().getValidator()
    }

    def 'test should trigger violation when username is blank or invalid size'() {
        given: 'username setting'
            user.setUsername(username)

        when: 'passing incorrect username'
            def violations = validator.validate(user)

        then: 'violations should be triggered'
            violations.size() > 0

        where: 'incorrect parameters'
            username << [null, "", " ", "     ", "paul", "JohnMarcusGeorgeOscar"]
    }

    def 'test should not trigger any violation when username is correct'() {
        given: 'username setting'
            user.setUsername(username)

        when: 'passing correct username'
            def violations = validator.validate(user)

        then: 'no violations'
            violations.isEmpty()

        where: 'correct parameters'
            username << ["RobertJackWow", "annie992", "22hjdhdgg32423##@"]
    }

    def 'test should trigger violation when password has wrong pattern'() {
        given: 'password setting'
            user.setPassword(password)

        when: 'passing incorrect password'
            def violations = validator.validate(user)

        then: 'violations should be triggered'
            violations.size() > 0

        where: 'incorrect parameters'
            password << [null, "", " ", "password", "StrongPass", "pass123",
                        "Pass1455", "OOO", "88", "RTP44", "#&", "a%&", "A**", "5^"]
    }

    def 'test should not trigger any violation when password is correct'() {
        given: 'password setting'
            user.setPassword(password)

        when: 'passing correct password'
            def violations = validator.validate(user)

        then: 'no violations'
            violations.isEmpty()

        where: 'correct parameters'
            password << ["StrongPassword145%", "aaBB33%%dr", "32*fGf777^8", "##3FVff3322", "@**ffB664sw"]
    }

    def 'test should trigger violation when date of birth is too early, too late or null'() {
        given: 'date of birth setting'
            user.setDateOfBirth(dateOfBirth)

        when: 'passing incorrect date of birth'
            def violations = validator.validate(user)

        then: 'violations should be triggered'
            violations.size() > 0

        where: 'incorrect parameters'
            dateOfBirth << [null,
                            LocalDate.of(1899, 4, 17),
                            LocalDate.now().plusYears(3). plusMonths(5)]
    }

    def 'test should not trigger any violation when date of birth is correct'() {
        given: 'date of birth setting'
            user.setDateOfBirth(dateOfBirth)

        when: 'passing correct date of birth'
            def violations = validator.validate(user)

        then: 'no violations'
            violations.isEmpty()

        where: 'correct parameters'
            dateOfBirth << [LocalDate.of(2011, 7, 2),
                            LocalDate.of(1979, 3, 19),
                            LocalDate.of(2001, 11, 6)]
    }

    def 'test should trigger violation when email has wrong pattern'() {
        given: 'email setting'
            user.setEmail(email)

        when: 'passing incorrect email'
            def violations = validator.validate(user)

        then: 'violations should be triggered'
            violations.size() > 0

        where: 'incorrect parameters'
            email << [null, "", " ", "alexfergusson.o2.com", "willywonka.@outlook.com", ".jerry.springer43@meta.com",
                      "valeria67@o2.com.", "conorbryant543@.gmail.com", "danielhernandez@.com"]
    }

    def 'test should not trigger any violation when email is correct'() {
        given: 'email setting'
            user.setEmail(email)

        when: 'passing correct email'
            def violations = validator.validate(user)

        then: 'no violations'
            violations.isEmpty()

        where: 'correct parameters'
            email << ["tommyfury@outlook.com", "helena.dwayne@o2.com",
                      "robbie-clout@yahoo.com", "richardbisping@yahoo.co.in", "daniel_spoon@gmail.com"]
    }

    def 'test should trigger violation when phone number has wrong pattern'() {
        given: 'phone number setting'
            user.setPhoneNumber(phoneNumber)

        when: 'passing incorrect phone number'
            def violations = validator.validate(user)

        then: 'violations should be triggered'
            violations.size() > 0

        where: 'incorrect parameters'
            phoneNumber << [null, "", " ", "         ", "1", "78424556", "9834567789", "676276376322636328", "a576hf"]
    }

    def 'test should not trigger any violation when phone number is correct'() {
        given: 'phone number setting'
            user.setPhoneNumber(phoneNumber)

        when: 'passing correct phone number'
            def violations = validator.validate(user)

        then: 'no violations'
            violations.isEmpty()

        where: 'correct parameters'
            phoneNumber << ["503429887", "703419876"]
    }

    def 'test should trigger violation when description is too long or empty'() {
        given: 'description setting'
            user.setDescription(description)

        when: 'passing incorrect description'
            def violations = validator.validate(user)

        then: 'violations should be triggered'
            violations.size() > 0

        where: 'incorrect parameters'
            description << [null
                            ,
                            ""
                            ,
                            " "
                            ,
                            "I'm the ultimate party maker, and here's why I light up every event!" +
                            " First, I bring an infectious energy that's impossible to resist." +
                            " I thrive on the excitement of a lively gathering," +
                            " and I make sure everyone else feels the same way." +
                            " When I hit the dance floor, it's not just a dance," +
                            " it's a performance, and I'll have everyone" +
                            " grooving to the beat in no time."]
    }

    def 'test should not trigger any violation when description is correct'() {
        given: 'description setting'
            user.setDescription(description)

        when: 'passing correct description'
            def violations = validator.validate(user)

        then: 'no violations'
            violations.isEmpty()

        where: 'correct parameters'
            description << ["I bring the energy, the charisma, and the flair to any event." +
                            " Dancing, mingling, and keeping the good times rolling are my specialties." +
                            " I make connections, create excitement, and ensure every moment is unforgettable."
                            ,
                            "My own description guys!"]
    }

    def 'test should trigger violation when photo path is blank'() {
        given: 'photo path setting'
            user.setPhoto(photoPath)

        when: 'passing incorrect photo path'
            def violations = validator.validate(user)

        then: 'violations should be triggered'
            violations.size() > 0

        where: 'incorrect parameters'
            photoPath << [null, "", " "]
    }

    def 'test should not trigger any violation when photo path is correct'() {
        given: 'photo path setting'
            user.setPhoto(photoPath)

        when: 'passing correct photo path'
            def violations = validator.validate(user)

        then: 'no violations'
            violations.isEmpty()

        where: 'correct parameters'
            photoPath << ["user16_06_08_2021_954", "user16_09_10_2022_035"]
    }

    def 'test should trigger violation when number of character traits is incorrect'() {
        given: 'character traits setting'
            def traits = characterTraits
            user.setCharacterTraits(traits)

        when: 'passing incorrect character traits'
            def violations = validator.validate(user)

        then: 'violations should be triggered'
            violations.size() > 0

        where: 'incorrect parameters'
            characterTraits << [null,
                                [] as Set,
                                [CharacterTrait.LEADING] as Set,
                                [CharacterTrait.COOPERATIVE, CharacterTrait.FUNNY] as Set,
                                [CharacterTrait.KIND, CharacterTrait.CALM, CharacterTrait.SHY, CharacterTrait.WITTY]
                                        as Set]
    }

    def 'test should not trigger any violation when number of character traits is correct'() {
        given: 'character traits setting'
            def traits = characterTraits
            user.setCharacterTraits(traits)

        when: 'passing correct character traits'
            def violations = validator.validate(user)

        then: 'no violations'
            violations.isEmpty()

        where: 'correct parameters'
            characterTraits << [[CharacterTrait.COOPERATIVE, CharacterTrait.FUNNY, CharacterTrait.ADAPTIVE] as Set,
                                [CharacterTrait.ENERGETIC, CharacterTrait.STOIC, CharacterTrait.TOLERANT] as Set]
    }
}
