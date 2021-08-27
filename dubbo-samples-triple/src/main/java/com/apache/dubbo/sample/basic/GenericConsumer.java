/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.apache.dubbo.sample.basic;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.service.GenericService;

public class GenericConsumer {
    private static GenericService generic;

    public static void main(String[] args) throws InterruptedException {
        ReferenceConfig<GenericService> ref = new ReferenceConfig<>();
        ref.setInterface("com.apache.dubbo.sample.basic.IGreeter2");
        ref.setCheck(false);
        ref.setTimeout(30000);
        ref.setProtocol("tri");
        ref.setGeneric("true");
        ref.setLazy(true);

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(new ApplicationConfig("demo-consumer"))
                .registry(new RegistryConfig("zookeeper://127.0.0.1:2181"))
                .reference(ref)
                .start();

        generic = ref.get();
        System.out.println("dubbo ref started");
        sayHelloUnary();
        sayHelloUnaryResponseVoid();
        sayHelloUnaryRequestVoid();
        sayHelloLong();
        sayHelloException();
        notFoundMethod();
    }

    public static void sayHelloUnaryRequestVoid() {
        System.out.println(generic.$invoke("sayHelloRequestVoid", new String[0], new Object[0]));
    }

    public static void sayHelloUnaryResponseVoid() {
        System.out.println(generic.$invoke("sayHelloResponseVoid", new String[]{String.class.getName()},
                new Object[]{"requestVoid"}));
    }

    public static void sayHelloUnary() {
        System.out.println(generic.$invoke("sayHello", new String[]{String.class.getName()}, new Object[]{"unary"}));
    }

    public static void sayHelloException() {
        try {
            generic.$invoke("sayHelloException", new String[]{String.class.getName()}, new Object[]{"exception"});
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static void notFoundMethod() {
        try {
            generic.$invoke("sayHelloNotExist", new String[]{String.class.getName()}, new Object[]{"unary long"});
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static void sayHelloLong() {
        final Object resp = generic.$invoke("sayHelloLong", new String[]{String.class.getName()}, new Object[]{"unary long"});
        System.out.println(((String) resp).length());
    }
}
