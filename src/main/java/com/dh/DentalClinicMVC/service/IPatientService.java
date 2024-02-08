package com.dh.DentalClinicMVC.service;

import com.dh.DentalClinicMVC.entity.Patient;
import com.dh.DentalClinicMVC.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IPatientService {
    Patient save (Patient patient);
    Optional<Patient> findById(Long id);
    void update(Patient patient);
    void delete(Long id) throws ResourceNotFoundException;
    List<Patient> findAll();
}
