package esposito.medicalCenter.report.service;

import esposito.medicalCenter.report.dto.ReportSummaryResponse;
import esposito.medicalCenter.report.entity.ReportEntity;
import esposito.medicalCenter.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    public List<ReportSummaryResponse> getReportListByAppointmentId(Long appointmentId){
        return reportRepository.findByAppointmentId(appointmentId).stream().map(
                ReportSummaryResponse::new
        ).toList();
    }

    @Transactional
    public ReportSummaryResponse createReportWithFile(Long appointmentId, MultipartFile file) {

        // OPZIONALE MA CONSIGLIATO NEL MODULAR MONOLITH:
        // if (!appointmentFacade.existsById(appointmentId)) {
        //     throw new RuntimeException("Appuntamento non trovato!");
        // }

        try {
            // 1. Genero nome file univoco e salvo su disco (come prima)
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".")) : ".pdf";
            String uniqueFilename = UUID.randomUUID().toString() + extension;

            Path directoryPath = Paths.get("./File");
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }
            Path filePath = directoryPath.resolve(uniqueFilename);
            Files.copy(file.getInputStream(), filePath);

            // 2. CREO LA NUOVA ENTITÀ
            ReportEntity newReport = new ReportEntity();
            newReport.setAppointmentId();
            newReport.setReportPath(filePath.toString());
            newReport.setOriginalFilename(originalFilename); // Utile da salvare nel DB per mostrare il vero nome all'operatore
            // newReport.setUploadDate(LocalDateTime.now());

            // 3. Salvo nel Database
            ReportEntity savedReport = reportRepository.save(newReport);

            // 4. Mappo l'entità salvata in un DTO di risposta e lo restituisco
            return new ReportSummaryResponse(
                    savedReport.getId(),
                    savedReport.getOriginalFilename(),
                    savedReport.getAppointmentId()
            );

        } catch (IOException e) {
            throw new RuntimeException("Errore durante il salvataggio del file", e);
        }
    }

}
