package dev.johnkyaw.medmx.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patient")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patient_seq")
    @SequenceGenerator(name = "patient_seq", sequenceName = "patient_sequence", allocationSize = 1)
    private long id;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    //Date class from java.util is outdated
    @NonNull
    private LocalDate dateOfBirth;

    @NonNull
    private String gender;

    @NonNull
    private String address;

    @NonNull
    private String address2;

    @NonNull
    private String phone;

    @ManyToOne
    @JoinColumn(name = "physician_id")
    private Physician physician;

    @OneToMany(mappedBy = "patient")
    private List<Schedule> schedules = new ArrayList<>();

    public int calculateAge(LocalDate dob) {
        LocalDate currentDate = LocalDate.now();
        return Period.between(dob, currentDate).getYears();
    }

}
