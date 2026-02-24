package esposito.medicalCenter.medicalExaminationType;

import esposito.medicalCenter.appointment.AppointmentEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "medicalExaminationTypes")
public class MedicalExaminationTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @Column(name = "is_active", nullable = false)
    private Boolean active = true;

    @OneToMany(mappedBy = "medicalExaminationType")
    private List<AppointmentEntity> appointments;
}
