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

package org.apache.dubbo.samples.basic;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import io.grpc.examples.helloworld.DubboGreeterGrpc;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/dubbo-demo-consumer.xml", "classpath*:spring/dubbo-demo-provider.xml"})
public class OriginalGrpcIT {
    //
//    static {
//        try {
//            GenericContainer zookeeper = new GenericContainer<>("zookeeper:3.4.9")
//                    .withExposedPorts(2181).waitFor();
//
//            System.setProperty("zookeeper.address", zookeeper.getContainerIpAddress());
//            System.setProperty("zookeeper.port", zookeeper.getFirstMappedPort() + "");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
    // port mapping, https://github.com/testcontainers/testcontainers-java/issues/256
    @ClassRule
    public static GenericContainer zookeeper = new GenericContainer<>(DockerImageName.parse("zookeeper:3.4.9"))
            .withCreateContainerCmdModifier(
                    cmd ->{
                        cmd.withPortBindings(new PortBinding(Ports.Binding.bindPort(2181), new ExposedPort(2181)));
                    }
            );

    @Autowired
    @Qualifier("greeter")
    private DubboGreeterGrpc.IGreeter greeter;

    @Test
    public void testGreeting() throws Exception {
        HelloReply reply = greeter.sayHello((HelloRequest.newBuilder().setName("world!").build()));
        Assert.assertTrue(reply.getMessage().startsWith("Hello world!"));
    }
}

