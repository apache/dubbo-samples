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

import java.io.IOException;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;

public class StreamWrapperConsumer {
    public static void main(String[] args) throws InterruptedException, IOException {
        ReferenceConfig<IStreamGreeter2> ref = new ReferenceConfig<>();
        ref.setInterface(IStreamGreeter2.class);
        ref.setCheck(false);
        ref.setProtocol(CommonConstants.TRIPLE);
        ref.setLazy(true);
        ref.setTimeout(100000);
        ref.setApplication(new ApplicationConfig("stream-consumer"));
        ref.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181"));
        final IStreamGreeter2 iStreamGreeter = ref.get();

        System.out.println("dubbo ref started");
        try {

            StreamObserver<String> streamObserver = iStreamGreeter.sayHello(new StreamObserver<String>() {
                @Override
                public void onNext(String reply) {
                    System.out.println("onNext: " + reply);
                }

                @Override
                public void onError(Throwable throwable) {
                    System.out.println("onError:" + throwable.getMessage());
                }

                @Override
                public void onCompleted() {
                    System.out.println("onCompleted");
                }
            });

            streamObserver.onNext("tony");

            streamObserver.onNext("nick");

            streamObserver.onCompleted();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        System.in.read();
    }
}
