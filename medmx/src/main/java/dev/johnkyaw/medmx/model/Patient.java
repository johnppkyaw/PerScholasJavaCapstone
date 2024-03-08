package dev.johnkyaw.medmx.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int patId;

    private String firstName;

    private String lastName;

    private int age;

    private String gender;

    private String address;

    private String city;

    private String state;

    private int zipCode;

    private int primaryPhysician;

    @ManyToMany
    private Set<Physician> specialists;
}
