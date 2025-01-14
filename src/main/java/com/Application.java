package com;

import java.io.File;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Application {

  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(Application.class);

    // 設定外部配置文件的位置
    String externalConfigPath = "./external.properties";
    File externalConfigFile = new File(externalConfigPath);

    try {
      if (externalConfigFile.exists() && externalConfigFile.isFile()) {
        System.setProperty(
            "spring.config.additional-location", "file:" + externalConfigFile.getAbsolutePath());
        System.out.println("外部配置文件已加載: " + externalConfigFile.getAbsolutePath());
      } else {
        System.out.println("外部配置文件不存在或無效，使用內部配置。");
      }
    } catch (Exception e) {
      System.err.println("檢查外部配置文件時發生錯誤: " + e.getMessage());
      System.out.println("繼續使用內部配置。");
    }

    application.run(args);
  }
}
