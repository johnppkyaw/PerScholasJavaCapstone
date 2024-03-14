package dev.johnkyaw.medmx.repository;

import dev.johnkyaw.medmx.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
