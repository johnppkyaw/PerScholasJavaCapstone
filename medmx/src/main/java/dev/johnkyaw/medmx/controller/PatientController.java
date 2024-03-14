package dev.johnkyaw.medmx.controller;

import dev.johnkyaw.medmx.model.Patient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PatientController {

    @GetMapping("/")
    public String loadHomePage() {
        return "index";
    }

    @GetMapping("/patientlist")
    public String loadPatientListPage() {
        return "patientlist";
    }
    @GetMapping("/addpatient")
    public String loadCreatePatientPage(Model model) {
        Patient patient = new Patient();
        model.addAttribute("patient", patient);
        return "createpatient";
    }

}
