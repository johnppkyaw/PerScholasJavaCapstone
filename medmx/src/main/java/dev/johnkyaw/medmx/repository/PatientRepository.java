package dev.johnkyaw.medmx.repository;

import dev.johnkyaw.medmx.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Query("SELECT p FROM Patient p WHERE p.physician.id = :physicianId")
    List<Patient> findByPhysicianId(@Param("physicianId") Long physicianId);
}
