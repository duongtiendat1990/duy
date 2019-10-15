package ttc.ifish.repositories.report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ttc.ifish.models.entities.report.ReportUserConFormHour;

@Repository
public interface ReportUserRepository extends JpaRepository<ReportUserConFormHour,Integer> {
  ReportUserConFormHour findByHourAndDate(Integer hour, String date);
  ReportUserConFormHour findByDate(String date);
}
