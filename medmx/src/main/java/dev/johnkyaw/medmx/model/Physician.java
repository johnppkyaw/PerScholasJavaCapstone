package dev.johnkyaw.medmx.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "physician")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Physician {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "physician_seq")
    @SequenceGenerator(name = "physician_seq", sequenceName = "physician_sequence", allocationSize = 1)
    private int id;

    private String firstName;

    private String lastName;

    private String specialty;

    private String clinicName;

    private String clinicAddress;

    private String clinicAddress2;

    @OneToMany(mappedBy = "physician", cascade = CascadeType.DETACH)
    private Set<Patient> patients = new HashSet<>();

    @OneToMany(mappedBy = "physician")
    private List<Schedule> schedules;
}