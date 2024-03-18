package dev.johnkyaw.medmx.repository;

import dev.johnkyaw.medmx.model.Physician;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhysicianRepository extends JpaRepository<Physician, Long> {
    Optional<Physician> findByFirstNameAndLastNameAndSpecialty(String firstName, String lastName, String specialty);

    Physician findByUsername(String username);

    Physician findByIdAndFirstNameAndLastName(Long Id, String firstName, String lastName);
}
