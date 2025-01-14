package com.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtility {

  private LoggerUtility() {
    // 私有建構子，避免被實例化, 多個實例沒有必要
    // 避免被 LoggerUtility logger1 = new LoggerUtility();
    //  LoggerUtility logger2 = new LoggerUtility();
  }

  /**
   * 獲取當前呼叫類別的 Logger 實例
   *
   * @return Logger 實例
   */
  private static Logger getLogger() {
    // 利用堆疊追蹤獲取呼叫此方法的類別名
    String className = Thread.currentThread().getStackTrace()[3].getClassName();
    return LoggerFactory.getLogger(className);
  }

  /**
   * INFO 級別的日誌記錄
   *
   * @param message 日誌訊息
   * @param args 格式化參數
   */
  public static void info(String message, Object... args) {
    Logger logger = getLogger();
    logger.info(message, args);
  }

  /**
   * ERROR 級別的日誌記錄
   *
   * @param message 日誌訊息
   * @param throwable 錯誤例外
   */
  public static void error(String message, Throwable throwable) {
    Logger logger = getLogger();
    logger.error(message, throwable);
  }

  /**
   * DEBUG 級別的日誌記錄
   *
   * @param message 日誌訊息
   * @param args 格式化參數
   */
  public static void debug(String message, Object... args) {
    Logger logger = getLogger();
    logger.debug(message, args);
  }

  /**
   * WARN 級別的日誌記錄
   *
   * @param message 日誌訊息
   * @param args 格式化參數
   */
  public static void warn(String message, Object... args) {
    Logger logger = getLogger();
    logger.warn(message, args);
  }
}
