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
package org.apache.dubbo.samples.simplified.annotation;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.samples.simplified.annotation.api.AnnotationService;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.Collections;
import java.util.List;

import static org.apache.dubbo.common.constants.CommonConstants.PATH_SEPARATOR;


public class ZkUtil {
    private static String zookeeperHost = System.getProperty("zookeeper.address", "127.0.0.1");
    private static CuratorFramework client;

    static {
        initClient();
    }

    private static void initClient() {
        client = CuratorFrameworkFactory.newClient(zookeeperHost + ":2181", 60 * 1000, 60 * 1000,
                new ExponentialBackoffRetry(1000, 3));
        client.start();
    }

    private static String toRootDir() {
        return "/dubbo";
    }

    private static String toServicePath() {
        String name = AnnotationService.class.getName();
        return toRootDir() + PATH_SEPARATOR + URL.encode(name);
    }

    private static String toCategoryPath(String side) {
        return toServicePath() + PATH_SEPARATOR + side;
    }

    public static String toUrlPath(String side) {
        return toCategoryPath(side);
    }

    public static List<String> getChildren(String path) {
        try {
            return client.getChildren().forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}
