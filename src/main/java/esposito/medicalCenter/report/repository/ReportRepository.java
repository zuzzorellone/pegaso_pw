package esposito.medicalCenter.report.repository;

import esposito.medicalCenter.report.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ReportRepository extends JpaRepository<ReportEntity, Long> {

    @Query("SELECT r FROM ReportEntity r WHERE r.appointment.id = :id")
    Set<ReportEntity> findReportEntitiesByAppointmentId(@Param("id") Long id);

    Set<ReportEntity> findByAppointmentId(Long appointmentId);
}
