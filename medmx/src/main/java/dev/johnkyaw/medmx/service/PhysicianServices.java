package dev.johnkyaw.medmx.service;

import dev.johnkyaw.medmx.dto.PhysicianDTO;
import dev.johnkyaw.medmx.model.Physician;
import dev.johnkyaw.medmx.model.Patient;
import dev.johnkyaw.medmx.model.PhysicianPrincipal;
import dev.johnkyaw.medmx.model.Role;
import dev.johnkyaw.medmx.repository.PhysicianRepository;
import dev.johnkyaw.medmx.repository.PatientRepository;
import dev.johnkyaw.medmx.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import dev.johnkyaw.medmx.config.SecurityConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PhysicianServices implements PhysicianService {
    private final PhysicianRepository physicianRepository;
    private PatientRepository patientRepository;
    private RoleServiceImpl roleService;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public PhysicianServices(PhysicianRepository physicianRepository, PatientRepository patientRepository, RoleServiceImpl roleService, @Lazy BCryptPasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        super();
        this.physicianRepository = physicianRepository;
        this.patientRepository = patientRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public Physician findByIdAndFirstNameAndLastName(Long id, String firstName, String lastName) {
        return physicianRepository.findByIdAndFirstNameAndLastName(id, firstName, lastName);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Physician physician = physicianRepository.findByUsername(username);
        if(physician != null) {
            return new PhysicianPrincipal(physician, roleService.getRolesByUser(physician.getId()));
        }else {
            log.warn("Invalid username or password {}", username);
            throw new UsernameNotFoundException("Invalid email or password");
        }
    }

    @Override
    public void creat(PhysicianDTO physicianDTO) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Physician physician = modelMapper.map(physicianDTO, Physician.class);
        physician.setPassword(passwordEncoder.encode(physician.getPassword()));

        checkRoleExist();

        if(physicianRepository.findAll().isEmpty()) {
            physician.setRoles(Arrays.asList(roleService.findRoleByRoleName("ROLE_ADMIN")));
        } else {
            physician.setRoles(Arrays.asList(roleService.findRoleByRoleName("ROLE_PHYSICIAN")));
        }
        physicianRepository.save(physician);
    }

    @Override
    public Physician findUserByUsername(String username) {
        return physicianRepository.findByUsername(username);
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

    private void checkRoleExist() {
        String[] roles = {"ROLE_ADMIN", "ROLE_PHYSICIAN", "ROLE_PATIENT"};
        for(String roleName : roles) {
            Role roleData = roleRepository.findByName(roleName);
            if(roleData == null) {
                Role role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
            }
        }
    }

    public void assignPatientFromPhysician(Long physicianId, Long patientId) {
        Optional<Patient> patientData = patientRepository.findById(patientId);
        Optional<Physician> physicianData = physicianRepository.findById(physicianId);
        if (patientData.isPresent() && physicianData.isPresent()) {
            Patient patient = patientData.get();
            Physician physician = physicianData.get();

            patient.setPhysician(physician);

            patientRepository.save(patient);
            physicianRepository.save(physician);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient or Physician not found");
        }
    }

    public void unassignPatientFromPhysician(Long physicianId, Long patientId) {
        Optional<Patient> patientData = patientRepository.findById(patientId);
        Optional<Physician> physicianData = physicianRepository.findById(physicianId);
        if (patientData.isPresent() && physicianData.isPresent()) {
            Patient patient = patientData.get();
            Physician physician = physicianData.get();

            patient.setPhysician(null);

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
