package com.example.arclesocialapp.data

import com.example.arclesocialapp.domain.Happening
import com.example.arclesocialapp.domain.Review
import com.example.arclesocialapp.domain.Spot
import com.example.arclesocialapp.domain.User
import com.example.arclesocialapp.enumeration.CharacterTrait
import com.example.arclesocialapp.enumeration.HappeningStatus

import java.time.LocalDate
import java.time.LocalDateTime

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
                .photo("user16_wednesday_selfie_04.jpg")
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
                .photo("review29_03_04_2022_006.jpg")
                .user(getUser())
                .build()
    }

    static Happening getHappening() {
        return Happening.builder()
                .spot(Spot.builder().build())
                .startTime(LocalDateTime.of(2024, 9, 12, 17, 30))
                .description(
                        "We're throwing a party that's going to be the talk of the campus." +
                        " Picture this: good vibes, good company, and good times all night long." +
                        " The crowd will be a mix of amazing folks, both guys and pretty girls," +
                        " ready to have a blast. Our drinks selection is top-notch, with everything" +
                        " from classic cocktails to craft beers. And we've got a signature drink" +
                        " menu that'll blow your mind. Whether you're into dancing, mingling," +
                        " or simply enjoying the atmosphere, there's something for everyone."
                )
                .photos(
                        ["happening53_11_07_2022_132.jpg",
                         "happening53_11_07_2022_156.jpg",
                         "happening53_11_07_2022_082.jpg"] as Set
                )
                .status(HappeningStatus.SCHEDULED)
                .build()
    }
}
