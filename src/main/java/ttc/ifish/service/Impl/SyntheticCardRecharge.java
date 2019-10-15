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
public class SyntheticCardRecharge {
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
    int count = 0;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Calendar timeNow = Calendar.getInstance();
    Date date = null;
    try {
      date = simpleDateFormat.parse(START_TIMES);
    }
    catch (ParseException e) {
      e.printStackTrace();
    }
    Calendar startTime = Calendar.getInstance();
    startTime.setTime(date);

    while (timeNow.after(startTime)) {
      Calendar startTimes = Calendar.getInstance();
      startTimes.setTime(date);
      count++;
      startTimes.add(Calendar.DATE, count - 1);
      Calendar now = Calendar.getInstance();
     now.setTime(date);
     now.add(Calendar.DATE,count-2);

      System.out.println("year " + now.get(Calendar.YEAR));
      System.out.println("month " + now.get(Calendar.MONTH));
      System.out.println("day " + now.get(Calendar.DAY_OF_MONTH));
      System.out.println("hour " + now.get(Calendar.HOUR));
      if (!now.after(timeNow)){
        break;
      }
      for (int i = 0; i <= 23; i++) {
        Calendar startHour = Calendar.getInstance();
        Calendar endHour = Calendar.getInstance();
        startHour.setTime(startTimes.getTime());
        endHour.setTime(startTimes.getTime());
        startHour.add(Calendar.HOUR, i);
        endHour.add(Calendar.HOUR, i + 1);
        now.add(Calendar.HOUR,i+1);
        List<CardRecharge> cardRechargeList = cardRechargeRepository.getCardRecharge(startHour, endHour);
        Iterator<CardRecharge> cardRechargeIterator = cardRechargeList.iterator();
//        System.out.println("year " + startHour.get(Calendar.YEAR));
//        System.out.println("month " + startHour.get(Calendar.MONTH));
//        System.out.println("day " + startHour.get(Calendar.DAY_OF_MONTH));
//        System.out.println("hour " + startHour.get(Calendar.HOUR));
        while (cardRechargeIterator.hasNext()) {
          CardRecharge cardRecharge = cardRechargeIterator.next();
          Long totalAmount = cardRechargeRepository.sumValueCardRecharge(cardRecharge.getUserID(), startHour, endHour);
          ReportUserCardRecharge reportUserCardRecharge = new ReportUserCardRecharge();
          System.out.println("time " + startHour.getTime() + "  /totalCardRechar " + totalAmount);
          reportUserCardRecharge.setValueCardRecharge(totalAmount);
          reportUserCardRecharge.setDate(startHour);
          reportUserCardRecharge.setUserId(cardRecharge.getUserID());
          reportUserCardRecharge.setValueCardCashOut(0l);
          ReportUserCardRecharge recharge = reportUserCardRechargeService.findByDateAndUserId(startHour,
            cardRecharge.getUserID());
          if (recharge != null) {
            recharge.setValueCardRecharge(totalAmount);
            reportUserCardRechargeService.save(recharge);

          }
          else {
            reportUserCardRechargeService.save(reportUserCardRecharge);
          }

        }
        List<CardCashOut> cardCashOutList = cardCashOutRepository.getCardCashOut(startHour, endHour);
        Iterator<CardCashOut> cashOutIterator = cardCashOutList.iterator();
        while (cashOutIterator.hasNext()) {
          System.out.println("tatal cashout ");
          CardCashOut cardCashOut = cashOutIterator.next();
          Long totalCardCashOut = cardCashOutRepository.getTotalAmount(cardCashOut.getUserID(), startHour, endHour);
          ReportUserCardRecharge recharge = new ReportUserCardRecharge();
          recharge.setValueCardCashOut(totalCardCashOut);
          System.out.println("times " + startHour.getTime() + " /total cashout " + totalCardCashOut);
          recharge.setDate(startHour);
          recharge.setUserId(cardCashOut.getUserID());
          recharge.setValueCardRecharge(0l);
          ReportUserCardRecharge reportUserCardRecharge = reportUserCardRechargeService.findByDateAndUserId(startHour,
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

  }
}
