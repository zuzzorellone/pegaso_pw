package esposito.medicalCenter.appointment.repository;

import esposito.medicalCenter.appointment.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {

    @Query("SELECT a FROM AppointmentEntity a WHERE a.appointmentDate > :now AND a.medicalExaminationType.name = :examinationType")
    List<AppointmentEntity> getAllAppointmentDateTimeList(@Param("examinationType") String examinationType,
                                                      @Param("now") LocalDateTime now);

    @Query("SELECT a FROM AppointmentEntity a " +
            "WHERE a.appointmentDate >= :start " +
            "AND a.appointmentDate <= :end " +
            "AND a.medicalExaminationType.name = :examinationTypeName")
    List<AppointmentEntity> findByAppointmentDateBetweenAndExaminationTypeName(
            LocalDateTime start,
            LocalDateTime end,
            String examinationTypeName
    );

}
