# Using Oracle GraalVM for JDK 17 Community Edition
FROM container-registry.oracle.com/graalvm/native-image-community:17-ol8 AS builder

# Set the working directory to /home/app
WORKDIR /build

# Copy the source code into the image for building
COPY . /build

# Build
RUN ./mvnw clean package --no-transfer-progress native:compile -Pnative -U
RUN ls -l /build/target

# The deployment Image
FROM container-registry.oracle.com/os/oraclelinux:9-slim

EXPOSE 9091

# The `APP_FILE` variable used in `COPY` instruction should be set at the same build stage (`FROM` ...) of the `COPY` instruction
ARG APP_FILE
RUN echo "Set ARG APP_FILE=${APP_FILE} at the same build stage ('FROM ...') of COPY instruction"

# Copy the native executable into the containers (source file name could not contain variables)
COPY --from=builder /build/target/${APP_FILE} app
ENTRYPOINT ["/app"]
