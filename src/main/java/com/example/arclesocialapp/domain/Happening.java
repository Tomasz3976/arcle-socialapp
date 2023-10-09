package com.example.arclesocialapp.domain;

import com.example.arclesocialapp.enumeration.HappeningStatus;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Happening {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Embedded
    @Valid
    private Spot spot;

    @NotNull(message = "{startTime.notNull}")
    private LocalDateTime startTime;

    @Size(max = 255, message = "{description.size}")
    private String description;

    @NotNull(message = "{status.notNull}")
    @Enumerated(STRING)
    private HappeningStatus status;

    @ManyToMany
    private Set<User> participants;
}
