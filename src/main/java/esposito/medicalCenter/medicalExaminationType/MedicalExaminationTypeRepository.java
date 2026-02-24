package esposito.medicalCenter.medicalExaminationType;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicalExaminationTypeRepository extends JpaRepository<MedicalExaminationTypeEntity, Long> {

    Optional<MedicalExaminationTypeEntity> findMedicalExaminationTypeEntityByName(String name);


}
