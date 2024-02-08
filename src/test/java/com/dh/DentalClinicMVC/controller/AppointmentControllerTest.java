package com.dh.DentalClinicMVC.controller;

import com.dh.DentalClinicMVC.dto.AppointmentDTO;
import com.dh.DentalClinicMVC.entity.Address;
import com.dh.DentalClinicMVC.entity.Dentist;
import com.dh.DentalClinicMVC.entity.Patient;
import com.dh.DentalClinicMVC.service.IAppointmentService;
import com.dh.DentalClinicMVC.service.IDentistService;
import com.dh.DentalClinicMVC.service.IPatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private IPatientService patientService;
    @Autowired
    private IDentistService dentistService;
    @Autowired
    private IAppointmentService appointmentService;

    @BeforeEach
    public void setUp() {
        dataLoad();
    }

    public void dataLoad() {
        Address address = new Address();
        address.setStreet("Calle A");
        address.setNumber(123);
        address.setLocation("Luján");
        address.setProvince("Mendoza");

        Patient patient = new Patient();
        patient.setName("Juan");
        patient.setLastName("Perez");
        patient.setEmail("juan@gmail.com");
        patient.setCardIdentity(12345);
        patient.setAdmissionOfDate(LocalDate.of(2022,1,29));
        patient.setAddress(address);

        Dentist dentist = new Dentist();
        dentist.setName("Pedro");
        dentist.setLastName("Muelas");
        dentist.setRegistration(547841);

        patientService.save(patient);
        dentistService.save(dentist);

        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setDate("2024-02-11");
        appointmentDTO.setPatient_id(1L);
        appointmentDTO.setDentist_id(1L);

        AppointmentDTO  appointmentSaved = appointmentService.save(appointmentDTO);
    }

    @Test
    public void ListAllAppointmentTest() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                .get("/turnos").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assertFalse(response.getResponse().getContentAsString().isEmpty());
    }

}