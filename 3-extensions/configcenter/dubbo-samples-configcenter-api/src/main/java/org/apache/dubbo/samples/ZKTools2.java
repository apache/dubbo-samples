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
package org.apache.dubbo.samples;

import org.apache.dubbo.common.utils.StringUtils;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class ZKTools2 {
    private static CuratorFramework client;
    private static String zookeeperHost1 = System.getProperty("zookeeper.address.1", "127.0.0.1");
    private static String zookeeperPort1 = System.getProperty("zookeeper.port.1", "2181");
    private static String zookeeperHost2 = System.getProperty("zookeeper.address.2", "127.0.0.1");
    private static String zookeeperPort2 = System.getProperty("zookeeper.port.2", "2182");

    public static void main(String[] args) throws Exception {
        initClient();
        generateDubboPropertiesForGlobal();
    }

    public static void generateDubboPropertiesForGlobal() {
        String str = "dubbo.registry.address=zookeeper://" + zookeeperHost2 + ":" + zookeeperPort2;

        System.out.println(str);

        try {
            String path = "/dubbo/config/dubbo/dubbo.properties";
            if (client.checkExists().forPath(path) == null) {
                client.create().creatingParentsIfNeeded().forPath(path);
            }
            setData(path, str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setZookeeperServer1(String host, String port) {
        zookeeperHost1 = host;
        zookeeperPort1 = port;
    }

    public static void setZookeeperServer2(String host, String port) {
        zookeeperHost2 = host;
        zookeeperPort2 = port;
    }

    public static void initClient() {
        client = CuratorFrameworkFactory.newClient(zookeeperHost1 + ":" + zookeeperPort1,
                60 * 1000, 60 * 1000,
                new ExponentialBackoffRetry(1000, 3));
        client.start();
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
