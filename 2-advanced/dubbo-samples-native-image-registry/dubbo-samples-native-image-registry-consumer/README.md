# Build native image
The following steps work for all platforms - MacOS, Windows, and Linux.

To do this, we've provided a [multistage Docker build file](./Dockerfile) for building a Docker image containing your native executable inside a Docker container.

## Build the consumer

1. Containerise the native executable using the following command:

    ```shell
    docker build -f ./Dockerfile --build-arg APP_FILE=dubbo-samples-native-image-consumer -t consumer-native:1.0.0 .
    ```

2. Run the application:

    ```shell
    docker run --rm --name native-consumer consumer-native:1.0.0
    ```

3. To stop the application, first get the container id using `docker ps`, and then run:

    ```shell
    docker rm -f <container_id>
    ```
