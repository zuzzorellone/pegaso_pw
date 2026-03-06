package esposito.medicalCenter.report.dto;

import esposito.medicalCenter.report.entity.ReportEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReportSummaryResponse {

    private Long id;

    public ReportSummaryResponse(ReportEntity entity) {
        id = entity.getId();
    }
}
