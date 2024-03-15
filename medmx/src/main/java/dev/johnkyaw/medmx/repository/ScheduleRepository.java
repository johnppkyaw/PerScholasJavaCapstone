package dev.johnkyaw.medmx.repository;

import dev.johnkyaw.medmx.model.Patient;
import dev.johnkyaw.medmx.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("SELECT s FROM Schedule s WHERE s.physician.id = :physicianId AND DATE(s.date) = :searchDate")
    List<Schedule> getAllSchedulesByPhysicianAndDate(@Param("physicianId") Long physicianId, @Param("searchDate") LocalDate searchDate);
}
