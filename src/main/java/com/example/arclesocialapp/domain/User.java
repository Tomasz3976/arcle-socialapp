package com.example.arclesocialapp.domain;

import com.example.arclesocialapp.annotation.LaterThanYear;
import com.example.arclesocialapp.enumeration.CharacterTrait;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "app_user")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank(message = "Username {notBlankMessage}")
    @Size(min = 5, max = 20, message = "{username.sizeMessage}")
    private String username;

    @NotBlank(message = "Password {notBlankMessage}")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*?])[A-Za-z\\d!@#$%^&*?]{10,}$",
             message = "{password.patternMessage}")
    private String password;

    @NotNull(message = "Date of birth {notNullMessage}")
    @Past(message = "{dateOfBirth.pastMessage}")
    @LaterThanYear(value = 1900, message = "{dateOfBirth.LaterThanYearMessage}")
    private LocalDate dateOfBirth;

    @Column(unique = true)
    @NotBlank(message = "Email {notBlankMessage}")
    @Email(message = "{emailMessage}")
    private String email;

    @Column(unique = true)
    @NotBlank(message = "Phone number {notBlankMessage}")
    @Size(max = 9, message = "Phone number {maxSizeMessage}")
    @Pattern(regexp="(^$|[0-9]{9})", message = "{phoneNumber.patternMessage}")
    private String phoneNumber;

    @NotBlank(message = "Description {notBlankMessage}")
    @Size(max = 255, message = "Description {maxSizeMessage}")
    private String description;

    @ElementCollection(fetch = EAGER)
    @CollectionTable(name = "user_character_trait")
    @Column(name = "character_trait")
    @Enumerated(STRING)
    @NotNull(message = "Character traits {notNullMessage}")
    @Size(min = 3, max = 3, message = "{characterTraits.sizeMessage}")
    private Set<CharacterTrait> characterTraits;

    @Valid
    @OneToMany(mappedBy = "user")
    private Set<Review> reviews;

    @JsonIgnore
    @ManyToMany(mappedBy = "participants")
    private Set<Happening> happenings;
}
