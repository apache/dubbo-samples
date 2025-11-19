# Build native image
The following steps work for all platforms - MacOS, Windows, and Linux.

To do this, we've provided a [multistage Docker build file](./Dockerfile) for building a Docker image containing your native executable inside a Docker container.

## Build the provider

1. Containerise the native executable using the following command:

    ```shell
    docker build -f ./Dockerfile --build-arg APP_FILE=dubbo-samples-native-image-provider -t provider-native:1.0.0 .
    ```

2. Run the application:

    ```shell
    docker run --rm --name native -p 50052:50052 provider-native:1.0.0
    ```

3. Open a new terminal window, call the endpoint using `curl`:

    ```shell
    curl \
        --header "Content-Type: application/json" \
        --data '{"name":"Dubbo"}' \
        http://localhost:50052/org.apache.dubbo.nativeimage.DemoService/sayHello/
    ```

    It should generate a random nonsense verse in the style of the poem Jabberwocky by Lewis Carrol.

4. To stop the application, first get the container id using `docker ps`, and then run:

    ```shell
    docker rm -f <container_id>
    ```
