/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.apache.dubbo.sample.tri.util;


import org.apache.dubbo.common.constants.CommonConstants;

import com.alibaba.fastjson2.util.JDKUtils;

import static org.apache.dubbo.common.constants.RegistryConstants.DEFAULT_REGISTER_MODE_ALL;
import static org.apache.dubbo.common.constants.RegistryConstants.DEFAULT_REGISTER_MODE_INSTANCE;
import static org.apache.dubbo.common.constants.RegistryConstants.DEFAULT_REGISTER_MODE_INTERFACE;
import static org.apache.dubbo.common.constants.RegistryConstants.REGISTER_MODE_KEY;

public class TriSampleConstants {

    // macos 11 later the 50051 is occupied by system (pid=1!!!)
    public static final int SERVER_PORT = Integer.parseInt(System.getProperty("provider.port",  "50052"));

    public static final int GRPC_SERVER_PORT = Integer.parseInt(System.getProperty("grpc.provider.port", "50053"));

    public static final int DEFAULT_DUBBO_PORT = 20880;

    public static final int CONSUMER_METADATA_SERVICE_PORT = 20881;

    public static final String HOST = System.getProperty("provider.host", "127.0.0.1");

    public static final String ZK_HOST = System.getProperty("zookeeper.host", "127.0.0.1");

    public static final int ZK_PORT = Integer.parseInt(System.getProperty("zookeeper.port", "2181"));

    public static final String ZK_ADDRESS = "zookeeper://" + ZK_HOST + ":" + ZK_PORT;

    public static final String ZK_ADDRESS_MODE_INSTANCE = ZK_ADDRESS + "?" + REGISTER_MODE_KEY + "=" + DEFAULT_REGISTER_MODE_INSTANCE;

    public static final String ZK_ADDRESS_MODE_INTERFACE = ZK_ADDRESS + "?" + REGISTER_MODE_KEY + "=" + DEFAULT_REGISTER_MODE_INTERFACE;

    public static final String ZK_ADDRESS_MODE_ALL = ZK_ADDRESS + "?" + REGISTER_MODE_KEY + "=" + DEFAULT_REGISTER_MODE_ALL;

    public static final String LOCAL_HOST = "localhost";

    public static final String DEFAULT_ADDRESS = CommonConstants.TRIPLE + "://" + HOST + ":" + SERVER_PORT + "?serialization=" + (JDKUtils.JVM_VERSION >= 17 ? "fastjson2" : "hessian2");

    public static final String DEFAULT_MULTI_ADDRESS = CommonConstants.TRIPLE + "://" + HOST + ":" + SERVER_PORT + ";" + CommonConstants.TRIPLE + "://" + LOCAL_HOST + ":" + SERVER_PORT;

    public static final String GRPC_DIRECT_URL = CommonConstants.TRIPLE + "://" + HOST + ":" + GRPC_SERVER_PORT;

}
