package dev.johnkyaw.medmx.repository;

import dev.johnkyaw.medmx.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> getAllNotesByPatientId(Long patientId);
    List<Note> getNotesByPatientIdAndDate(Long patientId, LocalDate date);
}