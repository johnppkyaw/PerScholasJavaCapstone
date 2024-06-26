package dev.johnkyaw.medmx.service;

import dev.johnkyaw.medmx.model.Note;
import dev.johnkyaw.medmx.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class NoteServices {
    private final NoteRepository noteRepository;

    @Autowired
    public NoteServices(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> getAllNotesByPatientId(Long id) {
        return noteRepository.getAllNotesByPatientId(id);
    }

    public List<Note> getAllNotesByPatientIdAndDate(Long patientId, LocalDate date) {
        return noteRepository.getNotesByPatientIdAndDate(patientId, date);
    };

    public Note getNoteById(Long id) {
        return noteRepository.findById(id).orElse(null);
    }

    public Note saveOrUpdateNote(Note note) {
        return noteRepository.save(note);
    }

    public void deleteNoteById(Long id) {
        noteRepository.deleteById(id);
    }
}
