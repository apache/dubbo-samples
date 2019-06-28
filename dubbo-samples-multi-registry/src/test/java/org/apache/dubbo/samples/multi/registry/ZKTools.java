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
package org.apache.dubbo.samples.multi.registry;

import org.apache.dubbo.common.URL;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ZKTools {
    private static String zookeeperHost = System.getProperty("zookeeper.address", "127.0.0.1");
    private static Map<Integer, CuratorFramework> clients = new HashMap<>();

    private static CuratorFramework initClient(int port) {
        if (!clients.containsKey(port)) {
            CuratorFramework client = CuratorFrameworkFactory.newClient(zookeeperHost + ":" + port, 60 * 1000, 60 * 1000,
                    new ExponentialBackoffRetry(1000, 3));
            client.start();
            clients.put(port, client);
        }
        return clients.get(port);
    }

    public static List<String> getProviders(Class clazz, int port) throws Exception {
        CuratorFramework client = initClient(port);
        String path = "/dubbo/" + clazz.getName() + "/providers";
        List<String> result = client.getChildren().forPath(path);
        return result.stream().map(URL::decode).collect(Collectors.toList());
    }

}
