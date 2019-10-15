package ttc.ifish.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ttc.ifish.models.entities.report.ReportUserCardRecharge;
import ttc.ifish.repositories.report.ReportUserCardRechargeRepository;
import ttc.ifish.service.ReportUserCardRechargeService;

import java.util.Calendar;

@Service
@Transactional
public class ReportUserCardRechargeServiceImpl implements ReportUserCardRechargeService {


  @Autowired
  private ReportUserCardRechargeRepository reportUserCardRechargeRepository;


  @Override
  public void save(ReportUserCardRecharge reportUserCardRecharge) {
    reportUserCardRechargeRepository.save(reportUserCardRecharge);
  }

  @Override
  public ReportUserCardRecharge findById(Integer id) {
    return reportUserCardRechargeRepository.findById(id).get();
  }

  @Override
  public ReportUserCardRecharge findByDateAndUserId(Calendar date, Integer userId) {
    return reportUserCardRechargeRepository.findByDateAndUserId(date, userId);
  }

  @Override
  public ReportUserCardRecharge findByDate(Calendar date) {
    return reportUserCardRechargeRepository.findByDate(date);
  }


}
