package dev.johnkyaw.medmx.controller;

import dev.johnkyaw.medmx.model.Note;
import dev.johnkyaw.medmx.service.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteRestController {
    Logger log = LoggerFactory.getLogger(PatientRestController.class);

    private final NoteService noteService;

    @Autowired
    public NoteRestController(NoteService noteService) {
        this.noteService = noteService;
    }

    @Transactional
    @GetMapping("/patients/{patientId}")
    public ResponseEntity<List<Note>> getAllNotesByPatientId(@PathVariable("patientId") Long patientId) {
        List<Note> notes = noteService.getAllNotesByPatientId(patientId);
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }
    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        Note note = noteService.getNoteById(id);
        if(note != null) {
            return new ResponseEntity<>(note, HttpStatus.OK);
        } else {
            log.warn("Note not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @Transactional
    @PostMapping
    public ResponseEntity<Note> addNote(@RequestBody Note note) {
        Note addedNote = noteService.saveOrUpdateNote(note);
        return new ResponseEntity<>(addedNote, HttpStatus.CREATED);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @RequestBody Note note) {
        if (noteService.getNoteById(id) == null) {
            log.warn("Could not edit the note since the note was not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        note.setId(id);
        Note updatedNote = noteService.saveOrUpdateNote(note);
        return new ResponseEntity<>(updatedNote, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable Long id) {
        if (noteService.getNoteById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        noteService.deleteNoteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
