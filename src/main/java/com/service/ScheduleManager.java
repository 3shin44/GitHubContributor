package com.service;

import com.util.LoggerUtility;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleManager {
  private ContributeGenerator contributeGenerator;

  @Autowired
  public void ContributeGenerator(ContributeGenerator contributeGenerator) {
    this.contributeGenerator = contributeGenerator;
  }

  // 使用 Spring 提供的 Cron 動態排程
  @Scheduled(cron = "${contributor.scheduler.cron}")
  public void executeTask() {
    LoggerUtility.info(
        "Scheduled task executed at "
            + java.time.LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    contributeGenerator.ContributeProcedure();
  }
}
