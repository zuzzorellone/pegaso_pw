package esposito.medicalCenter.medicalExaminationType.dto;

import esposito.medicalCenter.medicalExaminationType.MedicalExaminationTypeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ResponseMedicalExaminationTypeDTO {

    private Long id;
    private String name;
    private String description;
    private Boolean isActive = Boolean.TRUE;

    public ResponseMedicalExaminationTypeDTO(MedicalExaminationTypeEntity medicalExaminationTypeEntity) {
        setId(medicalExaminationTypeEntity.getId());
        setName(medicalExaminationTypeEntity.getName());
        setDescription(medicalExaminationTypeEntity.getDescription());
    }

}
