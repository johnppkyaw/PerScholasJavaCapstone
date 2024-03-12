package dev.johnkyaw.medmx.service;

import dev.johnkyaw.medmx.model.Patient;
import dev.johnkyaw.medmx.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PatientServices {
    private PatientRepository patientRepository;
    @Autowired
    public PatientServices(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getAllPatients() {
        return new ArrayList<>(patientRepository.findAll());
    }

    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    public void addPatient(Patient patient) {
        patientRepository.save(patient);
    }

    public void updatePatient(Long id, Patient patient) {
        Optional<Patient> patientData = patientRepository.findById(id);
        if(patientData.isPresent()) {
            Patient _patient = patientData.get();
            _patient.setFirstName(patient.getFirstName());
            _patient.setLastName(patient.getLastName());
            _patient.setAddress(patient.getAddress());
            _patient.setAddress2(patient.getAddress2());
            _patient.setAge(patient.getAge());
            _patient.setGender(patient.getGender());
        }
    }
}
