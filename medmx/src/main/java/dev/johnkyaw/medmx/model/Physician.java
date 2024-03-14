package dev.johnkyaw.medmx.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "physician")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Physician {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "physician_seq")
    @SequenceGenerator(name = "physician_seq", sequenceName = "physician_sequence", allocationSize = 1)
    private long id;

    @Getter
    @Setter
    private String firstName;

    @Getter
    @Setter
    private String lastName;

    @Getter
    @Setter
    private String specialty;

    @Getter
    @Setter
    private String clinicName;

    @Getter
    @Setter
    private String clinicAddress;

    @Getter
    @Setter
    private String clinicAddress2;

    @Getter
    @Setter
    private String phone;

    @Getter
    @Setter
    @OneToMany(mappedBy = "physician", cascade = CascadeType.DETACH)
    private List<Patient> patients = new ArrayList<>();

    @Getter
    @Setter
    @OneToMany(mappedBy = "physician")
    private List<Schedule> schedules = new ArrayList<>();
}