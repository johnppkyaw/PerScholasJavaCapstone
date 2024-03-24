package dev.johnkyaw.medmx.service;

import dev.johnkyaw.medmx.model.Physician;
import dev.johnkyaw.medmx.repository.PhysicianRepository;
import org.junit.*;
import org.junit.runner.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@EntityScan(basePackages = "dev.johnkyaw.medmx.model")
public class PhysicianRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PhysicianRepository physicianRepository;

    @Test
    public void physicianRepo() throws Exception {
        Physician physician1 = new Physician("John", "Smith", "Cardiologist", "Cardio Clinic", "123 Main St", " ", "555-1111");
        Physician physician2 = new Physician("Jane", "Doe", "Pediatrician", "Kids Clinic", "456 Elm St", " ", "555-2222");

        entityManager.persist(physician1);
        entityManager.persist(physician2);
        entityManager.flush();

        Physician physician = this.physicianRepository.findByIdAndFirstNameAndLastName(1L, "John", "Smith");
        assertThat(physician.getSpecialty()).isEqualTo("Cardiologist");
    }
}
