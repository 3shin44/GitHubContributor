# GitHub Contributor 

## 說明

使用Spring Boot 3 配合排程功能，自動產生GitHub活躍紀錄

排程啟動時，至本地端GitHub Repo讀取指定檔案，在文字檔內容產生UUID造成差異

寫入檔案後推送至GitHub產生活躍紀錄

## 安裝啟動

### 環境
- JAVA 17+
- Docker
- Windows/Linux

### GitHub
1. 建立GitHub Repo
2. 建立GitHub PAT (Personal Access Token), 並允許PUSH權限

### Build Container
1. 替換使用者名稱與EMAIL，容器名稱預設: "ubuntu-java17-git"
`docker build --build-arg GIT_USER="GitUserName" --build-arg GIT_EMAIL="GitEmail@example.com" -t ubuntu-java17-git .`
備註: 此處須與GitHub Repository Email相同或相關才會納入計算
2. (optional) 匯出
`docker save -o ubuntu-java17-git ubuntu-java17-git`
3. (optional) 佈署環境匯入
`docker load -i ubuntu-java17-git`

建議於原生環境build IMAGE，避免底層架構問題

### JAR & Clone Git Repository
1. 將JAR & external.properties 放置相同目錄下 (例: D:/Folder or /home/user/Folder)
2. 啟動IMAGE並掛載至CONTAINER內
`docker run -it --rm -v D:/Folder:/app ubuntu-java17-git`
`docker run -it --rm -v /home/user/Folder:/app ubuntu-java17-git`
3. 進入容器後執行GIT CLONE
`git clone {GitHub Repo}`
4. 開啟external.properties設定以下參數
```
# 排程啟動 (範例: 每天AM0900, 1000執行)
contributor.scheduler.cron=0 0 9,10 * * ?
# GitHub Repo本地端路徑 (指向容器內路徑, 例: /app/GitHubContributor)
contributor.folder.path=path_to_folder
# 產生異動用的檔案, 例: /app/GitHubContributor/Contributor.md
contributor.file.path=path_to_text_file
# GitHub Repo, 例: https://github.com/{USERNAME}/{REPO}.git
contributor.git.repo=your_git_repo
# GitHub PAT (Personal Access Token)
contributor.git.pat=your_git_personal_access_token
# 不執行機率 (0-100, 未設置時預設為0)
contributor.skip.percentage=30
```
5. 執行JAR
`java -jar github-contributor.jar`

## 改進方案
- 導入容器化方式便於跨平台佈署 --> 建置完成, 於Windows開發, 佈署在Linux
- GitHub Action 產生JAR檔
- 移除非必要web函式庫, 或內建簡易查詢介面 --> 移除非必要web函式庫

## 想法來源

![image](./imgFolder/rhys.png)