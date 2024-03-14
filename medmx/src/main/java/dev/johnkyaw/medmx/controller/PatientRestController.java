package dev.johnkyaw.medmx.controller;

import dev.johnkyaw.medmx.service.PatientServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    @GetMapping("/physicians/{id}/patients")
    public List<Patient> getAllPatientsByPhysician(@PathVariable("id") long id) {
        return patientServices.getAllPatientsByPhysicianId(id);
    }
    @GetMapping("/patients")
    public List<Patient> getAllPatients() {
        return patientServices.getAllPatients();
    }
    @GetMapping("/patients/{id}")
    public Optional<Patient> getPatientById(@PathVariable("id") long id) {
        return patientServices.getPatientById(id);
    }

    @PostMapping("/patients")
    public void createPatient(@RequestBody Patient patient) {
        patientServices.addPatient(patient);
    }

    @PutMapping("/patients/{id}")
    public void updatePatient(@PathVariable("id") long id, @RequestBody Patient patient) {
        Optional<Patient> patientData = patientServices.getPatientById(id);
        if(patientData.isPresent()) {
            patientServices.updatePatient(id, patient);
        } else {
            logger.warn("Attempted editing but the patient not found!");
        }
    }

    @DeleteMapping("/patients/{id}")
    public void deletePatient(@PathVariable("id") long id) {
        Optional<Patient> patientData = patientServices.getPatientById(id);
        if(patientData.isPresent()) {
            patientServices.deletePatient(id);
        } else {
            logger.warn("Attempted editing but the patient not found!");
        }
    }
}


