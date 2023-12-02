/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.dubbo.samples.provider;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.dubbo.config.MetadataReportConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.samples.api.GreetService;

import java.io.IOException;

public class ProviderApplication {

    public static void main(String[] args) throws IOException {
        prepareNacosServer();
        System.setProperty("dubbo.application.qos-enable","false");
        ServiceConfig<GreetService> serviceConfig = new ServiceConfig<>();
        serviceConfig.setRef(new org.apache.dubbo.samples.provider.GreetServiceImpl());
        serviceConfig.setInterface(GreetService.class);
        RegistryConfig registryConfig = new RegistryConfig(GreetService.NACOS_ADDR);
        registryConfig.setRegisterMode("instance");

        DubboBootstrap bootstrap = DubboBootstrap.getInstance()
                .application("GreetProviderApplication")
                .registry(registryConfig)
                .metadataReport(new MetadataReportConfig(GreetService.NACOS_NAMESPACE_1_PATH))
                .metadataReport(new MetadataReportConfig(GreetService.NACOS_NAMESPACE_2_PATH))
                .protocol(new ProtocolConfig("dubbo", GreetService.PORT))
                .service(serviceConfig);
        bootstrap.start().await();
    }

    private static void prepareNacosServer() throws IOException{
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, buildRequestContent(GreetService.NACOS_NAMESPACE_NAME_1));
        Request request = new Request.Builder()
                .url(GreetService.NACOS_NAMESPACE_CONSOLE_PATH)
                .method("POST", body)
                .build();
        RequestBody body2 = RequestBody.create(mediaType, buildRequestContent(GreetService.NACOS_NAMESPACE_NAME_2));
        Request request2 = new Request.Builder()
                .url(GreetService.NACOS_NAMESPACE_CONSOLE_PATH)
                .method("POST", body2)
                .build();
        try (
                Response response = client.newCall(request).execute();
                Response response2 = client.newCall(request2).execute();
        ) {
            if (!response.isSuccessful() && !response2.isSuccessful()) {
                throw new IOException("Can not access nacos server.");
            }
        }
    }

    private static String buildRequestContent(String namespaceName){
        return "customNamespaceId="+namespaceName + "&namespaceName="+namespaceName+"&namespaceDesc="+namespaceName+"&namespaceID=";
    }

}
