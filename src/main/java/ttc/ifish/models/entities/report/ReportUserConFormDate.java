package ttc.ifish.models.entities.report;

import javax.persistence.*;

@Entity
@Table(name = "report_user_date")
public class ReportUserConFormDate {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer reportID;
  @Column(name = "date")
  private String date;
  @Column(name = "countUser")
  private Integer countUser;


  public ReportUserConFormDate(){};
  public Integer getReportID() {
    return reportID;
  }

  public void setReportID(Integer reportID) {
    this.reportID = reportID;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public Integer getCountUser() {
    return countUser;
  }

  public void setCountUser(Integer countUser) {
    this.countUser = countUser;
  }
}
