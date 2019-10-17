package ttc.ifish.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ttc.ifish.models.entities.fishlog.DiaryUser;
import ttc.ifish.models.entities.report.ReportUserConFormDate;
import ttc.ifish.models.entities.report.ReportUserConFormHour;
import ttc.ifish.repositories.fishlog.DiaryUserRepositoryImpl;
import ttc.ifish.repositories.report.ReportUserConFormDateReponsitory;
import ttc.ifish.repositories.report.ReportUserRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ReportUserOnline {
  @Value(value = "${times}")
  public int TIMES;
  @Value(value = "${startDateReportUser}")
  private String START_DATE;
  @Autowired
  DiaryUserRepositoryImpl diaryUserRepository;
  @Autowired
  ReportUserRepository reportUserRepository;
  @Autowired
  ReportUserConFormDateReponsitory reportUserConFormDateReponsitory;

  public void generalDiary() {
    SimpleDateFormat getTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat tableNameDateFormat = new SimpleDateFormat("yyyy_M_d");
    try {
      Date initiateTime = getTimeFormat.parse(START_DATE);
      Calendar startTime = Calendar.getInstance();
      startTime.setTime(initiateTime);
      Calendar endTime = (Calendar) startTime.clone();
      endTime.add(Calendar.HOUR_OF_DAY, 1);
      do {
        String tableNameSuffix = tableNameDateFormat.format(startTime.getTime());
        reportUserFormDate(tableNameSuffix);
        reportUserFormHour(startTime, endTime, tableNameSuffix);
        startTime.add(Calendar.HOUR_OF_DAY, 1);
        endTime.add(Calendar.HOUR_OF_DAY, 1);
      } while (endTime.before(Calendar.getInstance()));
    }
    catch (ParseException e) {
      e.printStackTrace();
    }
  }

  private void reportUserFormDate(String dateTime) {
    List<DiaryUser> reportUserConFormDate = diaryUserRepository.findAllUserOnlineInDate(dateTime);
    ReportUserConFormDate userConFormDate = new ReportUserConFormDate();
    userConFormDate.setCountUser(reportUserConFormDate.size());
    userConFormDate.setDate(dateTime);
    reportUserConFormDateReponsitory.save(userConFormDate);
  }

  private void reportUserFormHour(Calendar startTime, Calendar endTime, String tableNameSuffix) {
    List<DiaryUser> diaryUsers = diaryUserRepository.findAllUserOnlineAtTheSameHour(tableNameSuffix, startTime, endTime);
    ReportUserConFormHour reportUser = new ReportUserConFormHour();
    reportUser.setCountUser(diaryUsers.size());
    int hour = startTime.get(Calendar.HOUR_OF_DAY);
    reportUser.setHour(hour);
    reportUser.setDate(tableNameSuffix);
    ReportUserConFormHour reportUser1 = reportUserRepository.findByHourAndDate(hour, tableNameSuffix);
    if (reportUser1 != null) {
      reportUser1.setCountUser(diaryUsers.size());
      reportUserRepository.save(reportUser1);
    }
    else {
      reportUserRepository.save(reportUser);
    }
  }
}





