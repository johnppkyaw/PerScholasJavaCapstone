package dev.johnkyaw.medmx.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private List<Patient> patients = new ArrayList<>();

    @OneToMany(mappedBy = "physician")
    @JsonIgnore
    private List<Schedule> schedules = new ArrayList<>();
}
//Physician sample
//[
//        {
//        "firstName": "John",
//        "lastName": "Smith",
//        "specialty": "Cardiologist",
//        "clinicName": "Cardio Clinic",
//        "clinicAddress": "123 Main St",
//        "clinicAddress2": "",
//        "phone": "555-1111",
//        "patients": null,
//        "schedules": null
//        },
//        {
//        "firstName": "Jane",
//        "lastName": "Doe",
//        "specialty": "Pediatrician",
//        "clinicName": "Kids Clinic",
//        "clinicAddress": "456 Elm St",
//        "clinicAddress2": "Suite 101",
//        "phone": "555-2222",
//        "patients": null,
//        "schedules": null
//        },
//        {
//        "firstName": "Michael",
//        "lastName": "Johnson",
//        "specialty": "Dermatologist",
//        "clinicName": "Skin Clinic",
//        "clinicAddress": "789 Oak St",
//        "clinicAddress2": "Suite 202",
//        "phone": "555-3333",
//        "patients": null,
//        "schedules": null
//        }
//        ]