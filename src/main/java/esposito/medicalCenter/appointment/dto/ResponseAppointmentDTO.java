package esposito.medicalCenter.appointment.dto;

import esposito.medicalCenter.appointment.AppointmentEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseAppointmentDTO {

    private String patientName;
    private String patientSurname;

    private LocalDateTime appointmentDateTime;

    private String status;

    private String medicalExaminationType;

    public ResponseAppointmentDTO(AppointmentEntity appointmentEntity) {
        setPatientName(appointmentEntity.getPatient().getName());
        setPatientSurname(appointmentEntity.getPatient().getSurname());
        setAppointmentDateTime(appointmentEntity.getAppointmentDate());
        setStatus(appointmentEntity.getAppointmentStatus().name());
        setMedicalExaminationType(appointmentEntity.getMedicalExaminationType().getName());
    }
}
