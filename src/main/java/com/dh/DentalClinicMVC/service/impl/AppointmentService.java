package com.dh.DentalClinicMVC.service.impl;

import com.dh.DentalClinicMVC.dto.AppointmentDTO;
import com.dh.DentalClinicMVC.entity.Appointment;
import com.dh.DentalClinicMVC.entity.Dentist;
import com.dh.DentalClinicMVC.entity.Patient;
import com.dh.DentalClinicMVC.exception.ResourceNotFoundException;
import com.dh.DentalClinicMVC.repository.IAppointmentRepository;
import com.dh.DentalClinicMVC.service.IAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

@Service
public class AppointmentService implements IAppointmentService {

    private IAppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService(IAppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public AppointmentDTO save(AppointmentDTO appointmentDTO) {
        //mapear nuestras entidades como DTO a mano
        //instanciar una entidad de turno
        Appointment appointmentEntity = new Appointment();

        //instanciar un paciente
        Patient patientEntity = new Patient();
        patientEntity.setId(appointmentDTO.getPatient_id());

        //instanciar un odontólogo
        Dentist dentistEntity = new Dentist();
        dentistEntity.setId(appointmentDTO.getDentist_id());

        //seteamos el paciente y el odontólogo a nuestra entidad turno
        appointmentEntity.setPatient(patientEntity);
        appointmentEntity.setDentist(dentistEntity);

        //convertir el string del turnoDto que es la fecha a LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(appointmentDTO.getDate(), formatter);

        //setear la fecha
        appointmentEntity.setDate(date);

        //persistir en la BD
        appointmentRepository.save(appointmentEntity);

        //vamos a trabajar con el DTO que debemos devolver
        //generar una instancia de turno DTO
        AppointmentDTO appointmentDTOToReturn = new AppointmentDTO();

        //le seteamos los datos de la entidad que persistimos
        appointmentDTOToReturn.setId(appointmentEntity.getId());
        appointmentDTOToReturn.setDate(appointmentEntity.getDate().toString());
        appointmentDTOToReturn.setDentist_id(appointmentEntity.getDentist().getId());
        appointmentDTOToReturn.setPatient_id(appointmentEntity.getPatient().getId());

        return appointmentDTOToReturn;
    }

    @Override
    public Optional<AppointmentDTO> findById(Long id) throws ResourceNotFoundException {
        //vamos a buscar la entidad por id a la BD
        Optional<Appointment> appointmentToLookFor = appointmentRepository.findById(id);

        //instanciamos el dto
        Optional<AppointmentDTO> appointmentDTO = null;

        if (appointmentToLookFor.isPresent()) {
            //recuperar la entidad que se encontró y guardarla en la variable appointmen
            Appointment appointment = appointmentToLookFor.get();

            //trabajar sobre la información que tenemos que devolver: dto
            //vamos a crear una intancia de turno dto para devolver
            AppointmentDTO appointmentDTOToReturn = new AppointmentDTO();
            appointmentDTOToReturn.setId(appointment.getId());
            appointmentDTOToReturn.setPatient_id(appointment.getPatient().getId());
            appointmentDTOToReturn.setDentist_id(appointment.getDentist().getId());
            appointmentDTOToReturn.setDate(appointment.getDate().toString());

            appointmentDTO = Optional.of(appointmentDTOToReturn);
            return appointmentDTO;

        } else {
            throw new ResourceNotFoundException("No se encontró el turno con id: " + id);
        }




    }

    @Override
    public AppointmentDTO update(AppointmentDTO appointmentDTO) throws Exception {

        //chequeo que el turno a actualizar exista
        if(appointmentRepository.findById(appointmentDTO.getId()).isPresent()) {

            //buscar la entidad en la BD
            Optional<Appointment> appointmentEntity = appointmentRepository.findById(appointmentDTO.getId());

            //instanciar un paciente
            Patient patientEntity = new Patient();
            patientEntity.setId(appointmentDTO.getPatient_id());

            //instanciar un odontólogo
            Dentist dentistEntity = new Dentist();
            dentistEntity.setId(appointmentDTO.getDentist_id());

            //seteamos el paciente y el odontólogo a nuestra entidad turno
            appointmentEntity.get().setPatient(patientEntity);
            appointmentEntity.get().setDentist(dentistEntity);

            //convertir el string del turnoDto que es la fecha a LocalDate
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(appointmentDTO.getDate(), formatter);

            //setear la fecha
            appointmentEntity.get().setDate(date);

            //persistir en la BD
            appointmentRepository.save(appointmentEntity.get());

            //vamos a trabajar sobre la respuesta (dto) a devolver
            AppointmentDTO appointmentDTOToReturn = new AppointmentDTO();
            appointmentDTOToReturn.setId(appointmentEntity.get().getId());
            appointmentDTOToReturn.setPatient_id(appointmentEntity.get().getPatient().getId());
            appointmentDTOToReturn.setDentist_id(appointmentEntity.get().getDentist().getId());
            appointmentDTOToReturn.setDate(appointmentEntity.get().getDate().toString());

            return appointmentDTOToReturn;
        } else {
            throw new Exception("No se pudo actualizar el turno");
        }

    }

    @Override
    public Optional<AppointmentDTO> delete(Long id) throws ResourceNotFoundException {
        //vamos a buscar la entidad por id en la BD y guardara en un Optional
        Optional<Appointment> appointmentToLookFor = appointmentRepository.findById(id);

        Optional<AppointmentDTO> appointmentDTO;
        if (appointmentToLookFor.isPresent()) {
            //recuperar el turno que se encontró y lo vamos a guardar en una variable turno
            Appointment appointment =  appointmentToLookFor.get();

            //vamos a devolver un dto
            //vamos a trabajar sobre ese dto a devolver
            //crear una instancia de ese dto
            AppointmentDTO appointmentDTOToReturn = new AppointmentDTO();
            appointmentDTOToReturn.setId(appointment.getId());
            appointmentDTOToReturn.setDentist_id(appointment.getDentist().getId());
            appointmentDTOToReturn.setPatient_id(appointment.getPatient().getId());
            appointmentDTOToReturn.setDate(appointment.getDate().toString());

            appointmentDTO = Optional.of(appointmentDTOToReturn);
            return appointmentDTO;

        } else {
            //vamos a lanzar la exception
            throw new ResourceNotFoundException("No se encontró el turno con id: " + id);
        }

    }

    @Override
    public List<AppointmentDTO> findAll() {
        //vamos a traernos las entidades de la BD y la vamos a guardar en una lista
        List<Appointment> appointments = appointmentRepository.findAll();

        //vamos a crear una lista vacía de turnos DTO
        List<AppointmentDTO> appointmentDTOS = new ArrayList<>();

        //recorremos la lista de entidades de turno para luego
        //guardarlas en la lista de turnos DTO
        for (Appointment appointment: appointments) {
            appointmentDTOS.add(new AppointmentDTO(appointment.getId(),
                    appointment.getPatient().getId(), appointment.getDentist().getId(),
                    appointment.getDate().toString()));
        }

        return appointmentDTOS;
    }
}
