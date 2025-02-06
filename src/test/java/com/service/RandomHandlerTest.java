package com.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RandomHandlerTest {

  private final RandomHandler randomHandler = new RandomHandler();

  @Test
  void testRun() {

    for (int i = 0; i < 10; i++) {
      boolean result = randomHandler.percentLottery();
      System.out.println("Test run: " + i + ", result: " + result);
    }
  }
}
