/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.scenario.builder;

import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import org.asynchttpclient.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class JacocoDownloader {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * The jacoco agent version, set to 0.8.13 for supporting JDK 23 and 24 class files
     */
    private static final String JACOCO_VERSION = "0.8.13";

    /**
     * The jacoco binary file name
     */
    private static final String JACOCO_BINARY_FILE_NAME = "org.jacoco.agent-" + JACOCO_VERSION + "-runtime.jar";

    /**
     * The url format for jacoco binary file.
     */
    private static final String JACOCO_BINARY_URL_FORMAT = "https://repo1.maven.org/maven2/org/jacoco/org.jacoco.agent/" + JACOCO_VERSION + "/org.jacoco.agent-" + JACOCO_VERSION + "-runtime.jar";

    /**
     * The temporary directory.
     */
    private static final String TEMPORARY_DIRECTORY = "jacoco";

    /**
     * The timeout when download jacoco binary archive file.
     */
    private static final int REQUEST_TIMEOUT = 30 * 1000;

    /**
     * The timeout when connect the download url.
     */
    private static final int CONNECT_TIMEOUT = 10 * 1000;

    /**
     * Returns {@code true} if the file exists with the given file path, otherwise {@code false}.
     *
     * @param filePath the file path to check.
     */
    private static boolean checkFile(Path filePath) {
        return Files.exists(filePath) && filePath.toFile().isFile();
    }

    public static void initialize(IConfiguration configuration) {
        if (!configuration.enableJacoco()) {
            LOGGER.info("Disabled JacocoAgent");
            return;
        }

        Path expectedFile = new File(configuration.outputDir() + File.separator + "jacocoagent.jar").toPath();
        // checks the jacoco binary file exists or not
        if (checkFile(expectedFile)) {
            LOGGER.info("Existed JacocoAgent: " + expectedFile);
            return;
        }
        Path temporaryFilePath;
        try {
            temporaryFilePath = Paths.get(Files.createTempDirectory("").getParent().toString(),
                    TEMPORARY_DIRECTORY,
                    JACOCO_BINARY_FILE_NAME);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Cannot create the temporary directory, file path: %s", TEMPORARY_DIRECTORY), e);
        }

        // create the temporary directory path.
        try {
            Files.createDirectories(temporaryFilePath.getParent());
        } catch (IOException e) {
            throw new RuntimeException(String.format("Failed to create the temporary directory to save jacoco binary file, file path:%s", temporaryFilePath.getParent()), e);
        }

        // download jacoco binary file in temporary directory.
        try {
            LOGGER.info("It is beginning to download the jacoco binary archive, it will take several minutes..." +
                    "\nThe jacoco binary archive file will be download from " + JACOCO_BINARY_URL_FORMAT + "," +
                    "\nwhich will be saved in " + temporaryFilePath.toString() + "," +
                    "\nalso it will be renamed to '" + JACOCO_BINARY_FILE_NAME + "' and moved into " + expectedFile + ".\n");
            download(temporaryFilePath);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Download jacoco binary archive failed, download url:%s, file path:%s." +
                            "\nOr you can do something to avoid this problem as below:" +
                            "\n1. Download jacoco binary archive manually regardless of the version" +
                            "\n2. Rename the downloaded file named 'org.jacoco.agent-{version}-runtime.jar' to 'jacocoagent.jar'" +
                            "\n3. Put the renamed file in %s, you maybe need to create the directory if necessary.\n",
                    JACOCO_BINARY_URL_FORMAT, temporaryFilePath, expectedFile), e);
        }

        // check downloaded jacoco binary file in temporary directory.
        if (!checkFile(temporaryFilePath)) {
            throw new IllegalArgumentException(String.format("There are some unknown problem occurred when downloaded the jacoco binary archive file, file path:%s", temporaryFilePath));
        }

        // create target directory if necessary
        if (!Files.exists(expectedFile)) {
            try {
                Files.createDirectories(expectedFile.getParent());
            } catch (IOException e) {
                throw new IllegalArgumentException(String.format("Failed to create target directory, the directory path: %s", expectedFile.getParent()), e);
            }
        }

        // copy the downloaded jacoco binary file into the target file path
        try {
            Files.copy(temporaryFilePath, expectedFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("Failed to copy file, the source file path: %s, the target file path: %s", temporaryFilePath, expectedFile), e);
        }

        // checks the jacoco binary file exists or not again
        if (!checkFile(expectedFile)) {
            throw new IllegalArgumentException(String.format("The jacoco binary archive file doesn't exist, file path:%s", expectedFile));
        }

        LOGGER.info("Downloaded JacocoAgent: " + expectedFile);
    }

    /**
     * Download the file with the given url.
     *
     * @param targetPath the target path to save the downloaded file.
     */
    private static void download(Path targetPath) throws ExecutionException, InterruptedException, IOException, TimeoutException {
        AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient(
                new DefaultAsyncHttpClientConfig.Builder()
                        .setConnectTimeout(CONNECT_TIMEOUT)
                        .setRequestTimeout(REQUEST_TIMEOUT)
                        .setMaxRequestRetry(1)
                        .build());
        Future<Response> responseFuture = asyncHttpClient.prepareGet(JacocoDownloader.JACOCO_BINARY_URL_FORMAT).execute(new AsyncCompletionHandler<Response>() {
            @Override
            public Response onCompleted(Response response) {
                LOGGER.info("Download jacoco binary archive file successfully! download url: " + JacocoDownloader.JACOCO_BINARY_URL_FORMAT);
                return response;
            }

            @Override
            public void onThrowable(Throwable t) {
                LOGGER.warn("Failed to download the file, download url: " + JacocoDownloader.JACOCO_BINARY_URL_FORMAT);
                super.onThrowable(t);
            }
        });
        // Future timeout should 2 times as equal as REQUEST_TIMEOUT, because it will retry 1 time.
        Response response = responseFuture.get(REQUEST_TIMEOUT * 2, TimeUnit.MILLISECONDS);
        Files.copy(response.getResponseBodyAsStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
    }

}
