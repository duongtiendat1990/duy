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
    reportCardRechargeAndCardCashOut(startTime, endTime);

  }

  private void reportCardRechargeAndCardCashOut(Calendar startTime, Calendar endTime) {
    while (endTime.before(Calendar.getInstance())) {
      startTime.add(Calendar.DATE, 1);
      for (int i = 0; i <= 23 && endTime.before(Calendar.getInstance()); i++, startTime.add(Calendar.HOUR_OF_DAY, 1)) {
        endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR_OF_DAY, 1);
        reportCardRecharge(startTime, endTime);
        reportCardCashOut(startTime, endTime);
      }
    }
  }

  private void reportCardRecharge(Calendar startTime, Calendar endTime) {
    List<CardRecharge> cardRechargeList = cardRechargeRepository.getCardRecharge(startTime, endTime);
    Iterator<CardRecharge> cardRechargeIterator = cardRechargeList.iterator();
    while (cardRechargeIterator.hasNext()) {
      CardRecharge cardRecharge = cardRechargeIterator.next();
      Long totalAmount = cardRechargeRepository.sumValueCardRecharge(cardRecharge.getUserID(), startTime, endTime);
      ReportUserCardRecharge reportUserCardRecharge = new ReportUserCardRecharge();
      reportUserCardRecharge.setValueCardRecharge(totalAmount);
      reportUserCardRecharge.setDate(startTime);
      reportUserCardRecharge.setUserId(cardRecharge.getUserID());
      reportUserCardRecharge.setValueCardCashOut(0l);
      ReportUserCardRecharge recharge = reportUserCardRechargeService.findByDateAndUserId(startTime,
        cardRecharge.getUserID());
      if (recharge != null) {
        recharge.setValueCardRecharge(totalAmount);
        reportUserCardRechargeService.save(recharge);

      }
      else {
        reportUserCardRechargeService.save(reportUserCardRecharge);
      }

    }
  }

  private void reportCardCashOut(Calendar startTime, Calendar endTime) {
    List<CardCashOut> cardCashOutList = cardCashOutRepository.getCardCashOut(startTime, endTime);
    Iterator<CardCashOut> cashOutIterator = cardCashOutList.iterator();
    while (cashOutIterator.hasNext()) {
      CardCashOut cardCashOut = cashOutIterator.next();
      Long totalCardCashOut = cardCashOutRepository.getTotalAmount(cardCashOut.getUserID(), startTime, endTime);
      ReportUserCardRecharge recharge = new ReportUserCardRecharge();
      recharge.setValueCardCashOut(totalCardCashOut);
      recharge.setDate(startTime);
      recharge.setUserId(cardCashOut.getUserID());
      recharge.setValueCardRecharge(0l);
      ReportUserCardRecharge reportUserCardRecharge = reportUserCardRechargeService.findByDateAndUserId(startTime,
        cardCashOut.getUserID());
      if (reportUserCardRecharge == null) {
        reportUserCardRechargeService.save(recharge);
      }
      else {
        reportUserCardRecharge.setValueCardCashOut(totalCardCashOut);
        reportUserCardRechargeService.save(reportUserCardRecharge);
      }
    }
  }
}
