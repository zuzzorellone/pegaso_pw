package esposito.medicalCenter.patient.api;

import esposito.medicalCenter.appointment.dto.RequestPatientDTO;

import java.util.Map;
import java.util.Set;

public interface PatientFacade {

    public PatientIntegrationDTO getOrCreatePatient(RequestPatientDTO requestPatientDTO);

    public Map<Long, PatientIntegrationDTO> getPatientListByIds(Set<Long> patientIdSet);
    public PatientIntegrationDTO getPatientById(Long id);
}
