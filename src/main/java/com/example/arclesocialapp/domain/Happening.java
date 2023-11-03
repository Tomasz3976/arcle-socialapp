package com.example.arclesocialapp.domain;

import com.example.arclesocialapp.annotation.LaterThanYear;
import com.example.arclesocialapp.enumeration.HappeningStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
public class Happening {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Embedded
    @Valid
    @NotNull(message = "Happening spot {notNullMessage}")
    private Spot spot;

    @NotNull(message = "Happening start time {notNullMessage}")
    @LaterThanYear(value = 2020, message = "{startTime.laterThanYearMessage}")
    private LocalDateTime startTime;

    @NotBlank(message = "Happening description {notEmptyMessage}")
    @Size(max = 768, message = "Happening description {maxSizeMessage}")
    private String description;

    @ElementCollection(fetch = EAGER)
    @CollectionTable(name = "happening_photos")
    @Column(name = "photo_path")
    @Size(max = 5, message = "photos.sizeMessage")
    private Set<String> photos;

    @NotNull(message = "Happening status {notNullMessage}")
    @Enumerated(STRING)
    private HappeningStatus status;

    @Valid
    @ManyToMany
    private Set<User> participants;
}
