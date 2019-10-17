package ttc.ifish.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ttc.ifish.models.entities.fishgame.CardCashOut;
import ttc.ifish.models.entities.fishgame.CardRecharge;
import ttc.ifish.models.entities.report.ReportUserCardRecharge;
import ttc.ifish.repositories.fishgame.CardCashOutRepositoryImpl;
import ttc.ifish.repositories.fishgame.CardRechargeRepositoryImpl;
import ttc.ifish.service.ReportUserCardRechargeService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class ReportCard {
  @Value(value = "${startDate}")
  public String START_TIMES;

  @Value(value = "${times}")
  public int TIMES;
  @Autowired
  private CardRechargeRepositoryImpl cardRechargeRepository;
  @Autowired
  private ReportUserCardRechargeService reportUserCardRechargeService;
  @Autowired
  CardCashOutRepositoryImpl cardCashOutRepository;

  public void syntheticCard() {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = null;
    try {
      date = simpleDateFormat.parse(START_TIMES);
    }
    catch (ParseException e) {
      e.printStackTrace();
    }
    Calendar startTime = Calendar.getInstance();
    startTime.setTime(date);
    Calendar endTime = (Calendar) startTime.clone();
    endTime.add(Calendar.HOUR_OF_DAY, 1);
    storeReportCardRechargeAndCardCashOut(startTime, endTime);

  }

  private void storeReportCardRechargeAndCardCashOut(Calendar startTime, Calendar endTime) {
    do {
      reportCardRechargePerHour(startTime, endTime);
      reportCardCashOutPerHour(startTime, endTime);
      startTime.add(Calendar.HOUR_OF_DAY, 1);
      endTime.add(Calendar.HOUR_OF_DAY, 1);
    } while (endTime.before(Calendar.getInstance()));
  }

  private void reportCardRechargePerHour(Calendar startTime, Calendar endTime) {
    List<CardRecharge> cardRechargeList = cardRechargeRepository.getCardRecharge(startTime, endTime);
    for (CardRecharge cardRecharge : cardRechargeList) {
      Long totalAmount = cardRechargeRepository.sumValueCardRecharge(cardRecharge.getUserID(), startTime, endTime);
      ReportUserCardRecharge report = reportUserCardRechargeService.findByDateAndUserId(startTime,
        cardRecharge.getUserID());
      boolean recordExisted = report != null;
      if (recordExisted) {
        report.setValueCardRecharge(totalAmount);
        reportUserCardRechargeService.save(report);
      }
      else {
        ReportUserCardRecharge reportUserCardRecharge = new ReportUserCardRecharge();
        reportUserCardRecharge.setValueCardRecharge(totalAmount);
        reportUserCardRecharge.setDate(startTime);
        reportUserCardRecharge.setUserId(cardRecharge.getUserID());
        reportUserCardRecharge.setValueCardCashOut(0L);
        reportUserCardRechargeService.save(reportUserCardRecharge);
      }

    }
  }

  private void reportCardCashOutPerHour(Calendar startTime, Calendar endTime) {
    List<CardCashOut> cardCashOutList = cardCashOutRepository.getCardCashOut(startTime, endTime);
    for (CardCashOut cardCashOut : cardCashOutList) {
      Long totalAmountCashOut = cardCashOutRepository.getTotalAmount(cardCashOut.getUserID(), startTime, endTime);
      ReportUserCardRecharge reportUserCardRecharge = reportUserCardRechargeService.findByDateAndUserId(startTime,
        cardCashOut.getUserID());
      boolean recordNotExisted = reportUserCardRecharge == null;
      if (recordNotExisted) {
        ReportUserCardRecharge report = new ReportUserCardRecharge();
        report.setValueCardCashOut(totalAmountCashOut);
        report.setDate(startTime);
        report.setUserId(cardCashOut.getUserID());
        report.setValueCardRecharge(0L);
        reportUserCardRechargeService.save(report);
      }
      else {
        reportUserCardRecharge.setValueCardCashOut(totalAmountCashOut);
        reportUserCardRechargeService.save(reportUserCardRecharge);
      }
    }
  }
}
