package esposito.medicalCenter.medicalExaminationType.dto;

import lombok.Getter;
import lombok.Setter;


public record RequestMedicalExaminationTypeDTO(

        String name,
        String description,
        Boolean active
) {
}
