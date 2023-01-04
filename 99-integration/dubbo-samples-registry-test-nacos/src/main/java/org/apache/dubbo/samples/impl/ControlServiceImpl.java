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
package org.apache.dubbo.samples.impl;

import org.apache.dubbo.qos.command.impl.Offline;
import org.apache.dubbo.qos.command.impl.Online;
import org.apache.dubbo.rpc.model.FrameworkModel;
import org.apache.dubbo.samples.api.ControlService;
import org.apache.dubbo.samples.api.DemoService1;
import org.apache.dubbo.samples.api.DemoService2;
import org.apache.dubbo.samples.api.DemoService3;

public class ControlServiceImpl implements ControlService {

    @Override
    public void exportService(String serviceName) {
        switch (serviceName) {
            case "DemoService1":
                new Online(FrameworkModel.defaultModel()).online(DemoService1.class.getName());
                break;
            case "DemoService2":
                new Online(FrameworkModel.defaultModel()).online(DemoService2.class.getName());
                break;
            case "DemoService3":
                new Online(FrameworkModel.defaultModel()).online(DemoService3.class.getName());
                break;
            default:
                throw new IllegalArgumentException(serviceName);
        }
    }

    @Override
    public void unExportService(String serviceName) {
        switch (serviceName) {
            case "DemoService1":
                new Offline(FrameworkModel.defaultModel()).offline(DemoService1.class.getName());
                break;
            case "DemoService2":
                new Offline(FrameworkModel.defaultModel()).offline(DemoService2.class.getName());
                break;
            case "DemoService3":
                new Offline(FrameworkModel.defaultModel()).offline(DemoService3.class.getName());
                break;
            default:
                throw new IllegalArgumentException(serviceName);
        }
    }
}
