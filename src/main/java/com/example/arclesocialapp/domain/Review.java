package com.example.arclesocialapp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull(message = "Author id {notNullMessage}")
    private Long authorId;

    @Min(value = 1, message = "{rating.tooLowMessage}")
    @Max(value = 10, message = "{rating.tooHighMessage}")
    private Byte rating;

    @Size(max = 255, message = "{description.sizeMessage}")
    private String description;

    @NotNull(message = "Corresponding user {notNullMessage}")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
