package ttc.ifish.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ttc.ifish.models.entities.report.ReportUserConFormHour;
import ttc.ifish.repositories.report.ReportUserRepository;
import ttc.ifish.service.ReportUserService;

@Service
public class ReportUserServiceImpl implements ReportUserService {
  @Autowired
  private ReportUserRepository reportUserRepository;
  @Override
  public void save(ReportUserConFormHour reportUser) {
    reportUserRepository.save(reportUser);
  }

  @Override
  public ReportUserConFormHour findByHourAndDate(String date, Integer hour) {
    return reportUserRepository.findByHourAndDate(hour,date);
  }
}
