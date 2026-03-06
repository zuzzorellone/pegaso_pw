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
            @PathVariable("id") Long reportId,
            @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        reportService.saveReportFile(reportId, file);

        return ResponseEntity.ok().build();
    }
}
