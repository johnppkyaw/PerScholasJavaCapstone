package dev.johnkyaw.medmx.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "physician",
        uniqueConstraints = { @UniqueConstraint(name = "UniqueNumberAndStatus", columnNames = {"first_name", "last_name", "specialty"})})
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@Getter
@Setter
public class Physician {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "physician_seq")
    @SequenceGenerator(name = "physician_seq", sequenceName = "physician_sequence", allocationSize = 1)
    private long id;

    @NonNull
    @Column(name = "first_name")
    private String firstName;

    @NonNull
    @Column(name = "last_name")
    private String lastName;

    @NonNull
    @Column(name = "specialty")
    private String specialty;

    @NonNull
    private String clinicName;

    @NonNull
    private String clinicAddress;

    @NonNull
    private String clinicAddress2;

    @NonNull
    private String phone;

    @OneToMany(mappedBy = "physician", cascade = CascadeType.DETACH)
    private List<Patient> patients = new ArrayList<>();

    @OneToMany(mappedBy = "physician")
    private List<Schedule> schedules = new ArrayList<>();
}