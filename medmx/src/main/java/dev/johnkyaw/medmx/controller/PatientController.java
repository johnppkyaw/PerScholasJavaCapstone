package dev.johnkyaw.medmx.controller;

import dev.johnkyaw.medmx.service.PatientServices;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import dev.johnkyaw.medmx.model.Patient;

import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("/api")
public class PatientController {
    PatientServices patientServices;

    @Autowired
    public PatientController(PatientServices patientServices) {
        this.patientServices = patientServices;
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

        }



    }

}


