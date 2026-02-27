package esposito.medicalCenter.patient;

import esposito.medicalCenter.appointment.dto.RequestPatientDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientEntity getOrCreatePatient(RequestPatientDTO requestPatientDTO) {
        return patientRepository.findPatientEntityByEmail(requestPatientDTO.email())
                .orElseGet(() -> {
                    PatientEntity newPatient = new PatientEntity();
                    newPatient.setName(requestPatientDTO.name());
                    newPatient.setSurname(requestPatientDTO.surname());
                    newPatient.setEmail(requestPatientDTO.email());
                    newPatient.setTelephoneNumber(requestPatientDTO.telephoneNumber());

                    return patientRepository.save(newPatient);
                });
    }
}
