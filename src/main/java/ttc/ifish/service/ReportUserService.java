package ttc.ifish.service;

import ttc.ifish.models.entities.report.ReportUserConFormHour;

public interface ReportUserService {
  void save(ReportUserConFormHour reportUser);
  ReportUserConFormHour findByHourAndDate(String date, Integer hour);
}
