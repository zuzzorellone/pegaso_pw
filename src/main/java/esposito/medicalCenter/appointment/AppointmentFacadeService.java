package esposito.medicalCenter.appointment;

import esposito.medicalCenter.appointment.dto.RequestAppointmentDTO;
import esposito.medicalCenter.appointment.dto.ResponseAppointmentDTO;
import esposito.medicalCenter.patient.PatientEntity;
import esposito.medicalCenter.patient.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AppointmentFacadeService {

    private final AppointmentService appointmentService;
    private final PatientService patientService;

    @Transactional
    public ResponseAppointmentDTO createAppointmentAndHandlePatient(RequestAppointmentDTO requestAppointmentDTO) {
        PatientEntity patientEntity = patientService.getOrCreatePatient(requestAppointmentDTO.patient());

        return appointmentService.createAppointmentForPatient(patientEntity, requestAppointmentDTO);
    }
}