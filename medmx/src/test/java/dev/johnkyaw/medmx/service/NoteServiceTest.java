package dev.johnkyaw.medmx.service;

import dev.johnkyaw.medmx.model.Note;
import dev.johnkyaw.medmx.model.Patient;
import dev.johnkyaw.medmx.model.Physician;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@RunWith(SpringRunner.class)
//The SpringRunner integrates the Spring TestContext Framework with JUnit and provides functionality to load Spring ApplicationContext, perform dependency injection, and manage transactional behavior during tests.
@DataJpaTest
//provides an isolated testing environment with an in-memory database and transaction management, allowing you to write focused and efficient tests for your data access layer.
@EntityScan(basePackages = "dev.johnkyaw.medmx.model")
//This is required when the entities are not in the same package or subpackages
@ComponentScan("dev.johnkyaw.medmx.service")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class NoteServiceTest {
    @Autowired
    private NoteServices noteServices;

    @Autowired
    private PatientServices patientServices;

    @Autowired
    private PhysicianServices physicianServices;

    @Autowired
    private TestEntityManager entityManager;

    private Patient patient;
    private Physician physician;

    @BeforeEach
    public void setUp() {
        // Persist the patient and physician once before each test
        patient = new Patient("John", "Doe", LocalDate.parse("1990-05-15"), "Male", "123 Main St", "", "555-1234");
        physician = new Physician("John", "Smith", "Cardiologist", "Cardio Clinic", "123 Main St", " ", "555-1111");
        entityManager.persist(patient);
        entityManager.persist(physician);
        entityManager.flush();
    }

    static Stream<Arguments> notes() {
        return Stream.of(
                Arguments.of(1L, LocalDate.parse("2024-03-23"), 1),
                Arguments.of(2L, LocalDate.parse("2024-03-24"), 1),
                Arguments.of(3L, LocalDate.parse("2024-03-25"), 1)
        );
    }

    @ParameterizedTest
    @WithMockUser
    @MethodSource("notes")
    public void findNotes(Long Id, LocalDate date, int size) {
        Patient patient1 = patientServices.getPatientById(Id).isPresent() ? patientServices.getPatientById(Id).get() : null;

        Physician physician1 = physicianServices.getPhysicianById(Id).isPresent() ? physicianServices.getPhysicianById(Id).get() : null;

        assert patient1 != null;
        assert physician1 != null;
        Note note = new Note(patient1, physician1);
        note.setDate(date);
        entityManager.persist(note);
        entityManager.flush();

        List<Note> notes = noteServices.getAllNotesByPatientIdAndDate(Id, date);
        assertEquals(notes.size(), size);
    }
}
