package dev.johnkyaw.medmx.controller;

import dev.johnkyaw.medmx.model.Physician;
import dev.johnkyaw.medmx.service.PhysicianServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Repeatable;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PhysicianRestController {
    PhysicianServices physicianServices;

    private final Logger logger = LoggerFactory.getLogger(PhysicianRestController.class);

    @Autowired
    public PhysicianRestController(PhysicianServices physicianServices) {
        this.physicianServices = physicianServices;
    }

    @Transactional
    @PostMapping("/physicians")
    public ResponseEntity<Void> addPhysician(@RequestBody Physician physician) {
        physicianServices.addPhysician(physician);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Transactional
    @PostMapping("/physicians/{physicianId}/patients/{patientId}/assign")
    public ResponseEntity<Void> assignPatientToPhysician(Long patientId, Long physicianId) {
        try {
            physicianServices.assignPatientFromPhysician(patientId, physicianId);
            return ResponseEntity.ok("Patient assigned from physician successfully");
        } catch(ResponseStatusException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getReason());
        }
    }

    @Transactional
    @GetMapping("/physicians")
    public ResponseEntity<List<Physician>> getAllPhysicians() {
        return ResponseEntity.ok(physicianServices.getAllPhysician());
    }

    @Transactional
    @GetMapping("/physicians/{id}")
    public ResponseEntity<Optional<Physician>> getPhysicianById(@PathVariable long id) {
        Optional<Physician> physician = physicianServices.getPhysicianById(id);
        if (physician.isPresent()) {
            return ResponseEntity.ok(physician);
        } else {
            logger.warn("Physician with id " + id + " not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @Transactional
    @PutMapping("/physicians/{id}")
    public ResponseEntity<Void> updatePhysician(@PathVariable("id") long id, @RequestBody Physician physician) {
        Optional<Physician> physicianData = physicianServices.getPhysicianById(id);
        if (physicianData.isPresent()) {
            physicianServices.updatePhysician(id, physician);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            logger.warn("Attempted editing but the physician not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @Transactional
    @DeleteMapping("/physicians/{physicianId}/patients/{patientId}/assign")
    public ResponseEntity<Void> assignPatientToPhysician(Long patientId, Long physicianId) {
        try {
            physicianServices.unassignPatientFromPhysician(patientId, physicianId);
            return ResponseEntity.ok("Patient unassigned from physician successfully");
        } catch(ResponseStatusException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getReason());
        }
    }

    @Transactional
    @DeleteMapping("/physicians/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable("id") long id) {
        Optional<Physician> physicianData = physicianServices.getPhysicianById(id);
        if (physicianData.isPresent()) {
            physicianServices.deletePhysician(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            logger.warn("Attempted editing but the patient not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @Transactional
    @DeleteMapping("/physicians")
    public ResponseEntity<Void> deleteAllPatient() {
        physicianServices.deleteAllPhysician();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


