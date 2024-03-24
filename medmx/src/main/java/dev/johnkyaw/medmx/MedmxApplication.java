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
}

