/*
 *
 *   Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements.  See the NOTICE file distributed with
 *   this work for additional information regarding copyright ownership.
 *   The ASF licenses this file to You under the Apache License, Version 2.0
 *   (the "License"); you may not use this file except in compliance with
 *   the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */
package org.apache.dubbo.test.config;

import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.support.Parameter;

import java.util.Map;

public class RegistryConfigDelegate {
    private final RegistryConfig registryConfig;

    public RegistryConfigDelegate() {
        this.registryConfig = new RegistryConfig();
    }

    public RegistryConfigDelegate(String address) {
        this.registryConfig = new RegistryConfig(address);
    }

    public RegistryConfigDelegate(String address, String protocol) {
        this.registryConfig = new RegistryConfig(address, protocol);
    }

    public String getProtocol() {
        return registryConfig.getProtocol();
    }

    public void setProtocol(String protocol) {
        registryConfig.setProtocol(protocol);
    }

    @Parameter(excluded = true)
    public String getAddress() {
        return registryConfig.getAddress();
    }

    public void setAddress(String address) {
        registryConfig.setAddress(address);
    }

    public Integer getPort() {
        return registryConfig.getPort();
    }

    public void setPort(Integer port) {
        registryConfig.setPort(port);
    }

    public String getUsername() {
        return registryConfig.getUsername();
    }

    public void setUsername(String username) {
        registryConfig.setUsername(username);
    }

    public String getPassword() {
        return registryConfig.getPassword();
    }

    public void setPassword(String password) {
        registryConfig.setPassword(password);
    }

    @Deprecated
    public Integer getWait() {
        return registryConfig.getWait();
    }

    @Deprecated
    public void setWait(Integer wait) {
        registryConfig.setWait(wait);
    }

    public Boolean isCheck() {
        return registryConfig.isCheck();
    }

    public void setCheck(Boolean check) {
        registryConfig.setCheck(check);
    }

    public String getFile() {
        return registryConfig.getFile();
    }

    public void setFile(String file) {
        registryConfig.setFile(file);
    }

    @Parameter(excluded = true)
    @Deprecated
    public String getTransport() {
        return registryConfig.getTransport();
    }

    @Deprecated
    public void setTransport(String transport) {
        registryConfig.setTransport(transport);
    }

    public String getTransporter() {
        return registryConfig.getTransporter();
    }

    public void setTransporter(String transporter) {
        registryConfig.setTransporter(transporter);
    }

    public String getServer() {
        return registryConfig.getServer();
    }

    public void setServer(String server) {
        registryConfig.setServer(server);
    }

    public String getClient() {
        return registryConfig.getClient();
    }

    public void setClient(String client) {
        registryConfig.setClient(client);
    }

    public Integer getTimeout() {
        return registryConfig.getTimeout();
    }

    public void setTimeout(Integer timeout) {
        registryConfig.setTimeout(timeout);
    }

    public Integer getSession() {
        return registryConfig.getSession();
    }

    public void setSession(Integer session) {
        registryConfig.setSession(session);
    }

    public Boolean isDynamic() {
        return registryConfig.isDynamic();
    }

    public void setDynamic(Boolean dynamic) {
        registryConfig.setDynamic(dynamic);
    }

    public Boolean isRegister() {
        return registryConfig.isRegister();
    }

    public void setRegister(Boolean register) {
        registryConfig.setRegister(register);
    }

    public Boolean isSubscribe() {
        return registryConfig.isSubscribe();
    }

    public void setSubscribe(Boolean subscribe) {
        registryConfig.setSubscribe(subscribe);
    }

    public String getCluster() {
        return registryConfig.getCluster();
    }

    public void setCluster(String cluster) {
        registryConfig.setCluster(cluster);
    }

    public String getGroup() {
        return registryConfig.getGroup();
    }

    public void setGroup(String group) {
        registryConfig.setGroup(group);
    }

    public String getVersion() {
        return registryConfig.getVersion();
    }

    public void setVersion(String version) {
        registryConfig.setVersion(version);
    }

    public Map<String, String> getParameters() {
        return registryConfig.getParameters();
    }

    public void setParameters(Map<String, String> parameters) {
        registryConfig.setParameters(parameters);
    }

    public Boolean isDefault() {
        return registryConfig.isDefault();
    }

    public void setDefault(Boolean isDefault) {
        registryConfig.setDefault(isDefault);
    }

    @Parameter(excluded = true)
    public String getId() {
        return registryConfig.getId();
    }

    public void setId(String id) {
        registryConfig.setId(id);
    }
}
