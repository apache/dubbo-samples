FROM openjdk:17-jdk-alpine
COPY ./target/*.jar /appruntime/app.jar
WORKDIR /appruntime
ENTRYPOINT ["java","-jar","/appruntime/app.jar"]