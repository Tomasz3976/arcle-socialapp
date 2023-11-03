package com.example.arclesocialapp.repository

import com.example.arclesocialapp.data.TestDataProvider
import com.example.arclesocialapp.domain.User
import com.example.arclesocialapp.enumeration.CharacterTrait
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.dao.DataIntegrityViolationException
import spock.lang.Specification
import spock.lang.Subject

import javax.validation.ConstraintViolationException
import java.time.LocalDate

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class UserRepositoryTest extends Specification {

    @Subject
    @Autowired
    UserRepository repository

    User user

    def setup() {
        user = TestDataProvider.getUser()
    }

    def 'test should throw an exception when saved user has already present username'() {
        given:
            'defining username'
            def username = "dolphin884"

            'username setting'
            user.setUsername(username)

            'saving user'
            repository.save(user)

            'creating new user with duplicated username'
            def secondUser = User.builder()
                    .username(username)
                    .password("VeryStrongPassword65#")
                    .dateOfBirth(LocalDate.of(2001, 5, 23))
                    .email("dolphin7744@outlook.com")
                    .phoneNumber("577440921")
                    .description(
                            "I'm Dolphin, and I'm all about making a splash in the world. I'm a free" +
                            " spirit who thrives in the water, and you can often find me by the ocean," +
                            " riding the waves or swimming with the dolphins."
                    )
                    .photo("user54_wow_34.jpg")
                    .characterTraits(
                            [CharacterTrait.KIND,
                             CharacterTrait.ORGANIZED,
                             CharacterTrait.STOIC] as Set
                    )
                    .build()

        when: 'saving user with duplicated username'
            repository.save(secondUser)

        then: 'exception should be thrown'
            def exception = thrown(DataIntegrityViolationException)
            exception.cause.getClass() == org.hibernate.exception.ConstraintViolationException
            exception.cause.cause.getClass() == JdbcSQLIntegrityConstraintViolationException
            exception.cause.cause.message.contains("Unique index or primary key violation")
    }

    def 'test should throw an exception when saved user has blank username'() {
        given: 'username setting'
            user.setUsername(username)

        when: 'saving user with blank username'
            repository.save(user)

        then: 'exception should be thrown'
            def exception = thrown(ConstraintViolationException)
            exception.message.contains("Username field cannot be empty")

        where: 'incorrect parameters'
            username << [null, "", " ", "     "]
    }

    def 'test should throw an exception when saved user has invalid username size'() {
        given: 'username setting'
            user.setUsername(username)

        when: 'saving user with wrong sized username'
            repository.save(user)

        then: 'exception should be thrown'
            def exception = thrown(ConstraintViolationException)
            exception.message.contains("Username field should have length 5-20 letters")

        where: 'incorrect parameters'
            username << ["john", "MyBeautifulDarkTwistedFantasy10"]
    }

    def 'test should not throw any exception when saved user has correct username'() {
        given: 'username setting'
            user.setUsername(username)

        when: 'saving user with correct username'
            repository.save(user)

        then: 'no exceptions'
            noExceptionThrown()

        where: 'correct parameters'
            username << ["MargotBrown48", "jordi993", "547382ffdhdud"]
    }

    def 'test should throw an exception when saved user has empty password'() {
        given: 'password setting'
            user.setPassword(password)

        when: 'saving user with empty password'
            repository.save(user)

        then: 'exception should be thrown'
            def exception = thrown(ConstraintViolationException)
            exception.message.contains("Password field cannot be empty")

        where: 'incorrect parameters'
            password << [null, ""]
    }

    def 'test should throw an exception when saved user has password with wrong pattern'() {
        given: 'password setting'
            user.setPassword(password)

        when: 'saving user with wrong pattern password'
            repository.save(user)

        then: 'exception should be thrown'
            def exception = thrown(ConstraintViolationException)
            exception.message.contains("Password must contain at least 10 characters, at least one lowercase letter," +
                    " at least one uppercase letter, at least one digit and at least one special character")

        where: 'incorrect parameters'
            password << [" ", "ravioli", "BigElephant", "dog773", "Like77232",
                         "HDS", "22002", "POP675", "*#", "ww*@", "IUY%%", "234!!"]
    }

    def 'test should not throw any exception when saved user has correct password'() {
        given: 'password setting'
            user.setPassword(password)

        when: 'saving user with correct password'
            repository.save(user)

        then: 'no exceptions'
            noExceptionThrown()

        where: 'correct parameters'
            password << ["EverydayRoutine4444@", "iyuAE54*##ddd", "111@asGd2345!2222", "%%%9HApp22", "&&%ooW332aw"]
    }

    def 'test should throw an exception when saved user has null date of birth'() {
        given: 'date of birth setting'
            user.setDateOfBirth(null)

        when: 'saving user with null date of birth'
            repository.save(user)

        then: 'exception should be thrown'
            def exception = thrown(ConstraintViolationException)
            exception.message.contains("Date of birth must not be null")
    }

    def 'test should throw an exception when saved user has too late date of birth'() {
        given: 'date of birth setting'
            user.setDateOfBirth(dateOfBirth)

        when: 'saving user with too late date of birth'
            repository.save(user)

        then: 'exception should be thrown'
            def exception = thrown(ConstraintViolationException)
            exception.message.contains("Date of birth must be in the past tense")

        where: 'incorrect parameters'
            dateOfBirth << [LocalDate.now().plusYears(2).plusMonths(11),
                            LocalDate.now().plusYears(4).plusMonths(5)]
    }

    def 'test should throw an exception when saved user has too early date of birth'() {
        given: 'date of birth setting'
            user.setDateOfBirth(dateOfBirth)

        when: 'saving user with too early date of birth'
            repository.save(user)

        then: 'exception should be thrown'
            def exception = thrown(ConstraintViolationException)
            exception.message.contains("Date of birth must year 1900 or later")

        where: 'incorrect parameters'
            dateOfBirth << [LocalDate.of(1899, 11, 24),
                            LocalDate.of(1897, 6, 4)]
    }

    def 'test should not throw any exception when saved user has correct date of birth'() {
        given: 'date of birth setting'
            user.setDateOfBirth(dateOfBirth)

        when: 'saving user with correct date of birth'
            repository.save(user)

        then: 'no exceptions'
            noExceptionThrown()

        where: 'correct parameters'
            dateOfBirth << [LocalDate.of(2003, 4, 5),
                            LocalDate.of(1957, 11, 12),
                            LocalDate.of(2005, 10, 8)]
    }

    def 'test should throw an exception when saved user has already present email'() {
        given:
            'defining email'
            def email = "BigBoyJohn54@gmail.com"

            'email setting'
            user.setEmail(email)

            'saving user'
            repository.save(user)

            'creating new user with duplicated email'
            def secondUser = User.builder()
                    .username("BigBoy155")
                    .password("CatDogDuck554&&")
                    .dateOfBirth(LocalDate.of(2000, 1, 12))
                    .email(email)
                    .phoneNumber("367288111")
                    .description(
                            "I'm Big Boy, and I'm all about living large and living life to the fullest." +
                            " I've got a big personality, big dreams, and a big appetite for adventure."
                    )
                    .photo("user93_myPhoto6.jpg")
                    .characterTraits(
                            [CharacterTrait.COOPERATIVE,
                             CharacterTrait.SCRUPULOUS,
                             CharacterTrait.LEADING] as Set
                    )
                    .build()

        when: 'saving user with duplicated email'
            repository.save(secondUser)

        then: 'exception should be thrown'
            def exception = thrown(DataIntegrityViolationException)
            exception.cause.getClass() == org.hibernate.exception.ConstraintViolationException
            exception.cause.cause.getClass() == JdbcSQLIntegrityConstraintViolationException
            exception.cause.cause.message.contains("Unique index or primary key violation")
    }

    def 'test should throw an exception when saved user has empty email'() {
        given: 'email setting'
            user.setEmail(email)

        when: 'saving user with empty email'
            repository.save(user)

        then: 'exception should be thrown'
            def exception = thrown(ConstraintViolationException)
            exception.message.contains("Email field cannot be empty")

        where: 'incorrect parameters'
            email << [null, ""]
    }

    def 'test should throw an exception when saved user has email with wrong pattern'() {
        given: 'email setting'
            user.setEmail(email)

        when: 'saving user with wrong pattern email'
            repository.save(user)

        then: 'exception should be thrown'
            def exception = thrown(ConstraintViolationException)
            exception.message.contains("Entered email is invalid")

        where: 'incorrect parameters'
            email << [" ", "BenzMercedes.o2.com", "volkswagenpassat.@outlook.com",
                      ".john.cooper664@meta.com", "peter553@outlook.com.", "violin19@.o2.com", "linux@.com"]
    }

    def 'test should not throw any exception when saved user has correct email'() {
        given: 'email setting'
            user.setEmail(email)

        when: 'saving user with correct email'
            repository.save(user)

        then: 'no exceptions'
            noExceptionThrown()

        where: 'correct parameters'
            email << ["anthonyjohnson@o2.com", "annie.poster@outlook.com",
                      "morgan-freeman@yahoo.com", "bernardhopkins@yahoo.co.in", "marie_torch@gmail.com"]
    }

    def 'test should throw an exception when saved user has already present phone number'() {
        given:
            'defining phone number'
            def phoneNumber = "756930221"

            'phone number setting'
            user.setPhoneNumber(phoneNumber)

            'saving user'
            repository.save(user)

            'creating new user with duplicated phone number'
            def secondUser = User.builder()
                    .username("LaraWhite21")
                    .password("ApplePieBananaJoe009##")
                    .dateOfBirth(LocalDate.of(1999, 2, 19))
                    .email("MyFavouriteAnimalIsDog444@hotmail.com")
                    .phoneNumber(phoneNumber)
                    .description(
                            "I'm Lara, and my life is an adventure waiting to happen. Exploring" +
                            " new places, cultures, and cuisines is my passion. I'm a travel" +
                            " enthusiast who's always on the lookout for the next thrilling journey."
                    )
                    .photo("user433_selfie_44.jpg")
                    .characterTraits(
                            [CharacterTrait.SHY,
                             CharacterTrait.STOIC,
                             CharacterTrait.HELPFUL] as Set
                    )
                    .build()

        when: 'saving user with duplicated phone number'
            repository.save(secondUser)

        then: 'exception should be thrown'
            def exception = thrown(DataIntegrityViolationException)
            exception.cause.getClass() == org.hibernate.exception.ConstraintViolationException
            exception.cause.cause.getClass() == JdbcSQLIntegrityConstraintViolationException
            exception.cause.cause.message.contains("Unique index or primary key violation")
    }

    def 'test should throw an exception when saved user has empty phone number'() {
        given: 'phone number setting'
            user.setPhoneNumber(phoneNumber)

        when: 'saving user with empty phone number'
            repository.save(user)

        then: 'exception should be thrown'
            def exception = thrown(ConstraintViolationException)
            exception.message.contains("Phone number field cannot be empty")

        where: 'incorrect parameters'
            phoneNumber << [null, ""]
    }

    def 'test should throw an exception when saved user has phone number with wrong pattern'() {
        given: 'phone number setting'
            user.setPhoneNumber(phoneNumber)

        when: 'saving user with wrong pattern phone number'
            repository.save(user)

        then: 'exception should be thrown'
            def exception = thrown(ConstraintViolationException)
            exception.message.contains("Entered phone number is invalid")

        where: 'incorrect parameters'
            phoneNumber << [" ", "         ", "2", "45362789"]
    }

    def 'test should not throw any exception when saved user has correct phone number'() {
        given: 'phone number setting'
            user.setPhoneNumber(phoneNumber)

        when: 'saving user with correct phone number'
            repository.save(user)

        then: 'no exceptions'
            noExceptionThrown()

        where: 'correct parameters'
            phoneNumber << ["683091223", "584221098"]
    }

    def 'test should throw an exception when saved user has blank description'() {
        given: 'description setting'
            user.setDescription(description)

        when: 'saving user with blank description'
            repository.save(user)

        then: 'exception should be thrown'
            def exception = thrown(ConstraintViolationException)
            exception.message.contains("User description field cannot be empty")

        where: 'incorrect parameters'
            description << [null, "", " "]
    }

    def 'test should throw an exception when saved user has invalid description size'() {
        given: 'description setting'
            user.setDescription(description)

        when: 'saving user with wrong sized description'
            repository.save(user)

        then: 'exception should be thrown'
            def exception = thrown(ConstraintViolationException)
            exception.message.contains("User description should not be longer than 255 characters")

        where: 'incorrect parameters'
            description << ["I'm Francis, a lifelong explorer of knowledge and a true intellectual" +
                            " at heart. My world revolves around books, science, and deep philosophical" +
                            " conversations. I'm constantly seeking to expand my mind and engage" +
                            " in thought-provoking discussions on a wide range of topics."]
    }

    def 'test should not throw any exception when saved user has correct description'() {
        given: 'description setting'
            user.setDescription(description)

        when: 'saving user with correct description'
            repository.save(user)

        then: 'no exceptions'
            noExceptionThrown()

        where: 'correct parameters'
            description << ["I'm Madison, and I'm all about creativity and self-expression. Whether" +
                            " I'm painting on a canvas, playing my guitar, or experimenting with new" +
                            " recipes in the kitchen, I'm at my happiest when I'm creating something unique."
                            ,
                            "I'm a Super Saiyan!"]
    }

    def 'test should throw an exception when saved user has blank photo path'() {
        given: 'photo path setting'
            user.setPhoto(photoPath)

        when: 'saving user with blank photo path'
            repository.save(user)

        then: 'exception should be thrown'
            def exception = thrown(ConstraintViolationException)
            exception.message.contains("Photo path field cannot be empty")

        where: 'incorrect parameters'
            photoPath << [null, "", " "]
    }

    def 'test should not throw any exception when saved user has correct photo path'() {
        given: 'photo path setting'
            user.setPhoto(photoPath)

        when: 'saving user with correct photo path'
            repository.save(user)

        then: 'no exceptions'
            noExceptionThrown()

        where: 'correct parameters'
            photoPath << ["user16_12_04_2020_004", "user16_03_12_2022_181"]
    }

    def 'test should throw an exception when saved user has null character traits'() {
        given: 'character traits setting'
            user.setCharacterTraits(null)

        when: 'saving user with null character traits'
            repository.save(user)

        then: 'exception should be thrown'
            def exception = thrown(ConstraintViolationException)
            exception.message.contains("Character traits must not be null")
    }

    def 'test should throw an exception when saved user has invalid character traits size'() {
        given: 'character traits setting'
            def traits = characterTraits
            user.setCharacterTraits(traits)

        when: 'saving user with wrong sized character traits'
            repository.save(user)

        then: 'exception should be thrown'
            def exception = thrown(ConstraintViolationException)
            exception.message.contains("Number of character traits must be equal 3")

        where: 'incorrect parameters'
            characterTraits << [[] as Set,
                                [CharacterTrait.PATIENT] as Set,
                                [CharacterTrait.ORGANIZED, CharacterTrait.SHY] as Set,
                                [CharacterTrait.HUMBLE, CharacterTrait.KIND, CharacterTrait.STOIC, CharacterTrait.CALM]
                                        as Set]
    }

    def 'test should not throw any exception when saved user has correct character traits'() {
        given: 'character traits setting'
            def traits = characterTraits
            user.setCharacterTraits(traits)

        when: 'saving user with correct character traits'
            repository.save(user)

        then: 'no exceptions'
            noExceptionThrown()

        where: 'correct parameters'
            characterTraits << [[CharacterTrait.RELIABLE, CharacterTrait.ADAPTIVE, CharacterTrait.WITTY] as Set,
                                [CharacterTrait.CHEERFUL, CharacterTrait.CONFIDENT, CharacterTrait.CALM] as Set]
    }
}
