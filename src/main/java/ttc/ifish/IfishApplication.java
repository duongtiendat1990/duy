package ttc.ifish;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ttc.ifish.service.Impl.ReportUserOnline;
import ttc.ifish.service.Impl.ReportCard;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class IfishApplication {

  public static void main(String[] args) {
    ApplicationContext app = SpringApplication.run(IfishApplication.class, args);
    ReportUserOnline reportUserOnline = (ReportUserOnline) app.getBean(ReportUserOnline.class);
    ReportCard reportCard = (ReportCard) app.getBean(ReportCard.class);
    ScheduledExecutorService executor =
      Executors.newSingleThreadScheduledExecutor();
    Runnable periodicTask = new Runnable() {
      public void run() {
        reportCard.syntheticCard();
        reportUserOnline.generalDiary();
      }
    };
    executor.scheduleAtFixedRate(periodicTask, 0, reportCard.TIMES, TimeUnit.SECONDS);
  }
}


