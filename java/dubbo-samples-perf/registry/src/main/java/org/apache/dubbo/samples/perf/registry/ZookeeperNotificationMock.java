package org.apache.dubbo.samples.perf.registry;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.springframework.util.CollectionUtils;

import java.net.URLEncoder;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
public class ZookeeperNotificationMock {
    private static String zookeeperHost = System.getProperty("zookeeper.address", "11.164.235.9");
    private static String ROOT_PATH = "/dubbo/org.apache.dubbo.samples.basic.api.DemoService/providers/";
    private static ExecutorService executorService = Executors.newFixedThreadPool(100);
    private static String[] nodePathes;
    private static CuratorFramework client;

    public static void main(String[] args) throws Exception {
        initClient();
        if (args.length == 1) {
            deleteProviders();
        } else {
            initProviders();
            mockProvidersChange();
        }
    }

    public static void initClient() throws Exception {
        client = CuratorFrameworkFactory.newClient(zookeeperHost + ":2181", 60 * 1000, 60 * 1000,
                new ExponentialBackoffRetry(1000, 3));
        client.start();
    }

    public static void initProviders() throws Exception {
        nodePathes = new String[1000];
        for (int i = 0; i < 1000; i++) {
            String providerUrl = "dubbo://30.5.125.28:20880/org.apache.dubbo.samples.basic.api.DemoService?anyhost=true&application=demo-provider&bind.ip=30.5.125.122&bind.port=20880&deprecated=false&dubbo=2.0.2&dynamic=true&generic=false&interface=org.apache.dubbo.samples.basic.api.DemoService&methods=testVoid,sayHello&pid=19175&release=2.7.5-SNAPSHOT&side=provider" +
                    "timestamp=" + System.currentTimeMillis();
            try {
                String path = ROOT_PATH + URLEncoder.encode(providerUrl, "utf-8");
                nodePathes[i] = path;
                if (client.checkExists().forPath(path) == null) {
                    client.create().creatingParentsIfNeeded().forPath(path);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Thread.sleep(100);
    }

    public static void mockProvidersChange() throws Exception {
        Random r = new Random();
        String[] changes = new String[100];
        while (true) {
            CountDownLatch deleteLatch = new CountDownLatch(100);
            for (int i = 0; i < 100; i++) {
                String path = nodePathes[r.nextInt(1000)];
                changes[i] = path;
                executorService.submit(() -> {
                    try {
                        deleteNode(path);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        deleteLatch.countDown();
                    }
                });
            }
            deleteLatch.await();

            Thread.sleep(100);

            CountDownLatch createLatch = new CountDownLatch(100);
            for (int i = 0; i < 100; i++) {
                try {
                    createNode(changes[i]);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    createLatch.countDown();
                }
            }
            createLatch.await();

            Thread.sleep(100);
        }
    }

    public static void deleteProviders() throws Exception {
        List<String> children = client.getChildren().forPath("/dubbo/org.apache.dubbo.samples.basic.api.DemoService/providers");
        if (!CollectionUtils.isEmpty(children)) {
            children.forEach(c -> {
                try {
                    deleteNode(ROOT_PATH + c);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private static void createNode(String path) throws Exception {
        client.create().withMode(CreateMode.EPHEMERAL).forPath(path);
        System.out.println("Creating " + path);
    }

    private static void deleteNode(String path) throws Exception {
        client.delete().forPath(path);
        System.out.println("Deleting " + path);
    }

}
