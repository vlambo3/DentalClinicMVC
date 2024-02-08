package com.dh.DentalClinicMVC.service;

import com.dh.DentalClinicMVC.entity.Patient;
import com.dh.DentalClinicMVC.service.impl.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PatientServiceTest {

    @Autowired
    private PatientService patientService;

    @Test
    void findById() {
        Long idPatient = 3L;

        //buscar al paciente
        Optional< Patient> patient = patientService.findById(idPatient);
        assertNotNull(patient);
    }
}