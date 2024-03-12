package dev.johnkyaw.medmx.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "patient")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patient_seq")
    @SequenceGenerator(name = "patient_seq", sequenceName = "patient_sequence", allocationSize = 1)
    private long id;

    private String firstName;

    private String lastName;

    private int age;

    private String gender;

    private String address;

    private String address2;

    @ManyToOne
    @JoinColumn(name = "physician_id")
    private Physician primaryPhysician;

    @OneToMany(mappedBy = "patient")
    private List<Schedule> schedules;
}
