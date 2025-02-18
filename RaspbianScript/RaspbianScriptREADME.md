# Raspbian Script

## 說明

提供Raspbian環境快速啟動/關閉容器

啟動語法包含時區設定，避免內建時間未校正使排程非預期運作

## 變數說明

CONTAINER_NAME: 容器名稱，兩者設定相同，便於script辨識

### start_container.sh

PROJECT_DIR: 專案資料夾目錄位置

### stop_container.sh

## docker-compose版本

改由docker compose管理容器，容器異常時管理較方便

### start_compose.sh, stop_compose.sh

${APP_PATH}: 設定主機掛載路徑 (ex: /home/pi/) 或改由.env檔案設定
