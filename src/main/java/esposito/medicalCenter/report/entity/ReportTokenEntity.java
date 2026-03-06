package esposito.medicalCenter.report.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "reports_tokens")
public class ReportTokenEntity {
    @Id
    private Long id;
}
