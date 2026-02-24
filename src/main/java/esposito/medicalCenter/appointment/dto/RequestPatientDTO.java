package esposito.medicalCenter.appointment.dto;

import jakarta.validation.constraints.NotNull;

public record RequestPatientDTO(

        @NotNull(message = "The patient name can't be null")
        String name,

        @NotNull(message = "The patient surname can't be null")
        String surname,

        @NotNull(message = "The patient email can't be null")
        String email,

        @NotNull(message = "The patient telephone number can't be null")
        String telephoneNumber
) {
}
