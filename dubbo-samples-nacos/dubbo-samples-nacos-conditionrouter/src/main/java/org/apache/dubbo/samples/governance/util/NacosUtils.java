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

package org.apache.dubbo.samples.governance.util;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.util.Properties;

public class NacosUtils {
    public static void main(String[] args) throws Throwable {
        writeAppRule();
    }

    public static void writeAppRule() throws Throwable {
        String serverAddr = System.getProperty("nacos.address", "localhost");
        String dataId = "governance-conditionrouter-consumer.condition-router";
        String group = "dubbo";
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        ConfigService configService = NacosFactory.createConfigService(properties);

        try (InputStream is = NacosUtils.class.getClassLoader().getResourceAsStream("dubbo-routers-condition.yml")) {
            String content = IOUtils.toString(is);
            if (configService.publishConfig(dataId, group, content)) {
                System.out.println("write " + dataId + ":" + group + " successfully.");
            }
        }
    }
}
