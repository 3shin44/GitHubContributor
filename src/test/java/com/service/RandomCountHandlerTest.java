package com.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = "contributor.randomCount.max-count=10")
@TestPropertySource(properties = "contributor.random-count.delay=3")
@TestPropertySource(properties = "scheduler.enabled=true")
public class RandomCountHandlerTest {
  @Autowired private RandomCountHandler randomCountHandler;

  @Test
  void testRandomCountExecutor() {
    randomCountHandler.randomCountExecutor();
  }
}
