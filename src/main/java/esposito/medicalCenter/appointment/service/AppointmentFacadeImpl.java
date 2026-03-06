package esposito.medicalCenter.appointment.service;

import esposito.medicalCenter.appointment.api.AppointmentFacade;
import esposito.medicalCenter.appointment.repository.AppointmentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentFacadeImpl implements AppointmentFacade {

    private final AppointmentRepository appointmentRepository;

    @Override
    public void existsById(Long id) {
        appointmentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("No Appointment Found with id: " + id)
        );
    }

    @Override
    public Long getPatientIdByAppointment(Long id) {
        return appointmentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("No Appoaintment Found with id: "+ id)
        ).getPatientId();
    }
}
