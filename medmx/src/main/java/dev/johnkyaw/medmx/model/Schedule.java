package dev.johnkyaw.medmx.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.johnkyaw.medmx.CustomLocalTimeDeserializer;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "date", "startTime", "physician_id" }) })
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schedule_seq")
    @SequenceGenerator(name = "schedule_seq", sequenceName = "schedule_sequence", allocationSize = 1)
    private long id;

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private LocalTime startTime;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "physician_id", nullable = false)
    private Physician physician;

    private String status;

    private String notes;
}
