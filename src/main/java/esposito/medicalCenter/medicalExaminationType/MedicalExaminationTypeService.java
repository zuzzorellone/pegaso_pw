package esposito.medicalCenter.medicalExaminationType;

import esposito.medicalCenter.medicalExaminationType.dto.RequestMedicalExaminationTypeDTO;
import esposito.medicalCenter.medicalExaminationType.dto.ResponseMedicalExaminationTypeDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalExaminationTypeService {

    private final MedicalExaminationTypeRepository medicalExaminationTypeRepository;

    public MedicalExaminationTypeService(MedicalExaminationTypeRepository medicalExaminationTypeRepository) {
        this.medicalExaminationTypeRepository = medicalExaminationTypeRepository;
    }

    public void createStartMedicalExaminationTypes() {
        MedicalExaminationTypeEntity m1 = new MedicalExaminationTypeEntity();
        m1.setActive(Boolean.TRUE);
        m1.setName("RADIOGRAFIA");
        m1.setDescription("Visita radiografica");

        medicalExaminationTypeRepository.save(m1);
    }

    @Transactional
    public ResponseMedicalExaminationTypeDTO createMedicalExaminationType(RequestMedicalExaminationTypeDTO requestMedicalExaminationTypeDTO) {
        MedicalExaminationTypeEntity medicalExaminationTypeEntity = new MedicalExaminationTypeEntity();
        medicalExaminationTypeEntity.setName(requestMedicalExaminationTypeDTO.name());
        medicalExaminationTypeEntity.setDescription(requestMedicalExaminationTypeDTO.description());
        medicalExaminationTypeEntity =  medicalExaminationTypeRepository.save(medicalExaminationTypeEntity);

        return new ResponseMedicalExaminationTypeDTO(medicalExaminationTypeEntity);
    }
}
