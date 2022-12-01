/*
 *
 *   Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements.  See the NOTICE file distributed with
 *   this work for additional information regarding copyright ownership.
 *   The ASF licenses this file to You under the Apache License, Version 2.0
 *   (the "License"); you may not use this file except in compliance with
 *   the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package org.apache.dubbo.samples.attachment;

import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.samples.attachment.api.AttachmentService;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class AttachmentConsumer {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/attachment-consumer.xml");
        context.start();

        AttachmentService attachmentService = context.getBean("demoService", AttachmentService.class);
        RpcContext.getContext().setAttachment("index", "1");

        String hello = attachmentService.sayHello("world");
        System.out.println(hello);

        // attachment only affective once
        hello = attachmentService.sayHello("world");
        System.out.println(hello);
    }
}
