package esposito.medicalCenter.appointment.dto;

import esposito.medicalCenter.appointment.AppointmentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ResponseAppointmentDTO {

    private Long id;
    private String patientName;
    private String patientSurname;

    private LocalDateTime appointmentDateTime;

    private String status;

    private String medicalExaminationType;

    public ResponseAppointmentDTO(AppointmentEntity appointmentEntity) {
        setId(appointmentEntity.getId());
        setPatientName(appointmentEntity.getPatient().getName());
        setPatientSurname(appointmentEntity.getPatient().getSurname());
        setAppointmentDateTime(appointmentEntity.getAppointmentDate());
        setStatus(appointmentEntity.getAppointmentStatus().name());
        setMedicalExaminationType(appointmentEntity.getMedicalExaminationType().getName());
    }
}
