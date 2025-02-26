package com.service;

import com.util.LoggerUtility;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/** RandomCountHandler 依據設定檔上限產生隨機數值，使產生活躍紀錄次數貼近隨機次數 */
@Service
public class RandomCountHandler {

  // 讀取設定檔: 最大上限值
  @Value("${contributor.random-count.max-count:0}")
  private int maxCount;

  // 讀取設定檔: 每次push間隔秒數
  @Value("${contributor.random-count.delay:15}")
  private int delaySeconds;

  private ContributeGenerator contributeGenerator;

  @Autowired
  public void ContributeGenerator(ContributeGenerator contributeGenerator) {
    this.contributeGenerator = contributeGenerator;
  }

  // 執行 隨機次數活躍產生器
  public void randomCountExecutor() {
    LoggerUtility.info("Start randomCountExecutor");

    int randomCount = getRandomCount();
    LoggerUtility.info("Random count: " + randomCount);

    for (int i = 0; i < randomCount; i++) {
      try {
        // 產生活躍紀錄後，延遲指定時間再執行
        contributeGenerator.contributeProcedure();
        Thread.sleep(this.delaySeconds * 1000);
      } catch (InterruptedException e) {
        // 若發生異常, 記錄錯誤並直接中斷迴圈
        LoggerUtility.info("InterruptedException: " + e.getMessage());
        Thread.currentThread().interrupt();
        break;
      }
    }

    LoggerUtility.info("End randomCountExecutor");
  }

  // 取得隨機值
  private int getRandomCount() {
    int resultCount = 0;
    Random rand = new Random();
    if (this.maxCount > 0) {
      resultCount = rand.nextInt(this.maxCount);
    }
    return resultCount;
  }
}
