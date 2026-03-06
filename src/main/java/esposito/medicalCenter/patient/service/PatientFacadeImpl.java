package esposito.medicalCenter.patient.service;

import esposito.medicalCenter.appointment.dto.RequestPatientDTO;
import esposito.medicalCenter.patient.api.PatientIntegrationDTO;
import esposito.medicalCenter.patient.entity.PatientEntity;
import esposito.medicalCenter.patient.repository.PatientRepository;
import esposito.medicalCenter.patient.api.PatientFacade;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PatientFacadeImpl implements PatientFacade {

    private final PatientRepository patientRepository;

    @Override
    @Transactional
    public PatientIntegrationDTO getOrCreatePatient(RequestPatientDTO requestPatientDTO) {
        PatientEntity patientEntity = patientRepository.findPatientEntityByEmail(requestPatientDTO.email())
                .orElseGet(() -> {
                    PatientEntity newPatient = new PatientEntity();
                    newPatient.setName(requestPatientDTO.name());
                    newPatient.setSurname(requestPatientDTO.surname());
                    newPatient.setEmail(requestPatientDTO.email());
                    newPatient.setTelephoneNumber(requestPatientDTO.telephoneNumber());

                     return patientRepository.save(newPatient);

                });

        return mapEntityToDTO(patientEntity);
    }

    @Override
    public PatientIntegrationDTO getPatientById(Long id) {
        PatientEntity patientEntity = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with ID: "+ id));

        return mapEntityToDTO(patientEntity);
    }

    @Override
    public Map<Long, PatientIntegrationDTO> getPatientListByIds(Set<Long> patientIdSet) {
        Map<Long, PatientIntegrationDTO> mapToReturn = new HashMap<>();

        patientRepository.findAllById(patientIdSet).forEach(patientEntity ->
            {
                mapToReturn.put(patientEntity.getId(),
                        mapEntityToDTO(patientEntity));
            }
        );

        return mapToReturn;
    }

    private PatientIntegrationDTO mapEntityToDTO(PatientEntity patientEntity) {
        return new PatientIntegrationDTO(patientEntity.getId(),
                patientEntity.getName(),
                patientEntity.getSurname(),
                patientEntity.getEmail(),
                patientEntity.getTelephoneNumber());
    }
}
