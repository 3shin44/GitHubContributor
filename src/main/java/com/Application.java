package com;

import java.io.File;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Application {

  public static void main(String[] args) {

    String getApplicationVersion = getApplicationVersion();
    System.out.println("Running Application Version: " + getApplicationVersion);

    SpringApplication application = new SpringApplication(Application.class);

    // 設定外部配置文件的位置
    String externalConfigPath = "./external.properties";
    File externalConfigFile = new File(externalConfigPath);

    try {
      if (externalConfigFile.exists() && externalConfigFile.isFile()) {
        System.setProperty(
            "spring.config.additional-location", "file:" + externalConfigFile.getAbsolutePath());
        System.out.println(
            "External configuration file loaded: " + externalConfigFile.getAbsolutePath());
      } else {
        System.out.println(
            "External configuration file does not exist or is invalid, using internal configuration.");
      }
    } catch (Exception e) {
      System.err.println(
          "Error occurred while checking the external configuration file: " + e.getMessage());
      System.out.println("Continuing with internal configuration.");
    }

    application.run(args);
  }

  // 讀取 META-INF/MANIFEST.MF 版本號
  private static String getApplicationVersion() {
    try {
      InputStream manifestStream =
          Application.class.getClassLoader().getResourceAsStream("META-INF/MANIFEST.MF");

      if (manifestStream != null) {
        Manifest manifest = new Manifest(manifestStream);
        Attributes attr = manifest.getMainAttributes();
        return attr.getValue("Implementation-Version");
      }
    } catch (Exception ignored) {
    }

    return "Unknown";
  }
}
