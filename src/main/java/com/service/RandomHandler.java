package com.service;

import com.util.LoggerUtility;
import java.util.Random;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RandomHandler {

  @Value("${contributor.skip.percentage:0}")
  private int skipPercentage;

  public boolean percentLottery() {
    boolean result = false;
    // 隨機產生0到100之間的數值
    Random rand = new Random();
    int randomValue = rand.nextInt(100);

    LoggerUtility.info(
        "Start percentLottery: randomValue: {}, skipPercentage: {}", randomValue, skipPercentage);

    // 隨機數值大於指定百分比，回傳TRUE
    if (randomValue > skipPercentage) {
      result = true;
    }

    LoggerUtility.info("End percentLottery, flag: " + result);

    return result;
  }
}
