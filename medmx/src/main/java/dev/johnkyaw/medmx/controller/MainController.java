package dev.johnkyaw.medmx.controller;

import dev.johnkyaw.medmx.dto.PhysicianDTO;
import dev.johnkyaw.medmx.model.Note;
import dev.johnkyaw.medmx.model.Patient;
import dev.johnkyaw.medmx.model.Physician;
import dev.johnkyaw.medmx.service.NoteServices;
import dev.johnkyaw.medmx.service.PatientServices;
import dev.johnkyaw.medmx.service.PhysicianServices;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class MainController {
    Logger log = LoggerFactory.getLogger(MainController.class);
    private PhysicianServices physicianServices;
    private PatientServices patientServices;

    private NoteServices noteServices;

    @Autowired
    public MainController(PhysicianServices physicianServices, PatientServices patientServices, NoteServices noteServices) {
        log.info("injected physicianServices and patientServices to main controller");
        this.physicianServices = physicianServices;
        this.patientServices = patientServices;
        this.noteServices = noteServices;
    }

    @GetMapping("/")
    public String loadHomePage(Model model) {
        // Retrieve authentication object
        log.info("Loading home page");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Physician physician = physicianServices.findUserByUsername(username);
            if(physician != null) {
                log.info("A physician is logged in");
                model.addAttribute("physician", physician);
            }
        }
        return "index";
    }

//    @GetMapping("/home")
//    public String loginSuccessHomePage() {
//        log.info("Login Success!");
//        return "index";
//    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        log.info("Loaded registration page");
        // create a model object to store form data
        model.addAttribute("physicianDTO", new PhysicianDTO());
        return "register";
    }

    // handler method to handle physician registration from submit request
    @PostMapping("/register/save")
    public String registrationProcess(@Valid @ModelAttribute("physicianDTO") PhysicianDTO physicianDTO, BindingResult bindingResult,
                               Model model) {
        if(bindingResult.hasErrors())
        {
            log.warn("Wrong attempt");
            model.addAttribute("physicianDTO", physicianDTO);
            return "register";
        }
        log.info("Registration successful");
        physicianServices.creat(physicianDTO);
        return "index";

//        System.out.println(physician);
//        Physician existingPhysician = physicianServices.findByIdAndFirstNameAndLastName(physician.getId(), physician.getFirstName(), physician.getLastName());
//
//        // Check if physician exists
//        if (existingPhysician != null) {
//            // If physician exists but doesn't have username and password assigned
//            if (existingPhysician.getUsername() == null || existingPhysician.getUsername().isEmpty() ||
//                    existingPhysician.getPassword() == null || existingPhysician.getPassword().isEmpty()) {
//                // Assign username and password from the registration form
//                existingPhysician.setUsername(physician.getUsername());
//                existingPhysician.setPassword(physician.getPassword());
//                // Update the physician in the database
//                physicianServices.updatePhysician(existingPhysician.getId(), existingPhysician);
//                return "redirect:/register?success";
//            } else {
//                // Physician already registered
//                result.rejectValue("username", null, "There is already an account registered with the same email");
//            }
//        } else {
//            // Physician not found in the database
//            result.rejectValue("id", null, "The physician with the provided ID does not exist");
//        }
//
//        if (result.hasErrors()) {
//            model.addAttribute("physician", physician);
//            return "/register";
//        }
//
//        return "redirect:/register?success";
    }

    @GetMapping("/login")
    public String getLoginPage()
    {
        log.info("Login page displayed");
        return "login";
    }

    @GetMapping("/patientlist")
    public String loadPatientListPage(Model model) {

        // Retrieve authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Physician physician = physicianServices.findUserByUsername(username);

            if(physician != null) {
                model.addAttribute("physicianId", physician.getId());
            }
        }
        return "patientlist";
    }
    @GetMapping("/addpatient")
    public String loadCreatePatientPage(Model model) {
        // Retrieve authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Physician physician = physicianServices.findUserByUsername(username);

            if(physician != null) {
                Patient patient = new Patient();
                model.addAttribute("patient", patient);
                model.addAttribute("physician", physician);

            }
        }
        return "createpatient";
    }


    @GetMapping("/schedule")
    public String loadSchedulePage(Model model) {
        // Retrieve authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Physician physician = physicianServices.findUserByUsername(username);
            if(physician != null) {
                long physicianId = physician.getId();
                model.addAttribute("physician", physician);

                List<Patient> patients = patientServices.getAllPatientsByPhysicianId(physicianId);
                model.addAttribute("patients", patients);
            }

        }
        return "schedule-page";
    }

    @GetMapping("/patientDetail/{id}")
    public String loadPatientDetailPage(@PathVariable("id") long id, Model model) {
        //Get patient object and add to the attribute
        Optional<Patient> patientData = patientServices.getPatientById(id);
        if(patientData.isPresent()){
            log.info("Patient is present adding to model attribute");
            Patient patient = patientData.get();
            System.out.println(patient);
            model.addAttribute("patient", patient);
        } else {
            log.warn("Patient not found!");
        }
        //Get logged in physician and add that to attribute
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Physician physician = physicianServices.findUserByUsername(username);
            if(physician != null) {
                model.addAttribute("physician", physician);
            }
        }
        //Get all notes by patient id;
        List<Note> allPatientNotes = noteServices.getAllNotesByPatientId(id);
        model.addAttribute("notes", allPatientNotes);
        log.info("Directing to patient-detail html");
        return "patient-detail";
    }
}
