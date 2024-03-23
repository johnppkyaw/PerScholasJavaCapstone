package dev.johnkyaw.medmx.service;

import dev.johnkyaw.medmx.model.Patient;
import dev.johnkyaw.medmx.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientServices patientService;

    @Test
    public void testFindByLastName() {
        // Given
        Patient patient1 = new Patient("John", "Do", LocalDate.parse("1990-05-15"), "Male", "123 Main St", "", "555-1234");
        Patient patient2 = new Patient("Jane", "Goe", LocalDate.parse("1995-03-08"), "Female", "456 Elm St", "Apt 101", "555-5678");
        List<Patient> patients = new ArrayList<>();
        patients.add(patient1);

        when(patientRepository.findByLastName("Do")).thenReturn(patients);

        // When
        List<Patient> patients2 = patientService.getPatientsByLastName("Do");

        // Then
        assertEquals(patients, patients2);
    }
}
