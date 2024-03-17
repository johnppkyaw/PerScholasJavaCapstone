package dev.johnkyaw.medmx.service;

import dev.johnkyaw.medmx.model.Physician;
import dev.johnkyaw.medmx.model.Patient;
import dev.johnkyaw.medmx.repository.PhysicianRepository;
import dev.johnkyaw.medmx.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PhysicianServices {
    private final PhysicianRepository physicianRepository;
    private final PatientRepository patientRepository;

    @Autowired
    public PhysicianServices(PhysicianRepository physicianRepository, PatientRepository patientRepository) {
        this.physicianRepository = physicianRepository;
        this.patientRepository = patientRepository;
    }

    public void addPhysician(Physician physician) {
        try {
            Optional<Physician> existingPhysician = physicianRepository.findByFirstNameAndLastNameAndSpecialty(
                    physician.getFirstName(), physician.getLastName(), physician.getSpecialty());
            if(existingPhysician.isPresent()) {
                throw new RuntimeException("The physician with provided info already exists");
            } else {
                physicianRepository.save(physician);
            }
        } catch(DataIntegrityViolationException e) {
            throw new RuntimeException("Duplicate entry detected: " + e.getMessage());
        }
    }

    public List<Physician> getAllPhysician() {
        return physicianRepository.findAll();
    }

    public Optional<Physician> getPhysicianById(long id) {
        return physicianRepository.findById(id);
    }

    public void updatePhysician(Long id, Physician physician) {
        Optional<Physician> physicianData = physicianRepository.findById(id);
        if(physicianData.isPresent()) {
            Physician _physician = physicianData.get();
            _physician.setFirstName(physician.getFirstName());
            _physician.setLastName(physician.getLastName());
            _physician.setSpecialty(physician.getSpecialty());
            _physician.setClinicName(physician.getClinicName());
            _physician.setClinicAddress(physician.getClinicAddress());
            _physician.setClinicAddress2(physician.getClinicAddress2());
            _physician.setPhone(physician.getPhone());

            // Save the updated physician to the database
            physicianRepository.save(_physician);
        }
    }

    public void unassignPatientFromPhysician(Long patientId, Long physicianId) {
        Optional<Patient> patientData = patientRepository.findById(patientId);
        Optional<Physician> physicianData = physicianRepository.findById(patientId);
        if (patientData.isPresent() && physicianData.isPresent()) {
            Patient patient = patientData.get();
            Physician physician = physicianData.get();

            patient.setPhysician(null);
            physician.getPatients().remove(patient);

            patientRepository.save(patient);
            physicianRepository.save(physician);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient or Physician not found");
        }
    }

    public void assignPatientFromPhysician(Long patientId, Long physicianId) {
        Optional<Patient> patientData = patientRepository.findById(patientId);
        Optional<Physician> physicianData = physicianRepository.findById(patientId);
        if (patientData.isPresent() && physicianData.isPresent()) {
            Patient patient = patientData.get();
            Physician physician = physicianData.get();

            patient.setPhysician(physician);
            physician.getPatients().add(patient);

            patientRepository.save(patient);
            physicianRepository.save(physician);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient or Physician not found");
        }
    }

    public void deletePhysician(long id) {
        physicianRepository.deleteById(id);
    }

    public void deleteAllPhysician() {
        physicianRepository.deleteAll();
    }



}
