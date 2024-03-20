package dev.johnkyaw.medmx.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.johnkyaw.medmx.CustomLocalTimeDeserializer;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "note")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patient_seq")
    @SequenceGenerator(name = "patient_seq", sequenceName = "patient_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    @JsonDeserialize(using = CustomLocalTimeDeserializer.class)
    private LocalTime time;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "physician_id", nullable = false)
    private Physician physician;

    private String visitType;

    private String noteContent;
}
