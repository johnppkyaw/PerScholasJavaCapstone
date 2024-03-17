package dev.johnkyaw.medmx.service;

import dev.johnkyaw.medmx.model.Physician;
import dev.johnkyaw.medmx.repository.PhysicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhysicianServices {
    private final PhysicianRepository physicianRepository;

    @Autowired
    public PhysicianServices(PhysicianRepository physicianRepository) {
        this.physicianRepository = physicianRepository;
    }

    public void addPhysician(Physician physician) {
        physicianRepository.save(physician);
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

    public void deletePhysician(long id) {
        physicianRepository.deleteById(id);
    }

    public void deleteAllPhysician() {
        physicianRepository.deleteAll();
    }



}
