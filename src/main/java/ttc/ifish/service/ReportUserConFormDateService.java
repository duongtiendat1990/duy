package ttc.ifish.service;


import ttc.ifish.models.entities.report.ReportUserConFormDate;

public interface ReportUserConFormDateService {
  void save(ReportUserConFormDate reportUserConFormDate);
  ReportUserConFormDate findByDate(String date);
}
