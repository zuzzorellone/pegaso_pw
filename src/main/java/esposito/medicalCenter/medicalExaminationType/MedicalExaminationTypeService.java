package esposito.medicalCenter.medicalExaminationType;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalExaminationTypeService {

    private final MedicalExaminationTypeRepository medicalExaminationTypeRepository;

    public MedicalExaminationTypeService(MedicalExaminationTypeRepository medicalExaminationTypeRepository) {
        this.medicalExaminationTypeRepository = medicalExaminationTypeRepository;

        createStartMedicalExaminationTypes();
    }

    public void createStartMedicalExaminationTypes() {
        MedicalExaminationTypeEntity m1 = new MedicalExaminationTypeEntity();
        m1.setActive(Boolean.TRUE);
        m1.setName("RADIOGRAFIA");
        m1.setDescription("Visita radiografica");

        medicalExaminationTypeRepository.save(m1);
    }
}
