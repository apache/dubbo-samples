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

package org.apache.dubbo.sample.protobuf.consumer;


import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.sample.protobuf.GoogleProtobufBasic;
import org.apache.dubbo.sample.protobuf.GoogleProtobufService;

public class ConsumerStarter {
    private static String zookeeperHost = System.getProperty("zookeeper.address", "127.0.0.1");

    public static void main(String[] args) {
        ReferenceConfig<GoogleProtobufService> reference = new ReferenceConfig<>();
        ApplicationConfig applicationConfig = new ApplicationConfig("first-dubbo-consumer");
        applicationConfig.setQosEnable(false);
        reference.setApplication(applicationConfig);
        reference.setRegistry(new RegistryConfig("zookeeper://" + zookeeperHost + ":2181"));
        reference.setInterface(GoogleProtobufService.class);
        GoogleProtobufService service = reference.get();
        GoogleProtobufBasic.GooglePBRequestType requestType = GoogleProtobufBasic.GooglePBRequestType.newBuilder()
                .setString("some string from client")
                .build();
        GoogleProtobufBasic.GooglePBResponseType responseType = service.callGoogleProtobuf(requestType);
        System.out.println(responseType.getString());
    }
}
