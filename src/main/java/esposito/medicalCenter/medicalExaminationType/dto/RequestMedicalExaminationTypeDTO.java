package esposito.medicalCenter.medicalExaminationType.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


public record RequestMedicalExaminationTypeDTO(

        @NotNull(message = "Mandatory to insert the name")
        String name,

        String description,

        Boolean active
) {
}
