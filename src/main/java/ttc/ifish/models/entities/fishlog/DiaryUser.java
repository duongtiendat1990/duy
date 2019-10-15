package ttc.ifish.models.entities.fishlog;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;


@Entity
@Table(name = "fishroleonlinelog")
public class DiaryUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "UserID")
  private Integer UserID;
  @NotNull
  @Column(name = "IsOnline")
  private int IsOnline;
  @Column(name = "GlobelSum")
  private Integer GlobelSum;
  @Column(name = "MadelSum")
  private Integer MadelSum;
  @Column(name = "CurrceySum")
  private Integer CurrceySum;
  @Column(name = "MacAddress")
  private String MacAddress;
  @Column(name = "IpAddress")
  private String IpAddress;
  @NotNull

  @Column(name = "LogTime")
//  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private Calendar LogTime;


  public DiaryUser() {
  }

  ;

  public Integer getUserID() {
    return UserID;
  }

  public void setUserID(Integer userID) {
    UserID = userID;
  }

  public int getIsOnline() {
    return IsOnline;
  }

  public void setIsOnline(int isOnline) {
    IsOnline = isOnline;
  }

  public Calendar getLogTime() {
    return LogTime;
  }

  public void setLogTime(Calendar logTime) {
    LogTime = logTime;
  }

  public Integer getGlobelSum() {
    return GlobelSum;
  }

  public void setGlobelSum(Integer globelSum) {
    GlobelSum = globelSum;
  }

  public Integer getMadelSum() {
    return MadelSum;
  }

  public void setMadelSum(Integer madelSum) {
    MadelSum = madelSum;
  }

  public Integer getCurrceySum() {
    return CurrceySum;
  }

  public void setCurrceySum(Integer currceySum) {
    CurrceySum = currceySum;
  }

  public String getMacAddress() {
    return MacAddress;
  }

  public void setMacAddress(String macAddress) {
    MacAddress = macAddress;
  }

  public String getIpAddress() {
    return IpAddress;
  }

  public void setIpAddress(String ipAddress) {
    IpAddress = ipAddress;
  }
}
