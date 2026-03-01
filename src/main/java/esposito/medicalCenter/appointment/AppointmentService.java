package esposito.medicalCenter.appointment;

import esposito.medicalCenter.appointment.dto.RequestAppointmentDTO;
import esposito.medicalCenter.appointment.dto.ResponseAppointmentDTO;
import esposito.medicalCenter.medicalExaminationType.MedicalExaminationTypeRepository;
import esposito.medicalCenter.patient.PatientEntity;
import esposito.medicalCenter.patient.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final MedicalExaminationTypeRepository medicalExaminationTypeRepository;

    @Transactional(readOnly = true)
    public List<ResponseAppointmentDTO> getAllAppointment() {
        List<AppointmentEntity> appointmentEntityList = appointmentRepository.findAll();

        return appointmentEntityList.stream().map(ResponseAppointmentDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ResponseAppointmentDTO getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .map(ResponseAppointmentDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found with ID: " + id));
    }

    @Transactional
    public ResponseAppointmentDTO createAppointmentForPatient(PatientEntity patient, RequestAppointmentDTO request) {

        AppointmentEntity newAppointment = new AppointmentEntity();

        var examType = medicalExaminationTypeRepository.findMedicalExaminationTypeEntityByName(request.medicalExaminationType())
                .orElseThrow(() -> new EntityNotFoundException("Medical Examination Type not found: " + request.medicalExaminationType()));

        newAppointment.setMedicalExaminationType(examType);
        newAppointment.setAppointmentDate(request.appointmentDate());
        newAppointment.setAppointmentStatus(AppointmentStatus.WAITING_FOR_PATIENT_CONFIRMATION);

        newAppointment.setPatient(patient);

        return new ResponseAppointmentDTO(appointmentRepository.save(newAppointment));
    }

    @Transactional
    public ResponseAppointmentDTO updateAppointment(Long id, RequestAppointmentDTO requestAppointmentDTO) {
        AppointmentEntity appointmentToUpdate = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found with ID: " + id));

        appointmentToUpdate.setAppointmentDate(requestAppointmentDTO.appointmentDate());
        appointmentToUpdate.setAppointmentStatus(AppointmentStatus.WAITING_FOR_PATIENT_CONFIRMATION);

        String currentExamName = appointmentToUpdate.getMedicalExaminationType() != null
                ? appointmentToUpdate.getMedicalExaminationType().getName()
                : null;

        if (!java.util.Objects.equals(requestAppointmentDTO.medicalExaminationType(), currentExamName)) {
            appointmentToUpdate.setMedicalExaminationType(
                    medicalExaminationTypeRepository.findMedicalExaminationTypeEntityByName(requestAppointmentDTO.medicalExaminationType())
                            .orElseThrow(() -> new EntityNotFoundException("Medical Examination Type not found with name: " + requestAppointmentDTO.medicalExaminationType())));
        }

        return new ResponseAppointmentDTO(appointmentRepository.save(appointmentToUpdate));
    }
}
