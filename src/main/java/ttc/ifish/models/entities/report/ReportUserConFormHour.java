package ttc.ifish.models.entities.report;

import javax.persistence.*;

@Entity
@Table(name = "report_user_hour")
public class ReportUserConFormHour {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer reportId;
  @Column(name = "date")
  private String date;
  @Column(name = "hour")
  private Integer hour;
  @Column(name = "countUser")
  private Integer countUser;

  public ReportUserConFormHour() {
  }
  public Integer getReportId() {
    return reportId;
  }

  public void setReportId(Integer reportId) {
    this.reportId = reportId;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public Integer getHour() {
    return hour;
  }

  public void setHour(Integer hour) {
    this.hour = hour;
  }

  public Integer getCountUser() {
    return countUser;
  }

  public void setCountUser(Integer countUser) {
    this.countUser = countUser;
  }
}
