package dev.johnkyaw.medmx.repository;

import dev.johnkyaw.medmx.model.Physician;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhysicianRepository extends JpaRepository<Physician, Long> {
}
