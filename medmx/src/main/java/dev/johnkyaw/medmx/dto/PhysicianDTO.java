package dev.johnkyaw.medmx.dto;

import dev.johnkyaw.medmx.util.FieldMatch;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@FieldMatch.List ( { @FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match")})

public class PhysicianDTO {

    @NotEmpty
    @Column(unique = true)
    @Email
    private String username;

    @Pattern(regexp = "[A-Za-z]+$", message = "Only alphabetic allowed")
    private String firstName;

    @Pattern(regexp = "[A-Za-z]+$", message = "Only alphabetic allowed")
    private String lastName;

    @NotEmpty(message = "Required")
    private String clinicName;
    @NotEmpty(message = "Required")
    private String clinicAddress;
    @NotEmpty(message = "Required")
    private String clinicAddress2;
    @NotEmpty(message = "Required")
    private String phone;

    @NotEmpty(message = "Required")
    private String password;

    @NotEmpty(message = "Required")
    private String matchingPassword;

    public PhysicianDTO(@NotEmpty String username, @Pattern(regexp = "[A-Za-z]+$", message = "Only alphabetic allowed") String firstName, @Pattern(regexp = "[A-Za-z]+$", message = "Only alphabetic allowed") String lastName, @NotEmpty(message = "Required") String clinicName, @NotEmpty(message = "Required") String clinicAddress, @NotEmpty(message = "Required") String clinicAddress2, @NotEmpty(message = "Required") String phone, @NotEmpty(message = "Required") String password, @NotEmpty(message = "Required") String matchingPassword) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.clinicName = clinicName;
        this.clinicAddress = clinicAddress;
        this.clinicAddress2 = clinicAddress2;
        this.phone = phone;
        this.password = password;
        this.matchingPassword = matchingPassword;
    }
}

