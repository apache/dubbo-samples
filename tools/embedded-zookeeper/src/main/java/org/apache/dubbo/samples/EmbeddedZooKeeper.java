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

import java.io.File;
import java.util.Properties;
import java.util.UUID;

import org.apache.zookeeper.server.ServerConfig;
import org.apache.zookeeper.server.ZooKeeperServerMain;
import org.apache.zookeeper.server.quorum.QuorumPeerConfig;

public class EmbeddedZooKeeper {

    public static void main(String[] args) throws Exception {
        int port = 2181;
        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }

        Properties properties = new Properties();
        File file = new File(System.getProperty("java.io.tmpdir")
                + File.separator + UUID.randomUUID());
        file.deleteOnExit();
        properties.setProperty("dataDir", file.getAbsolutePath());
        properties.setProperty("clientPort", String.valueOf(port));

        QuorumPeerConfig quorumPeerConfig = new QuorumPeerConfig();
        quorumPeerConfig.parseProperties(properties);

        ZooKeeperServerMain zkServer = new ZooKeeperServerMain();
        ServerConfig configuration = new ServerConfig();
        configuration.readFrom(quorumPeerConfig);

        try {
            zkServer.runFromConfig(configuration);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
