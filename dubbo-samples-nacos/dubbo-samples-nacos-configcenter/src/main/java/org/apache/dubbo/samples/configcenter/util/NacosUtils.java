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

package org.apache.dubbo.samples.configcenter.util;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Properties;

public class NacosUtils {
    public static void main(String[] args) throws Throwable {
        writeDubboProperties();
    }

    public static void writeDubboProperties() throws Throwable {
        String serverAddr = System.getProperty("nacos.address", "localhost");
        String dataId = "dubbo.properties";
        String group = "dubbo";
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        ConfigService configService = NacosFactory.createConfigService(properties);

        StringBuilder content = new StringBuilder();
        File file = new File(NacosUtils.class.getClassLoader().getResource("config-center.properties").getFile());
        try (FileReader reader = new FileReader(file)) {
            BufferedReader br = new BufferedReader(reader);
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("dubbo.registry.address=")) {
                    line = "dubbo.registry.address=nacos://" + serverAddr + ":8848";
                }
                content.append(line).append("\n");
            }
        }

        if (configService.publishConfig(dataId, group, content.toString())) {
            System.out.println("write " + dataId + ":" + group + " successfully.");
        }
    }
}
