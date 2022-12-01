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
package org.apache.dubbo.samples.metadatareport.configcenter;

import org.apache.dubbo.common.Version;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.metadata.report.identifier.KeyTypeEnum;
import org.apache.dubbo.metadata.report.identifier.MetadataIdentifier;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import static org.apache.dubbo.common.constants.CommonConstants.PATH_SEPARATOR;

public class ZKTools {
    public static final String GLOBAL_DUBBO_PROPERTIES_PATH = "/dubbo/config/dubbo/dubbo.properties";
    public static final String CONSUMER_DUBBO_PROPERTIES_PATH = "/dubbo/config/metadatareport-configcenter-consumer/dubbo.properties";
    public static final String PROVIDER_DUBBO_PROPERTIES_PATH = "/dubbo/config/metadatareport-configcenter-provider/dubbo.properties";
    private static String zookeeperHost = System.getProperty("zookeeper.address", "127.0.0.1");
    private static CuratorFramework client;

    public static final int VERSION300 = Version.getIntVersion("3.0.0");

    static {
        initClient();
    }

    public static void main(String[] args) throws Exception {
        generateDubboProperties();
        removeDubboProperties();
    }

    public static void generateDubboProperties() {
        generateDubboPropertiesForGlobal();
        generateDubboPropertiesForProvider();
        generateDubboPropertiesForConsumer();
    }

    private static void initClient() {
        client = CuratorFrameworkFactory.newClient(zookeeperHost + ":2181", 60 * 1000, 60 * 1000,
                new ExponentialBackoffRetry(1000, 3));
        client.start();
    }

    public static void generateDubboPropertiesForGlobal() {
        String str = "dubbo.registry.address=zookeeper://" + zookeeperHost + ":2181\n" +
                "dubbo.metadata-report.address=zookeeper://" + zookeeperHost + ":2181\n" +
                "#global config for consumer\n" +
                "dubbo.consumer.timeout=6000\n" +
                "#global config for provider\n" +
                "dubbo.protocol.port=20831\n";

        if (Version.getIntVersion(Version.getVersion()) >= VERSION300) {
            // dubbo 3.x
            str += "dubbo.provider.timeout=5000\n";
            // TODO compatible with 3.0 preview and dev branch, remove the following line after merging dubbo 3.0 config refactor pr
            str += "dubbo.provider.my-provider.timeout=5000";
        } else {
            // compatible with dubbo 2.7.x
            str += "dubbo.provider.my-provider.timeout=5000";
        }

        System.out.println(str);

        try {
            String path = GLOBAL_DUBBO_PROPERTIES_PATH;
            if (client.checkExists().forPath(path) == null) {
                client.create().creatingParentsIfNeeded().forPath(path);
            }
            setData(path, str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generateDubboPropertiesForConsumer() {
        String str = "dubbo.consumer.timeout=6666";

        System.out.println(str);

        try {
            String path = CONSUMER_DUBBO_PROPERTIES_PATH;
            if (client.checkExists().forPath(path) == null) {
                client.create().creatingParentsIfNeeded().forPath(path);
            }
            setData(path, str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generateDubboPropertiesForProvider() {
        String str = "dubbo.protocol.threadpool=fixed\n" +
                "dubbo.protocol.threads=100";

        System.out.println(str);

        try {
            String path = PROVIDER_DUBBO_PROPERTIES_PATH;
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

    private static void removeInterfaceData(Class interfaceClazz) {
        try {
            client.delete().forPath("/dubbo/" + interfaceClazz.getName().replaceAll("\\.", "/"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getMetadata(String root, String serviceInterface, String version, String group,
                                     String side, String app) throws Exception {
        String path = getNodePath(root, new MetadataIdentifier(serviceInterface, version, group, side, app));
        return new String(client.getData().forPath(path));
    }

    public static String getNodePath(String root, MetadataIdentifier metadataIdentifier) {
        return toRootDir(root) + metadataIdentifier.getUniqueKey(KeyTypeEnum.PATH);
    }

    private static String toRootDir(String root) {
        if (root.equals(PATH_SEPARATOR)) {
            return root;
        }
        return root + PATH_SEPARATOR;
    }

    public static void removeDubboProperties() {
        try {
            client.delete().forPath(GLOBAL_DUBBO_PROPERTIES_PATH);
            client.delete().forPath(PROVIDER_DUBBO_PROPERTIES_PATH);
            client.delete().forPath(CONSUMER_DUBBO_PROPERTIES_PATH);
            client.delete().forPath(getParent(PROVIDER_DUBBO_PROPERTIES_PATH));
            client.delete().forPath(getParent(CONSUMER_DUBBO_PROPERTIES_PATH));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getParent(String path) {
        return path.substring(0, PROVIDER_DUBBO_PROPERTIES_PATH.lastIndexOf('/'));
    }

}
