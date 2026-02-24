package esposito.medicalCenter.appointment;

import esposito.medicalCenter.medicalExaminationType.MedicalExaminationTypeEntity;
import esposito.medicalCenter.patient.PatientEntity;
import esposito.medicalCenter.report.ReportEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "appointments")
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime appointmentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AppointmentStatus appointmentStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "medical_examination_type_id", nullable = false)
    private MedicalExaminationTypeEntity medicalExaminationType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientEntity patient;

    @OneToMany(mappedBy = "appointment",  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportEntity> reportList;

}
