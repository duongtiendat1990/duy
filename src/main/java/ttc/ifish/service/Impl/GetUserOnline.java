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
public class GetUserOnline {
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
    int count = 0;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
    Calendar timeNow = Calendar.getInstance();
    Date date1 = null;
    try {
      date1 = sdf.parse(START_DATE);
    }
    catch (ParseException e) {
      e.printStackTrace();
    }
    Calendar a1 = Calendar.getInstance();
    a1.setTime(date1);
    try {
      while (timeNow.after(a1)) {
        count++;
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy_M_d");
        Date date = sdf.parse(START_DATE);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, count - 1);
        Date date2 = cal.getTime();
        String ac = sdf1.format(date2);
        String er = sdf1.format(timeNow.getTime());
       List<DiaryUser>  reportUserConFormDate = diaryUserRepository.findAllUserOnlineInDate(ac);
       ReportUserConFormDate userConFormDate = new ReportUserConFormDate();
       userConFormDate.setCountUser(reportUserConFormDate.size());
       userConFormDate.setDate(ac);
       reportUserConFormDateReponsitory.save(userConFormDate);
        ReportUserConFormHour reportUser3 = reportUserRepository.findByHourAndDate(23, er);
        if (reportUser3 != null) {
          break;
        }
        for (int j = 0; j <= 23; j++) {
          Calendar startHour = Calendar.getInstance();
          startHour.setTime(cal.getTime());
          Calendar endHour = Calendar.getInstance();
          endHour.setTime(cal.getTime());
          startHour.add(Calendar.HOUR, j);
          endHour.add(Calendar.HOUR, j + 1);
          List<DiaryUser> diaryUsers = diaryUserRepository.findAllUserOnlineAtTheSameHour(ac, startHour, endHour);
          ReportUserConFormHour reportUser = new ReportUserConFormHour();
          reportUser.setCountUser(diaryUsers.size());
          reportUser.setHour(startHour.getTime().getHours());
          reportUser.setDate(ac);
          ReportUserConFormHour reportUser1 = reportUserRepository.findByHourAndDate(startHour.getTime().getHours(), ac);
          if (reportUser1 != null) {
            reportUser1.setCountUser(diaryUsers.size());
            reportUserRepository.save(reportUser1);
          }
          else {
            reportUserRepository.save(reportUser);
          }
        }
      }
    }
    catch (ParseException e) {
      e.printStackTrace();
    }
  }
}





