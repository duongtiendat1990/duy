package ttc.ifish.models.entities.fishgame;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;

@Entity
@Table(name = "fishcardrecharge")
public class CardRecharge {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "TransactionID")
  private Integer TransactionID;

  @NotNull
  @Column(name = "UserID")
  private Integer UserID;

  @NotNull
  @Column(name = "Operator")
  private String Operator;

  @NotNull
  @Column(name = "Serial")
  private String Serial;

  @NotNull
  @Column(name = "TopupCode")
  private String TopupCode;

  @NotNull
  @Column(name = "ClaimedValue")
  private Integer ClaimedValue;

  @NotNull
  @Column(name = "RealValue")
  private Integer RealValue;

  @NotNull
  @Column(name = "Status")
  private int Status;
  @Column(name = "isUpdate")
  private Boolean isUpdated;
  @NotNull
  @Column(name = "RequestedTime")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private Calendar RequestedTime;
  @Column(name = "ResponsedTime")
  private Calendar ResponsedTime;


  public CardRecharge() {
  }


  public Integer getClaimedValue() {
    return ClaimedValue;
  }

  public void setClaimedValue(Integer claimedValue) {
    ClaimedValue = claimedValue;
  }

  public int getStatus() {
    return Status;
  }

  public void setStatus(int status) {
    Status = status;
  }

  public Integer getTransactionID() {
    return TransactionID;
  }

  public void setTransactionID(Integer transactionID) {
    this.TransactionID = transactionID;
  }

  public Integer getUserID() {
    return UserID;
  }

  public void setUserID(Integer userID) {
    this.UserID = userID;
  }

  public String getOperator() {
    return Operator;
  }

  public void setOperator(String operator) {
    this.Operator = operator;
  }

  public String getSerial() {
    return Serial;
  }

  public void setSerial(String serial) {
    this.Serial = serial;
  }

  public String getTopupCode() {
    return TopupCode;
  }

  public void setTopupCode(String topupCode) {
    this.TopupCode = topupCode;
  }

  public Integer getRealValue() {
    return RealValue;
  }

  public void setRealValue(Integer realValue) {
    RealValue = realValue;
  }

  public Calendar getRequestedTime() {
    return RequestedTime;
  }

  public void setRequestedTime(Calendar requestedTime) {
    RequestedTime = requestedTime;
  }

  public Calendar getResponsedTime() {
    return ResponsedTime;
  }

  public void setResponsedTime(Calendar responsedTime) {
    ResponsedTime = responsedTime;
  }
}
