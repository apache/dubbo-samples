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
package org.apache.dubbo.samples.metadatareport.local.annotation;

import org.apache.dubbo.metadata.identifier.MetadataIdentifier;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import static org.apache.dubbo.common.constants.CommonConstants.PATH_SEPARATOR;

public class ZkUtil {
    private static String zookeeperHost = System.getProperty("zookeeper.address", "127.0.0.1");
    private static CuratorFramework client;

    static {
        initClient();
    }

    public static void initClient() {
        client = CuratorFrameworkFactory.newClient(zookeeperHost + ":2181", 60 * 1000, 60 * 1000,
                new ExponentialBackoffRetry(1000, 3));
        client.start();
    }

    public static String getMetadata(String root, String serviceInterface, String version, String group,
                                     String side, String app) throws Exception {
        String path = getNodePath(root, new MetadataIdentifier(serviceInterface, version, group, side, app));
        return new String(client.getData().forPath(path));
    }

    public static String getNodePath(String root, MetadataIdentifier metadataIdentifier) {
        return toRootDir(root) + metadataIdentifier.getUniqueKey(MetadataIdentifier.KeyTypeEnum.PATH);
    }

    private static String toRootDir(String root) {
        if (root.equals(PATH_SEPARATOR)) {
            return root;
        }
        return root + PATH_SEPARATOR;
    }

    private static void removeGlobalConfig() {
        try {
            client.delete().forPath("/dubbo/config/dubbo/dubbo.properties");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
