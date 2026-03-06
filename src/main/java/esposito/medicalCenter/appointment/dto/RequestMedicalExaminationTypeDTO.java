package esposito.medicalCenter.appointment.dto;

import jakarta.validation.constraints.NotNull;


public record RequestMedicalExaminationTypeDTO(

        @NotNull(message = "Mandatory to insert the name")
        String name,

        String description,

        Boolean active
) {
}
