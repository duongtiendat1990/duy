package ttc.ifish.repositories.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ttc.ifish.models.entities.report.ReportUserCardRecharge;

import java.util.Calendar;


@Repository
public interface ReportUserCardRechargeRepository extends JpaRepository<ReportUserCardRecharge,Integer> {
  ReportUserCardRecharge findByDateAndUserId(Calendar date,Integer userId);
  ReportUserCardRecharge findByDate(Calendar date);
}

