package dev.johnkyaw.medmx.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientDTO {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String address;
    private String address2;
    private String phone;
}
