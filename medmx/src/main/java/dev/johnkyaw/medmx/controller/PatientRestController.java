package dev.johnkyaw.medmx.controller;

import dev.johnkyaw.medmx.service.PatientServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import dev.johnkyaw.medmx.model.Patient;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PatientRestController {
    PatientServices patientServices;
    private final Logger logger = LoggerFactory.getLogger(PatientRestController.class);

    @Autowired
    public PatientRestController(PatientServices patientServices) {
        this.patientServices = patientServices;
    }

    @Transactional(readOnly = true)
    @GetMapping("/physicians/{id}/patients")
    public ResponseEntity<List<Patient>> getAllPatientsByPhysician(@PathVariable("id") long id) {
        return ResponseEntity.ok(patientServices.getAllPatientsByPhysicianId(id));
    }

    @Transactional
    @GetMapping("/patients")
    public ResponseEntity<List<Patient>> getAllPatients() {
        return ResponseEntity.ok(patientServices.getAllPatients());
    }

    @Transactional
    @GetMapping("/patients/{id}")
    public ResponseEntity<Optional<Patient>> getPatientById(@PathVariable("id") long id) {
        return ResponseEntity.ok(patientServices.getPatientById(id));
    }

    @Transactional
    @PostMapping("/patients")
    public ResponseEntity<Void> createPatient(@RequestBody Patient patient) {
        patientServices.addPatient(patient);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Transactional
    @PutMapping("/patients/{id}")
    public ResponseEntity<Void> updatePatient(@PathVariable("id") long id, @RequestBody Patient patient) {
        Optional<Patient> patientData = patientServices.getPatientById(id);
        if(patientData.isPresent()) {
            patientServices.updatePatient(id, patient);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            logger.warn("Attempted editing but the patient not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @DeleteMapping("/patients/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable("id") long id) {
        Optional<Patient> patientData = patientServices.getPatientById(id);
        if(patientData.isPresent()) {
            patientServices.deletePatient(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            logger.warn("Attempted editing but the patient not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}


