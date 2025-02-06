# 使用 Ubuntu 作為基底映像
FROM ubuntu:latest

# 設定環境參數，避免互動式安裝過程
ENV DEBIAN_FRONTEND=noninteractive

# 更新系統並安裝必要的工具
RUN apt-get update && apt-get install -y \
    curl \
    git \
    gnupg \
    && rm -rf /var/lib/apt/lists/*

# 設定 Git 使用者資訊 (定義 build arguments，允許外部傳入變數)
ARG GIT_USER
ARG GIT_EMAIL
RUN git config --global user.name "$GIT_USER" && \
    git config --global user.email "$GIT_EMAIL"

# Install Java 17
RUN apt-get update && apt-get install -y openjdk-17-jdk && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Set the working directory
WORKDIR /app

# 設定容器啟動時執行的命令
CMD ["bash"]
