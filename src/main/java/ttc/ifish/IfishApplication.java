package ttc.ifish;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ttc.ifish.service.Impl.GetUserOnline;
import ttc.ifish.service.Impl.SyntheticCardRecharge;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class IfishApplication {

  public static void main(String[] args) {
    ApplicationContext app = SpringApplication.run(IfishApplication.class, args);
    GetUserOnline getUserOnline = (GetUserOnline) app.getBean(GetUserOnline.class);
    SyntheticCardRecharge sc = (SyntheticCardRecharge) app.getBean(SyntheticCardRecharge.class);
    ScheduledExecutorService executor =
      Executors.newSingleThreadScheduledExecutor();
    Runnable periodicTask = new Runnable() {
      public void run() {
        sc.syntheticCard();
        getUserOnline.generalDiary();
      }
    };
    executor.scheduleAtFixedRate(periodicTask, 0, sc.TIMES, TimeUnit.SECONDS);
  }
}


