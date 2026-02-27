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

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String telephoneNumber;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<AppointmentEntity> appointments;
}
