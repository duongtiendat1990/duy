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
    String startDate = START_DATE.replace('-', '_');
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
    Date date = null;
    try {
      date = sdf.parse(startDate);
    }
    catch (ParseException e) {
      e.printStackTrace();
    }
    Calendar startTime = Calendar.getInstance();
    startTime.setTime(date);
    Calendar endTime = (Calendar) startTime.clone();
    endTime.add(Calendar.HOUR_OF_DAY, 1);
    try {
      while (endTime.after(Calendar.getInstance())) {
        startTime.add(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy_M_d");
        Date date1 = sdf.parse(START_DATE);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        cal.add(Calendar.DATE, 1);
        Date date2 = cal.getTime();
        String dateTime = sdf1.format(date2);
        reportUserFormDate(dateTime);
        for (int j = 0; j <= 23 && endTime.before(Calendar.getInstance()); j++, startTime.add(Calendar.HOUR_OF_DAY, 1))
          ;
        {
          endTime = (Calendar) startTime.clone();
          endTime.add(Calendar.HOUR_OF_DAY, 1);
          reportUserFormHour(startTime, endTime, dateTime);
        }
      }
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

  private void reportUserFormHour(Calendar startTime, Calendar endTime, String dateTime) {
    List<DiaryUser> diaryUsers = diaryUserRepository.findAllUserOnlineAtTheSameHour(dateTime, startTime, endTime);
    ReportUserConFormHour reportUser = new ReportUserConFormHour();
    reportUser.setCountUser(diaryUsers.size());
    reportUser.setHour(startTime.get(Calendar.HOUR_OF_DAY));
    reportUser.setDate(dateTime);
    ReportUserConFormHour reportUser1 = reportUserRepository.findByHourAndDate(
      startTime.get(Calendar.HOUR_OF_DAY),
      dateTime);
    if (reportUser1 != null) {
      reportUser1.setCountUser(diaryUsers.size());
      reportUserRepository.save(reportUser1);
    }
    else {
      reportUserRepository.save(reportUser);
    }
  }
}





