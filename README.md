# GitHub Contributor 

## 說明

使用Spring Boot 3 配合排程功能，自動產生GitHub活躍紀錄

排程啟動時，至本地端GitHub Repo讀取指定檔案，在文字檔內容產生UUID造成差異

寫入檔案後推送至GitHub產生活躍紀錄

## 安裝啟動

### 環境
- JAVA 17+
- Windows/Linux

### GitHub
1. 建立GitHub Repo
2. Clone至本地端
3. 建立GitHub PAT (Personal Access Token), 並允許PUSH權限

### JAR
1. 將JAR & external.properties 放置相同目錄下
2. 開啟external.properties設定以下參數
```
# 排程啟動
contributor.scheduler.cron=*/5 * * * * *
# GitHub Repo本地端路徑
contributor.folder.path=path_to_folder
# 產生異動用的檔案, 例: GitHubContributor\Contributor.md
contributor.file.path=path_to_text_file
# GitHub Repo, 例: https://github.com/{USERNAME}/{REPO}.git
contributor.git.repo=your_git_repo
# GitHub PAT (Personal Access Token)
contributor.git.pat=your_git_personal_access_token
```
3. 執行JAR
`path\java -jar github-contributor.jar`

## 改進方案
- 導入容器化方式便於跨平台佈署
- GitHub Action 產生JAR檔
- 移除非必要web函示庫, 或內建簡易查詢介面

## 來源

![image](./imgFolder/rhys.png)