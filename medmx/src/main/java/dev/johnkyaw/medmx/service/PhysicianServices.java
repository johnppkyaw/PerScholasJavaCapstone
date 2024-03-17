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

    public void createPhysician(Physician physician) {
        physicianRepository.save(physician);
    }

    public List<Physician> getAllPhysician() {
        return physicianRepository.findAll();
    }

    public Optional<Physician> getPhysicianById(long id) {
        return physicianRepository.findById(id);
    }



}
