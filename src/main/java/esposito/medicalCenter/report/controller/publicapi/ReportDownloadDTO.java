package esposito.medicalCenter.report.controller.publicapi;

import org.springframework.core.io.Resource;

public record ReportDownloadDTO(
        Resource file,
        String originalFilename
) {
}
