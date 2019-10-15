package ttc.ifish.models.entities.fishgame;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "fishcardcashout")
public class CardCashOut {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY )
  @Column(name = "TransactionID")
  private Integer transactionID;
  @Column(name  ="UserID")
  private Integer userID;
  @Column(name = "Operator")
  private String operator;
  @Column(name = "Serial")
  private String serial;
  @Column(name = "TopupCode")
  private String topupCode;
  @Column(name = "Value")
  private Integer value;
  @Column(name = "Status")
  private int status;
  @Column(name = "IsUpdated")
  private Boolean isUpdated;
  @Column(name = "RequestedTime")
  private Calendar requestedTime;
  @Column(name = "ResponsedTime")
  private  Calendar responsedTime;

  public CardCashOut(){};

  public Integer getUserID() {
    return userID;
  }

  public void setUserID(Integer userID) {
    this.userID = userID;
  }

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public String getSerial() {
    return serial;
  }

  public void setSerial(String serial) {
    this.serial = serial;
  }

  public String getTopupCode() {
    return topupCode;
  }

  public void setTopupCode(String topupCode) {
    this.topupCode = topupCode;
  }

  public Integer getValue() {
    return value;
  }

  public void setValue(Integer value) {
    this.value = value;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public Boolean getUpdated() {
    return isUpdated;
  }

  public void setUpdated(Boolean updated) {
    isUpdated = updated;
  }

  public Calendar getRequestedTime() {
    return requestedTime;
  }

  public void setRequestedTime(Calendar requestedTime) {
    this.requestedTime = requestedTime;
  }

  public Calendar getResponsedTime() {
    return responsedTime;
  }

  public void setResponsedTime(Calendar responsedTime) {
    this.responsedTime = responsedTime;
  }
}
