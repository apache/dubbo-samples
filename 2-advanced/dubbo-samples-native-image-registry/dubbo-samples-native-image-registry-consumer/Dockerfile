# Using Oracle GraalVM for JDK 17 Community Edition
FROM ghcr.io/graalvm/native-image-community:17-ol8 AS builder

# Set the working directory to /home/app
WORKDIR /build

# Copy the source code into the image for building
COPY . /build

ARG APP_FILE

# Build
RUN ./mvnw clean package --no-transfer-progress native:compile -Pnative

# The deployment Image
FROM container-registry.oracle.com/os/oraclelinux:9-slim

EXPOSE 9091

# Copy the native executable into the containers
COPY --from=builder /build/target/dubbo-samples-native-image-registry-consumer app
ENTRYPOINT ["/app"]