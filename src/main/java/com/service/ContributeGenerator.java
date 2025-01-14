package com.service;

import com.util.LoggerUtility;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ContributeGenerator {

  @Value("${file.path}")
  private String filePath;

  @Value("${git.username}")
  private String gitUsername;

  @Value("${git.password}")
  private String gitPassword;

  // 總控制流程
  public void ContributeProcedure() {
    try {
      LoggerUtility.info("Start ContributeProcedure");
      updateContent();
    } catch (Exception e) {
      LoggerUtility.info("Error ContributeProcedure: " + e);
    }
  }

  // 更新檔案
  private void updateContent() {
    try {
      LoggerUtility.info("Start updateContent");

      // 1. 確保檔案存在（如果不存在則創建）
      Path path = Paths.get(filePath);
      if (!Files.exists(path)) {
        Files.createFile(path);
        LoggerUtility.info("File created at path: " + filePath);
      }

      // 2. 讀取檔案的第一行
      String firstLine = Files.lines(path).findFirst().orElse("No content in file");
      LoggerUtility.info("Read first line: " + firstLine);

      // 3. 產生 UUID
      String uuid = UUID.randomUUID().toString();

      // 4. 寫入檔案
      Files.write(
          path, uuid.getBytes(), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

      // 5. log確認訊息
      LoggerUtility.info("Generated UUID: " + uuid);
      LoggerUtility.info("UUID written to file: " + filePath);

    } catch (Exception e) {
      LoggerUtility.info("Error updateContent: " + e);
    }
  }

  // Git 更新指令
  private void executeGitCommands(String uuid) throws IOException, InterruptedException {
    ProcessBuilder builder = new ProcessBuilder();
    builder.directory(new File("."));

    // 執行 `git add .`
    runCommand(builder, "git add .");

    // 執行 `git commit`
    runCommand(builder, "git commit -m \"" + uuid + "\"");

    // 執行 `git push`
    String gitPushCommand =
        String.format(
            "git -c http.extraHeader=\"Authorization: Basic %s\" push",
            encodeCredentials(gitUsername, gitPassword));
    runCommand(builder, gitPushCommand);
  }

  private void runCommand(ProcessBuilder builder, String command)
      throws IOException, InterruptedException {
    builder.command("sh", "-c", command);
    Process process = builder.start();
    try (BufferedReader reader =
        new BufferedReader(new InputStreamReader(process.getInputStream()))) {
      String line;
      while ((line = reader.readLine()) != null) {
        System.out.println(line);
      }
    }
    int exitCode = process.waitFor();
    if (exitCode != 0) {
      throw new RuntimeException("Command failed with exit code: " + exitCode);
    }
  }

  private String encodeCredentials(String username, String password) {
    String credentials = username + ":" + password;
    return java.util.Base64.getEncoder().encodeToString(credentials.getBytes());
  }
}
