package com.dh.DentalClinicMVC.dto;

public class AppointmentDTO {
    private Long id;
    private Long dentist_id;
    private Long patient_id;
    private String date;

    public AppointmentDTO() {
    }

    public AppointmentDTO(Long id, Long dentist_id, Long patient_id, String date) {
        this.id = id;
        this.dentist_id = dentist_id;
        this.patient_id = patient_id;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDentist_id() {
        return dentist_id;
    }

    public void setDentist_id(Long dentist_id) {
        this.dentist_id = dentist_id;
    }

    public Long getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(Long patient_id) {
        this.patient_id = patient_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
