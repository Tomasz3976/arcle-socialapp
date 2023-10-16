package com.example.arclesocialapp.data

import com.example.arclesocialapp.domain.Review
import com.example.arclesocialapp.domain.User
import com.example.arclesocialapp.enumeration.CharacterTrait

import java.time.LocalDate

class TestDataProvider {

    static User getUser() {
        return User.builder()
                .username("MikeRoger52")
                .password("AppleJuice61*")
                .dateOfBirth(LocalDate.of(1995, 7, 11))
                .email("MikeRoger988@gmail.com")
                .phoneNumber("762901573")
                .description(
                        "Hey there, I’m Mike, and I live for those epic party moments!" +
                                " I’m the guy who’ll turn any gathering into a non-stop thrill ride."
                )
                .characterTraits(
                        [CharacterTrait.ENERGETIC,
                         CharacterTrait.LEADING,
                         CharacterTrait.SOCIABLE] as Set
                )
                .build()
    }

    static Review getReview() {
        return Review.builder()
                .authorId(43L)
                .rating(8 as Byte)
                .description(
                        "I gave Mike an 8 out of 10 stars because he's a total party legend!" +
                                " What sets Mike apart is his unwavering enthusiasm and charisma." +
                                " He's the kind of guy who can light up any room with his positive energy."
                )
                .user(getUser())
                .build()
    }
}
