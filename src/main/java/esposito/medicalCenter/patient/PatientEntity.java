package esposito.medicalCenter.patient;

import esposito.medicalCenter.appointment.AppointmentEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "patients")
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String telephoneNumber;
    private String name;
    private String surname;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<AppointmentEntity> appointments;
}
