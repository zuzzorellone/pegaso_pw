package esposito.medicalCenter.report.service;

import esposito.medicalCenter.appointment.api.AppointmentFacade;
import esposito.medicalCenter.report.dto.ReportSummaryResponse;
import esposito.medicalCenter.report.entity.ReportEntity;
import esposito.medicalCenter.report.entity.ReportTokenEntity;
import esposito.medicalCenter.report.repository.ReportRepository;
import esposito.medicalCenter.report.repository.ReportTokenRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportService {

    @Value("${app.file.upload-dir}")
    private String uploadDir;

    private final ReportRepository reportRepository;
    private final ReportTokenRepository reportTokenRepository;
    private final AppointmentFacade appointmentFacade;

    public List<ReportSummaryResponse> getReportListByAppointmentId(Long appointmentId){
        List<ReportSummaryResponse> list = new LinkedList<>();

         reportRepository.findByAppointmentId(appointmentId).forEach(
                reportEntity -> {
                    ReportSummaryResponse reportSummaryResponse = new ReportSummaryResponse(
                            reportEntity.getId(),
                            reportEntity.getOriginalFilename(),
                            reportEntity.getAppointmentId()
                    );

                    list.add(reportSummaryResponse);
                }
        );

        return list;
    }

    @Transactional
    public ReportSummaryResponse createReportWithFile(Long appointmentId, MultipartFile file) {

        appointmentFacade.existsById(appointmentId);

        try {
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".")) : ".pdf";
            String uniqueFilename = UUID.randomUUID().toString() + extension;

            Path directoryPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }
            Path filePath = directoryPath.resolve(uniqueFilename);

            Files.copy(file.getInputStream(), filePath);

            ReportEntity newReport = new ReportEntity();
            newReport.setAppointmentId(appointmentId);
            newReport.setReportPath(filePath.toString());
            newReport.setOriginalFilename(originalFilename);
            ReportEntity savedReport = reportRepository.save(newReport);

            return new ReportSummaryResponse(
                    savedReport.getId(),
                    savedReport.getOriginalFilename(),
                    savedReport.getAppointmentId()
            );

        } catch (IOException e) {
            throw new RuntimeException("Saving File Error", e);
        }
    }

    @Transactional
    public String generateAndSaveTokenForReport(Long reportId) {
        ReportEntity report = reportRepository.findById(reportId)
                .orElseThrow(() -> new EntityNotFoundException("Report Not Found with ID: " + reportId));

        reportTokenRepository.findReportTokenEntityById(reportId)
                .ifPresent(reportTokenRepository::delete);

        ReportTokenEntity newToken = new ReportTokenEntity();
        newToken.setToken(UUID.randomUUID().toString());
        newToken.setExpirationDate(LocalDateTime.now().plusDays(15));
        newToken.setFailedAttempts(0);
        newToken.setReport(report);
        reportTokenRepository.save(newToken);

        return newToken.getToken();
    }

}
