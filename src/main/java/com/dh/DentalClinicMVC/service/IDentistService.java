package com.dh.DentalClinicMVC.service;

import com.dh.DentalClinicMVC.entity.Dentist;
import com.dh.DentalClinicMVC.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IDentistService {
    Dentist save (Dentist dentist);
    Optional<Dentist> findById(Long id);
    void update(Dentist dentist);
    void delete(Long id) throws ResourceNotFoundException;
    List<Dentist> findAll();
    Optional<Dentist> findByRegistration(Integer registration);
}
