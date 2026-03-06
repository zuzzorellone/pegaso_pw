package esposito.medicalCenter.appointment.service;

import esposito.medicalCenter.appointment.AppointmentStatus;
import esposito.medicalCenter.appointment.dto.RequestAppointmentDTO;
import esposito.medicalCenter.appointment.dto.ResponseAppointmentDTO;
import esposito.medicalCenter.appointment.entity.AppointmentEntity;
import esposito.medicalCenter.appointment.repository.AppointmentRepository;
import esposito.medicalCenter.appointment.repository.MedicalExaminationTypeRepository;
import esposito.medicalCenter.medicalExaminationType.api.MedicalExaminationFacade;
import esposito.medicalCenter.patient.api.PatientFacade;
import esposito.medicalCenter.patient.api.PatientIntegrationDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final MedicalExaminationTypeRepository medicalExaminationTypeRepository;
    private final PatientFacade patientFacade;

    @Transactional(readOnly = true)
    public List<ResponseAppointmentDTO> getAllAppointment() {
        List<AppointmentEntity> appointmentEntityList = appointmentRepository.findAll();

        Set<Long> patientIDSet = appointmentEntityList.stream().map(AppointmentEntity::getPatientId).collect(Collectors.toSet());

        Map<Long, PatientIntegrationDTO> patientMap = patientFacade.getPatientListByIds(patientIDSet);


        return appointmentEntityList.stream().map(appointmentEntity ->
                {
                    PatientIntegrationDTO patientIntegrationDTO = patientMap.get(appointmentEntity.getPatientId());
                    return mapEntityToDTO(appointmentEntity, patientIntegrationDTO);
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ResponseAppointmentDTO getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .map(
                        appointmentEntity -> {
                            PatientIntegrationDTO patientIntegrationDTO = patientFacade.getPatientById(appointmentEntity.getPatientId());
                            return mapEntityToDTO(appointmentEntity, patientIntegrationDTO);
                        }
                )
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found with ID: " + id));
    }

    @Transactional
    public ResponseAppointmentDTO createAppointmentForPatient(PatientIntegrationDTO patient, RequestAppointmentDTO request) {

        AppointmentEntity newAppointment = new AppointmentEntity();

        var examType = medicalExaminationTypeRepository.findMedicalExaminationTypeEntityByName(request.medicalExaminationType())
                .orElseThrow(() -> new EntityNotFoundException("Medical Examination Type not found: " + request.medicalExaminationType()));

        newAppointment.setMedicalExaminationType(examType);
        newAppointment.setAppointmentDate(request.appointmentDate());
        newAppointment.setAppointmentStatus(AppointmentStatus.WAITING_FOR_PATIENT_CONFIRMATION);

        newAppointment.setPatientId(patient.id());

        newAppointment = appointmentRepository.save(newAppointment);

        return mapEntityToDTO(newAppointment, patient);
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

        appointmentToUpdate= appointmentRepository.save(appointmentToUpdate);
        PatientIntegrationDTO patientDTO = patientFacade.getPatientById(appointmentToUpdate.getPatientId());

        return mapEntityToDTO(appointmentToUpdate, patientDTO);
    }

    @Transactional
    public ResponseAppointmentDTO createAppointmentAndHandlePatient(RequestAppointmentDTO requestAppointmentDTO) {
        PatientIntegrationDTO patientID = patientFacade.getOrCreatePatient(requestAppointmentDTO.patient());

        return createAppointmentForPatient(patientID, requestAppointmentDTO);
    }

    private ResponseAppointmentDTO mapEntityToDTO(AppointmentEntity appointmentEntity, PatientIntegrationDTO patientIntegrationDTO) {
        ResponseAppointmentDTO responseAppointmentDTO = new ResponseAppointmentDTO();
        responseAppointmentDTO.setId(appointmentEntity.getId());
        responseAppointmentDTO.setAppointmentDateTime(appointmentEntity.getAppointmentDate());
        responseAppointmentDTO.setMedicalExaminationType(appointmentEntity.getMedicalExaminationType().getName());

        if(patientIntegrationDTO != null) {
            responseAppointmentDTO.setPatientName(patientIntegrationDTO.name());
            responseAppointmentDTO.setPatientSurname(patientIntegrationDTO.surname());
        }

        return responseAppointmentDTO;
    }
}
