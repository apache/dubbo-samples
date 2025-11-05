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

public class ZKTools {
    private static String zookeeperHost = System.getProperty("zookeeper.address", "127.0.0.1");
    private static String zookeeperPort = System.getProperty("zookeeper.port", "2181");
    private static CuratorFramework client;

    public static void main(String[] args) throws Exception {
        initClient();
        generateAppevelRouter();
        generateAppevelRouter2();
    }

    public static void start(){
        initClient();
        generateAppevelRouter();
        generateAppevelRouter2();
    }

    public static void initClient() {
        client = CuratorFrameworkFactory.newClient(zookeeperHost + ":" + zookeeperPort, 60 * 1000, 60 * 1000,
                new ExponentialBackoffRetry(1000, 3));
        client.start();
    }

    public static void generateAppevelRouter() {
        String str = "configVersion: v3.1\n" + "scope: application\n"
//                + "key: org.apache.dubbo.samples.governance.api.DemoService\n"
                + "key: governance-conditionrouterv31-consumer\n"
                + "force: false\n"
                + "runtime: true\n"
                + "enabled: true\n"
                + "conditions:\n"
                + "  - from:\n"
                + "      match: env=gray\n"
                + "    to:\n"
                + "      - match: env!=$env\n"
                + "        weight: 100";

        System.out.println(str);

        try {
            String path = "/dubbo/config/dubbo/governance-conditionrouterv31-consumer.condition-router";
            if (client.checkExists().forPath(path) == null) {
                client.create().creatingParentsIfNeeded().forPath(path);
            }
            setData(path, str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generateAppevelRouter2() {
        String str = "configVersion: v3.1\n" + "scope: application\n"
//                + "key: org.apache.dubbo.samples.governance.api.DemoService2\n"
                + "key: governance-conditionrouterv31-consumer-2\n"
                + "force: false\n"
                + "runtime: true\n"
                + "enabled: true\n"
                + "conditions:\n"
                + "  - from:\n"
                + "      match: env=white\n"
                + "    to:\n"
                + "      - match: env!=$env\n"
                + "        weight: 100";

        System.out.println(str);

        try {
            String path = "/dubbo/config/dubbo/governance-conditionrouterv31-consumer-2.condition-router";
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

}
