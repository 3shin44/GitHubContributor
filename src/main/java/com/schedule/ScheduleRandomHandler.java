package com.schedule;

import com.service.ContributeGenerator;
import com.service.RandomHandler;
import com.util.LoggerUtility;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(
    name = "contributor.scheduler.enabled",
    havingValue = "true",
    matchIfMissing = false)
public class ScheduleRandomHandler {

  private ContributeGenerator contributeGenerator;

  @Autowired
  public void ContributeGenerator(ContributeGenerator contributeGenerator) {
    this.contributeGenerator = contributeGenerator;
  }

  private RandomHandler randomHandler;

  // 使用 Spring 提供的 Cron 動態排程
  @Scheduled(cron = "${contributor.scheduler.cron}")
  public void executeTask() {
    LoggerUtility.info(
        "ScheduleRandomHandler executed at "
            + java.time.LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    boolean needRunFlag = randomHandler.percentLottery();
    if (needRunFlag) {
      contributeGenerator.contributeProcedure();
    }

    LoggerUtility.info(
        "ScheduleRandomCountHandler finished at "
            + java.time.LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
  }
}
