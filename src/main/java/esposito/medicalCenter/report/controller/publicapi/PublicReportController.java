package esposito.medicalCenter.report.controller.publicapi;

import esposito.medicalCenter.report.entity.ReportEntity;
import esposito.medicalCenter.report.service.PublicReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/public/reports")
public class PublicReportController {

    private final PublicReportService publicReportService;

    @GetMapping("/download")
    public ResponseEntity<?> downloadReport(@RequestParam("token") String token,
                                            @RequestParam("taxId") String taxIdentificationNumber) {
        ReportDownloadDTO report = publicReportService.validateTokenAndGetReport(token, taxIdentificationNumber);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + report.originalFilename() + "\"")
                .body(report.file());
    }
}
