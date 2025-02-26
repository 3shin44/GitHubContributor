package com.schedule;

import com.service.RandomCountHandler;
import com.util.LoggerUtility;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(
    name = "contributor.random-count.scheduler.enabled",
    havingValue = "true",
    matchIfMissing = false)
public class ScheduleRandomCountHandler {

  private RandomCountHandler randomCountHandler;

  @Autowired
  public void RandomCountHandler(RandomCountHandler randomCountHandler) {
    this.randomCountHandler = randomCountHandler;
  }

  @Scheduled(cron = "${contributor.random-count.scheduler.cron}")
  public void executeTask() {
    LoggerUtility.info(
        "ScheduleRandomCountHandler executed at "
            + java.time.LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

    this.randomCountHandler.randomCountExecutor();

    LoggerUtility.info(
        "ScheduleRandomCountHandler finished at "
            + java.time.LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
  }
}
