# This is the demo Dockerfile for the generated template project, please change accordingly before building image from it.
# Run the following command to build image: docker build -f ./Dockerfile --build-arg APP_FILE=quickstart-service--0.0.1-SNAPSHOT.jar -t demo:latest .
FROM openjdk:17-jdk-alpine

# Set the working directory to /build
WORKDIR /build
COPY . /build

RUN chmod +x ../mvnw
RUN ./mvnw clean package -U -DskipTests

# JAR file will be specified by passing in a build time argument to docker build
ARG APP_FILE

# Remember to change the port according to the RPC protocol you select
EXPOSE 8087

# copy the JAR file into the root and rename
RUN cp ./target/${APP_FILE} app.jar

# Run java with the jar file when the container starts up
CMD ["java","-jar","app.jar"]
