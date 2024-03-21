package dev.johnkyaw.medmx;

import dev.johnkyaw.medmx.model.Physician;
import dev.johnkyaw.medmx.model.Role;
import dev.johnkyaw.medmx.repository.PhysicianRepository;
import dev.johnkyaw.medmx.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class MedmxApplication{


	public static void main(String[] args) {
		SpringApplication.run(MedmxApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		initRoles();
//		initUsers();
//	}
//
//	private void initRoles() {
//		List<Role> roles = roleService.getAllRoles();
//		System.out.println(roles);
//		if (roles.isEmpty()) {
//			roleService.saveRole(new Role("ROLE_ADMIN"));
//			roleService.saveRole(new Role("ROLE_USER"));
//			roleService.saveRole(new Role("ROLE_GUEST"));
//		}
//	}
//
//	private void initUsers() {
//		//Login 'admin@admin.com'
//		//Password 'test'
//		Physician physician = physicianRepository.findByUsername("admin@admin.com");
//		System.out.println(physician);
//		if (physician == null) {
//			Physician newPhysician = new Physician("John", "Smith", "Cardiologist", "Cardio Clinic", "123 Main St", "", "555-1111");
//			newPhysician.setUsername("admin@admin.com");
//			newPhysician.setPassword("$2a$11$DZfZLO720bZby.1QWCu81.gg2BUYCJC7PSsjEUMho.ZaVUVC1h9ZC");
//			newPhysician.setRoles(Arrays.asList(roleService.findRoleByRoleName("ROLE_ADMIN")));
//			physicianRepository.save(newPhysician);
//		}
//	}
}

