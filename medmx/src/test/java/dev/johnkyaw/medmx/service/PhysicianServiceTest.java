package dev.johnkyaw.medmx.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import dev.johnkyaw.medmx.controller.PhysicianRestController;
import dev.johnkyaw.medmx.model.Physician;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(PhysicianRestController.class)
public class PhysicianServiceTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private PhysicianServices physicianServices;

    @Test
    @WithMockUser
    public void findPhysician() throws Exception {
        given(this.physicianServices.getPhysicianById(1))
                .willReturn(Optional.of(new Physician(1, null, null, null, "John", "Smith", "Cardiologist", "Cardio Clinic", "123 Main St", " ", "555-1111")));
        MvcResult result = this.mvc.perform(get("/api/physicians/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        String jsonContent = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Physician physician = objectMapper.readValue(jsonContent, Physician.class);

        assertEquals(physician.getSpecialty(), "Cardiologist");
    }
}
