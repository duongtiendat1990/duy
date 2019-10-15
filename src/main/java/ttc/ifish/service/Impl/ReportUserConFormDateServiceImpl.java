package ttc.ifish.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ttc.ifish.models.entities.report.ReportUserConFormDate;
import ttc.ifish.repositories.report.ReportUserConFormDateReponsitory;
import ttc.ifish.service.ReportUserConFormDateService;

@Service
@Transactional(rollbackFor = Exception.class)
public class ReportUserConFormDateServiceImpl implements ReportUserConFormDateService {
  @Autowired
  private ReportUserConFormDateReponsitory reportUserConFormDateReponsitory;


  @Override
  public void save(ReportUserConFormDate reportUserConFormDate) {
reportUserConFormDateReponsitory.save(reportUserConFormDate);
  }

  @Override
  public ReportUserConFormDate findByDate(String date) {
    return reportUserConFormDateReponsitory.findByDate(date);
  }
}
