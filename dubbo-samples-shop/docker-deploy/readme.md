# Docker 部署 Dubbo Samples Shop

## 1. 准备工作

进入 `docker-deploy` 目录下，执行 `build.sh` 脚本。

```bash
cd dubbo-samples-shop
mvn clean package
```

> 此脚本会打包 dubbo 和前端项目，并且将打包后的文件拷贝到 `docker-deploy` 目录下。

## 2. 启动项目

`docker compose up -d` 您也可以执行 `docker compose up` 来观测启动日志。
