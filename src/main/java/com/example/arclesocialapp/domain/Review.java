package com.example.arclesocialapp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull(message = "Author id {notNullMessage}")
    @Positive(message = "{authorId.positiveValueMessage}")
    private Long authorId;

    @NotNull(message = "Rating {notNullMessage}")
    @Min(value = 1, message = "{rating.tooLowMessage}")
    @Max(value = 10, message = "{rating.tooHighMessage}")
    private Byte rating;

    @NotBlank(message = "Review description {notEmptyMessage}")
    @Size(max = 255, message = "Review description {maxSizeMessage}")
    private String description;

    @NotBlank(message = "Photo path {notEmptyMessage}")
    private String photo;

    @Valid
    @NotNull(message = "Corresponding user {notNullMessage}")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
