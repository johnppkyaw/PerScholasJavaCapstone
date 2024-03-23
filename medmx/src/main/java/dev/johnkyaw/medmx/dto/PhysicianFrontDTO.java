package dev.johnkyaw.medmx.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PhysicianFrontDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String specialty;
    private String clinicName;
    private String clinicAddress;
    private String clinicAddress2;
    private String phone;
}
