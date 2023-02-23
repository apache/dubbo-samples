FROM openjdk:8u342-jdk


ADD ./target/*.jar /app/app.jar

# 暴露对应的端口
EXPOSE 20880
EXPOSE 18081

ENTRYPOINT exec java -jar /app/app.jar