package dev.johnkyaw.medmx.service;
import dev.johnkyaw.medmx.dto.PhysicianDTO;
import dev.johnkyaw.medmx.model.Physician;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface PhysicianService extends UserDetailsService {
    public UserDetails loadUserByUsername(String userName);
    public void creat(PhysicianDTO physicianDTO);
    public Physician findUserByUsername(String username);
}
