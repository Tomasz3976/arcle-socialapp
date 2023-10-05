package com.example.arclesocialapp.domain;

import com.example.arclesocialapp.annotation.LaterThanYear;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.time.LocalDate;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
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
}
