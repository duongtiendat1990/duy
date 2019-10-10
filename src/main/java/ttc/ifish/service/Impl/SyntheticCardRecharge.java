package ttc.ifish.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ttc.ifish.models.entities.CardCashOut;
import ttc.ifish.models.entities.CardRecharge;
import ttc.ifish.models.entities.ReportUserCardRecharge;
import ttc.ifish.repositories.CardCashOutRepositoryImpl;
import ttc.ifish.repositories.CardRechargeRepositoryImpl;
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
  public String START_TIMES ;

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
      System.out.println("count  " + count);
      startTimes.add(Calendar.DATE, count - 1);


      for (int i = 0; i <= 23; i++) {
        Calendar startHour = Calendar.getInstance();
        Calendar endHour = Calendar.getInstance();
        startHour.setTime(startTimes.getTime());
        endHour.setTime(startTimes.getTime());
        startHour.add(Calendar.HOUR, i);
        endHour.add(Calendar.HOUR, i + 1);
        List<CardRecharge> cardRechargeList = cardRechargeRepository.getCardRecharge(startHour, endHour);
        Iterator<CardRecharge> cardRechargeIterator = cardRechargeList.iterator();
        System.out.println("date " + startHour.getTime());

        while (cardRechargeIterator.hasNext() ) {
          CardRecharge cardRecharge = cardRechargeIterator.next();
          Long totalAmount = cardRechargeRepository.sumValueCardRecharge(cardRecharge.getUserID(), startHour, endHour);
          ReportUserCardRecharge reportUserCardRecharge = new ReportUserCardRecharge();
          System.out.println("time " + startHour.getTime() + "  /totalCardRechar " + totalAmount);
          reportUserCardRecharge.setValueCardRecharge(totalAmount);
          reportUserCardRecharge.setDate(startHour);
          reportUserCardRecharge.setUserId(cardRecharge.getUserID());
          ReportUserCardRecharge recharge = reportUserCardRechargeService.findByDateAndUserId(startHour,cardRecharge.getUserID());
          if (recharge != null) {
            recharge.setValueCardRecharge(totalAmount);
            reportUserCardRechargeService.save(recharge);

          }
          else {
            reportUserCardRechargeService.save(reportUserCardRecharge);
          }

        }
        List<CardCashOut> cardCashOutList = cardCashOutRepository.getCardCashOut(startHour,endHour);
        Iterator<CardCashOut> cashOutIterator = cardCashOutList.iterator();
        while ( cashOutIterator.hasNext()){
          System.out.println("tatal cashout ");
          CardCashOut cardCashOut = cashOutIterator.next();
          Long totalCardCashOut = cardCashOutRepository.getTotalAmount(cardCashOut.getUserID(),startHour,endHour);
          ReportUserCardRecharge recharge = new ReportUserCardRecharge();
          recharge.setValueCardCashOut(totalCardCashOut);
          System.out.println(  "times "+startHour.getTime()+ " /total cashout " + totalCardCashOut );
          recharge.setDate(startHour);
          recharge.setUserId(cardCashOut.getUserID());
          ReportUserCardRecharge reportUserCardRecharge = reportUserCardRechargeService.findByDateAndUserId(startHour,cardCashOut.getUserID());
          if (reportUserCardRecharge == null){
            reportUserCardRechargeService.save(recharge);
          }else {
            reportUserCardRecharge.setValueCardCashOut(totalCardCashOut);
            reportUserCardRechargeService.save(reportUserCardRecharge);
          }
        }

      }
    }

  }
}
