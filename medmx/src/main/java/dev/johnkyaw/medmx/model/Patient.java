package dev.johnkyaw.medmx.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patient", uniqueConstraints = { @UniqueConstraint(name = "UniqueFirstAndLastAndDOB", columnNames = {"firstName", "lastName", "dateOfBirth"})})
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

    public int calculateAge(LocalDate dob) {
        LocalDate currentDate = LocalDate.now();
        return Period.between(dob, currentDate).getYears();
    }
}

//Patient sample
//[
//		{
//		"firstName": "John",
//		"lastName": "Doe",
//		"dateOfBirth": "1990-05-15",
//		"gender": "Male",
//		"address": "123 Main St",
//		"address2": "",
//		"phone": "555-1234",
//		"physician": null
//		},
//		{
//		"firstName": "Jane",
//		"lastName": "Smith",
//		"dateOfBirth": "1985-08-25",
//		"gender": "Female",
//		"address": "456 Elm St",
//		"address2": "Apt 101",
//		"phone": "555-5678",
//		"physician": null
//		},
//		{
//		"firstName": "Michael",
//		"lastName": "Johnson",
//		"dateOfBirth": "1976-11-10",
//		"gender": "Male",
//		"address": "789 Oak St",
//		"address2": "Suite 202",
//		"phone": "555-9876",
//		"physician": null
//		},
//		{
//		"firstName": "Emily",
//		"lastName": "Brown",
//		"dateOfBirth": "1995-03-08",
//		"gender": "Female",
//		"address": "101 Pine St",
//		"address2": "",
//		"phone": "555-4321",
//		"physician": null
//		},
//		{
//		"firstName": "David",
//		"lastName": "Wilson",
//		"dateOfBirth": "1980-07-20",
//		"gender": "Male",
//		"address": "222 Cedar St",
//		"address2": "Unit B",
//		"phone": "555-8765",
//		"physician": null
//		}
//		]
