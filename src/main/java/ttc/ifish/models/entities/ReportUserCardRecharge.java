package ttc.ifish.models.entities;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "report_user_card_recharge")
public class ReportUserCardRecharge {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer reportId;
  private Calendar date;
  private Integer userId;
  private Long valueCardRecharge;
  private Long valueCardCashOut;

  public ReportUserCardRecharge(){};

  public Long getValueCardCashOut() {
    return valueCardCashOut;
  }

  public void setValueCardCashOut(Long valueCardCashOut) {
    this.valueCardCashOut = valueCardCashOut;
  }

  public Integer getReportId() {
    return reportId;
  }

  public void setReportId(Integer reportId) {
    this.reportId = reportId;
  }

  public Calendar getDate() {
    return date;
  }

  public void setDate(Calendar date) {
    this.date = date;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }



  public Long getValueCardRecharge() {
    return valueCardRecharge;
  }

  public void setValueCardRecharge(Long valueCardRecharge) {
    this.valueCardRecharge = valueCardRecharge;
  }


}
