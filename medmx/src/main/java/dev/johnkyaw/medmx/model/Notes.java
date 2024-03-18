package dev.johnkyaw.medmx.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "note")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Notes {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patient_seq")
    @SequenceGenerator(name = "patient_seq", sequenceName = "patient_sequence", allocationSize = 1)
    private Long id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "physician_id", nullable = false)
    private Physician physician;

    private String noteContent;
    private String visitType;
}
