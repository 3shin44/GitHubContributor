package com.controller;

import com.util.LoggerUtility;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionController {
  @Value("${project.name}")
  private String projectName;

  @Value("${project.version}")
  private String version;

  @GetMapping("/version")
  public String getVersion() {
    LoggerUtility.info("GET REQUEST: getVersion");
    return projectName + ": " + version;
  }
}
