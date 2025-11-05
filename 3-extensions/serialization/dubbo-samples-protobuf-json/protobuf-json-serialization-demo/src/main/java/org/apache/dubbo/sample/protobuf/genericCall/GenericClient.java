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
package org.apache.dubbo.sample.protobuf.genericCall;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.metadata.definition.model.FullServiceDefinition;
import org.apache.dubbo.metadata.definition.model.MethodDefinition;
import org.apache.dubbo.metadata.definition.model.TypeDefinition;
import org.apache.dubbo.rpc.service.GenericService;
import org.apache.dubbo.sample.protobuf.GoogleProtobufService;
import org.apache.dubbo.sample.protobuf.utils.ZkUtil;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class GenericClient {
    private static String zookeeperHost = System.getProperty("zookeeper.address", "127.0.0.1");

    public static void main(String[] args) throws Exception {
        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        ApplicationConfig applicationConfig = new ApplicationConfig("protobuf-demo-consumer");
        applicationConfig.setQosEnable(false);
        reference.setApplication(applicationConfig);

        reference.setInterface("org.apache.dubbo.sample.protobuf.GoogleProtobufService");
        reference.setGeneric(CommonConstants.GENERIC_SERIALIZATION_PROTOBUF);
        reference.setRegistry(new RegistryConfig("zookeeper://" + zookeeperHost + ":2181"));
        GenericService genericService = reference.get();

        printServiceData();

        Map<String, Object> request = new HashMap<>();
        request.put("string", "message from client");
        String requestString = new Gson().toJson(request);
        Object result = genericService.$invoke("callGoogleProtobuf", new String[]{
                        "org.apache.dubbo.sample.protobuf.GoogleProtobufBasic$GooglePBRequestType"},
                new Object[]{requestString});
        System.out.println(new Gson().toJson(result));
    }

    private static void printServiceData() throws Exception {
        Thread.sleep(3000);
        System.out.println("*********************************************************");
        System.out.println("service metadata:");
        String serviceMetaData = ZkUtil.getMetadata("/dubbo", GoogleProtobufService.class.getName(), CommonConstants
                        .PROVIDER_SIDE,
                "protobuf-demo");
        FullServiceDefinition serviceDefinition = new Gson().fromJson(serviceMetaData, FullServiceDefinition.class);
        System.out.println("service interface: " + GoogleProtobufService.class.getName());
        for (MethodDefinition methodDefinition : serviceDefinition.getMethods()) {
            System.out.println("method name: " + methodDefinition.getName());
            for (String parameterType : methodDefinition.getParameterTypes()) {
                TypeDefinition typeDefinition = serviceDefinition.getTypes().stream().filter(TD -> TD.getType().equals
                        (parameterType)).findAny().get();
                System.out.println("parameter: " + new Gson().toJson(typeDefinition));
            }
        }
        System.out.println();
        System.out.println("*********************************************************");
    }
}
