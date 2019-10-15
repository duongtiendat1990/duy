package ttc.ifish.repositories.report;

import org.springframework.data.jpa.repository.JpaRepository;
import ttc.ifish.models.entities.report.ReportUserConFormDate;


public interface ReportUserConFormDateReponsitory extends JpaRepository<ReportUserConFormDate,Integer> {
  ReportUserConFormDate findByDate(String date);
}
