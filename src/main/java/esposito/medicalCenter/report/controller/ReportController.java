package esposito.medicalCenter.report.controller;

import esposito.medicalCenter.report.service.ReportService;
import esposito.medicalCenter.report.dto.ReportSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reports")
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    public ResponseEntity<List<ReportSummaryResponse>> getReportByID(@RequestParam(name = "appointmentId") Long appointmentId) {
        return ResponseEntity.ok(reportService.getReportListByAppointmentId(appointmentId));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ReportSummaryResponse> createReport(
            @RequestParam("appointmentId") Long appointmentId,
            @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        ReportSummaryResponse createdReport = reportService.createReportWithFile(appointmentId, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReport);
    }

    @PostMapping(value = "/{id}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadReportFile(
            @PathVariable("id") Long appointmentId,
            @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        reportService.createReportWithFile(appointmentId, file);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{reportId}/generate-token")
    public ResponseEntity<Map<String, String>> generateToken(@PathVariable Long reportId) {

        String token = reportService.generateAndSaveTokenForReport(reportId);

        String downloadLink = "http://localhost:8080/api/v1/public/reports/download?token=" + token;

        return ResponseEntity.ok(Map.of(
                "token", token,
                "downloadLink", downloadLink,
                "message", "Token generato con successo. Scadrà tra 30 giorni."
        ));
    }
}
