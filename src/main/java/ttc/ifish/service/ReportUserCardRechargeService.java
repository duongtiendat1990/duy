package ttc.ifish.service;

import ttc.ifish.models.entities.report.ReportUserCardRecharge;

import java.util.Calendar;

public interface ReportUserCardRechargeService {

  void save(ReportUserCardRecharge reportUserCardRecharge);

  ReportUserCardRecharge findById(Integer id);

  //  ReportUserCardRecharge findByDateAndOperatorAndValueAndUserId(Calendar date,String operator,Integer value,Integer userID);
  ReportUserCardRecharge findByDateAndUserId(Calendar date,Integer userId);

  ReportUserCardRecharge findByDate(Calendar date);

}
