FROM openjdk:8-jdk-alpine

ADD target/dubbo-samples-docker-1.0-SNAPSHOT.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT exec java $JAVA_OPTS -jar /app.jar
