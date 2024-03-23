package dev.johnkyaw.medmx.dto;

import dev.johnkyaw.medmx.model.Physician;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientDTO {
    private Long Id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String address;
    private String address2;
    private String phone;
    private PhysicianFrontDTO physician;
}
