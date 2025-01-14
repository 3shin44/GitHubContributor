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

  @Value("${contributor.folder.path}")
  private String folderPath;

  @Value("${contributor.file.path}")
  private String filePath;

  @Value("${contributor.git.repo}")
  private String gitRepo;

  @Value("${contributor.git.pat}")
  private String gitPAT;

  // 總控制流程
  public void ContributeProcedure() {
    try {
      LoggerUtility.info("Start ContributeProcedure");

      // 更新文檔內容
      String resultUUID = updateContent();

      // 更新GITHUB REPO
      executeGitCommands(resultUUID);

      LoggerUtility.info("ContributeProcedure finished");
    } catch (Exception e) {
      LoggerUtility.info("Error ContributeProcedure: " + e);
    }
  }

  // 更新檔案
  private String updateContent() {
    String resultUUID = "";
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
      resultUUID = UUID.randomUUID().toString();

      // 4. 寫入檔案
      Files.write(
          path,
          resultUUID.getBytes(),
          StandardOpenOption.WRITE,
          StandardOpenOption.TRUNCATE_EXISTING);

      // 5. log確認訊息
      LoggerUtility.info("Generated UUID: " + resultUUID);
      LoggerUtility.info("UUID written to file: " + filePath);

    } catch (Exception e) {
      LoggerUtility.info("Error updateContent: " + e);
    }
    return resultUUID;
  }

  // Git 更新指令
  private void executeGitCommands(String uuid) throws IOException, InterruptedException {
    LoggerUtility.info("executeGitCommands:" + uuid);

    // 檢查資料夾是否存在
    File directory = new File(folderPath);
    if (!directory.exists() || !directory.isDirectory()) {
      throw new IllegalArgumentException("指定的資料夾不存在或無效: " + folderPath);
    }

    ProcessBuilder builder = new ProcessBuilder();
    builder.directory(directory); // 設定執行目錄為指定的資料夾

    // 執行 `git add .`
    runCommand(builder, "git add .");

    // 執行 `git commit`
    runCommand(builder, "git commit -m \"" + uuid + "\"");

    // 執行 `git push`  (組合成完整的 push 指令)
    String repoBaseUrl = gitRepo.replace("https://", "");
    String gitPushCommand = String.format("git push https://%s@%s", gitPAT, repoBaseUrl);
    runCommand(builder, gitPushCommand);
  }

  private void runCommand(ProcessBuilder builder, String command)
      throws IOException, InterruptedException {

    // 在 Windows 系統使用 cmd /c 執行命令
    boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");
    if (isWindows) {
      builder.command("cmd", "/c", command);
    } else {
      // 非 Windows 系統使用 sh -c
      builder.command("sh", "-c", command);
    }

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
}
