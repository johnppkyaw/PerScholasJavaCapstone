package dev.johnkyaw.medmx.service;

import dev.johnkyaw.medmx.model.Note;
import dev.johnkyaw.medmx.model.Patient;
import dev.johnkyaw.medmx.model.Physician;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
//The SpringRunner integrates the Spring TestContext Framework with JUnit and provides functionality to load Spring ApplicationContext, perform dependency injection, and manage transactional behavior during tests.
@DataJpaTest
//provides an isolated testing environment with an in-memory database and transaction management, allowing you to write focused and efficient tests for your data access layer.
@EntityScan(basePackages = "dev.johnkyaw.medmx.model")
//This is required when the entities are not in the same package or subpackages
public class NoteRepositoryTest {
    @Autowired
    private NoteServices noteServices;

    @Autowired
    private TestEntityManager entityManager;

    static Stream<Arguments> notes() {
        Patient patient1 = new Patient("John", "Do", LocalDate.parse("1990-05-15"), "Male", "123 Main St", "", "555-1234");
        Physician physician1 = new Physician("John", "Smith", "Cardiologist", "Cardio Clinic", "123 Main St", " ", "555-1111");
        Note note = new Note(patient1, physician1);
        note.setDate(LocalDate.parse("2024-03-23"));
        Note note2 = new Note(patient1, physician1);
        note2.setDate(LocalDate.parse("2024-03-24"));
        Note note3 = new Note(patient1, physician1);
        note3.setDate(LocalDate.parse("2024-03-25"));
        return Stream.of(
                Arguments.of(note),
                Arguments.of(note2),
                Arguments.of(note3)
        );
    }

    @ParameterizedTest
    @WithMockUser
    @MethodSource("notes")
    public void findNotes(Note note) {
        System.out.print(note);
        entityManager.persist(note);
        entityManager.flush();
    }


}
