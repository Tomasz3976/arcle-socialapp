package com.example.arclesocialapp.domain;

import com.example.arclesocialapp.annotation.LaterThanYear;
import com.example.arclesocialapp.enumeration.CharacterTrait;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank(message = "{username.notBlank}")
    @Size(min = 5, max = 20, message = "{username.size}")
    private String username;

    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}",
             message = "{password.pattern}")
    private String password;

    @Past
    @LaterThanYear(1900)
    private LocalDate dateOfBirth;

    @Column(unique = true)
    @Email
    private String email;

    @Column(unique = true)
    @Pattern(regexp="(^$|[0-9]{9})", message = "{phone.pattern}")
    private String phoneNumber;

    @Size(max = 255, message = "{description.size}")
    private String description;

    @ElementCollection(fetch = EAGER)
    @CollectionTable(name = "user_character_trait")
    @Column(name = "character_trait")
    @Enumerated(STRING)
    @Size(min = 3, max = 3, message = "{characterTraits.size}")
    private Set<CharacterTrait> characterTraits;

    @OneToMany(mappedBy = "user")
    private Set<Review> reviews;

    @JsonIgnore
    @ManyToMany(mappedBy = "participants")
    private Set<Happening> happenings;
}
