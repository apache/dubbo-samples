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
package org.apache.dubbo.samples.governance;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.dubbo.common.utils.StringUtils;

/**
 *
 */
public class ZKTools {
    private static CuratorFramework client;

    public static void main(String[] args) throws Exception {
        client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", 60 * 1000, 60 * 1000,
                new ExponentialBackoffRetry(1000, 3));
        client.start();

        generateServiceLevelOverride();
    }

    public static void generateServiceLevelOverride() {
        String str = "# Service scope, without any app\n" +
                "---\n" +
                "scope: service\n" +
                "key: org.apache.dubbo.samples.governance.api.DemoService\n" +
                "configs:\n" +
                " - addresses: [0.0.0.0]\n" +
                "   side: consumer\n" +
                "   rules:\n" +
                "    config:\n" +
                "     timeout: 6000\n" +
                "...";

        System.out.println(str);

        try {
            String path = "/dubbo/config/org.apache.dubbo.samples.governance.api.DemoService/configurators";
            if (client.checkExists().forPath(path) == null) {
                client.create().creatingParentsIfNeeded().forPath(path);
            }
            setData(path, str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setData(String path, String data) throws Exception {
        client.setData().forPath(path, data.getBytes());
    }

    private static String pathToKey(String path) {
        if (StringUtils.isEmpty(path)) {
            return path;
        }
        return path.replace("/dubbo/config/", "").replaceAll("/", ".");
    }

}
