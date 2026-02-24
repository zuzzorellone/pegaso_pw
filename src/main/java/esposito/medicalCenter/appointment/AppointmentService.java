package esposito.medicalCenter.appointment;

import esposito.medicalCenter.appointment.dto.RequestAppointmentDTO;
import esposito.medicalCenter.appointment.dto.ResponseAppointmentDTO;
import esposito.medicalCenter.medicalExaminationType.MedicalExaminationTypeEntity;
import esposito.medicalCenter.medicalExaminationType.MedicalExaminationTypeRepository;
import esposito.medicalCenter.patient.PatientEntity;
import esposito.medicalCenter.patient.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final MedicalExaminationTypeRepository medicalExaminationTypeRepository;

    @Transactional(readOnly = true)
    public List<ResponseAppointmentDTO> getAllAppointment() {
        List<AppointmentEntity> appointmentEntityList = appointmentRepository.findAll();

        return appointmentEntityList.stream().map(ResponseAppointmentDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ResponseAppointmentDTO createAppointment(RequestAppointmentDTO requestAppointmentDTO) {
        AppointmentEntity newAppointment = new AppointmentEntity();

        Optional<MedicalExaminationTypeEntity> medicalExaminationType = medicalExaminationTypeRepository
                .findMedicalExaminationTypeEntityByName(requestAppointmentDTO.medicalExaminationType());

        if(medicalExaminationType.isEmpty()) {
            MedicalExaminationTypeEntity m1 = new MedicalExaminationTypeEntity();
            m1.setActive(Boolean.TRUE);
            m1.setName(requestAppointmentDTO.medicalExaminationType());
            m1.setDescription("Visita radiografica");

            newAppointment.setMedicalExaminationType(medicalExaminationTypeRepository.save(m1));
        }else {
            newAppointment.setMedicalExaminationType(medicalExaminationType.get());
        }

        newAppointment.setAppointmentDate(requestAppointmentDTO.appointmentDate());

        newAppointment.setAppointmentStatus(AppointmentStatus.WAITING_FOR_PATIENT_CONFIRMATION);

        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setName(requestAppointmentDTO.patient().name());
        patientEntity.setSurname(requestAppointmentDTO.patient().surname());
        patientEntity.setEmail(requestAppointmentDTO.patient().email());
        patientEntity.setTelephoneNumber(requestAppointmentDTO.patient().telephoneNumber());

        newAppointment.setPatient(patientRepository.save(patientEntity));

        AppointmentEntity appointmentEntitySaved = appointmentRepository.save(newAppointment);
        return new ResponseAppointmentDTO(appointmentEntitySaved);
    }
}
