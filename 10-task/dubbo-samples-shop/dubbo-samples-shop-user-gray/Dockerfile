#FROM maven:3.9.6-amazoncorretto-17 AS builder
#COPY . /appbuild
#WORKDIR /appbuild
#
#RUN mvn clean package -DskipTests -f pom.xml
FROM openjdk:17-jdk-alpine
COPY  ./target/*.jar /appruntime/app.jar
WORKDIR /appruntime
ENTRYPOINT ["java","-jar","/appruntime/app.jar"]