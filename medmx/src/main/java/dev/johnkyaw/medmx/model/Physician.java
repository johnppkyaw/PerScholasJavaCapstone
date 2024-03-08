package dev.johnkyaw.medmx.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Physician {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int physId;

    private String firstName;

    private String lastName;

    //MD, DO
    private String title;

    private String specialty;

    private String clinicName;

    private String clinicAddress;

    @ManyToMany
    private Set<Patient> patients = new HashSet<>();
}