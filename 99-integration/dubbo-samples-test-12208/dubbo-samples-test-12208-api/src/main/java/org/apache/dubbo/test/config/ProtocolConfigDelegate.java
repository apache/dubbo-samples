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

import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.support.Parameter;

import java.util.Map;

public class ProtocolConfigDelegate {
    private final ProtocolConfig protocolConfig;

    public ProtocolConfigDelegate() {
        this.protocolConfig = new ProtocolConfig();
    }

    public ProtocolConfigDelegate(String name) {
        this.protocolConfig = new ProtocolConfig(name);
    }

    public ProtocolConfigDelegate(String name, int port) {
        this.protocolConfig = new ProtocolConfig(name, port);
    }

    @Parameter(excluded = true)
    public String getName() {
        return protocolConfig.getName();
    }

    public void setName(String name) {
        protocolConfig.setName(name);
    }

    @Parameter(excluded = true)
    public String getHost() {
        return protocolConfig.getHost();
    }

    public void setHost(String host) {
        protocolConfig.setHost(host);
    }

    @Parameter(excluded = true)
    public Integer getPort() {
        return protocolConfig.getPort();
    }

    public void setPort(Integer port) {
        protocolConfig.setPort(port);
    }

    @Parameter(excluded = true)
    @Deprecated
    public String getPath() {
        return protocolConfig.getPath();
    }

    @Deprecated
    public void setPath(String path) {
        protocolConfig.setPath(path);
    }

    @Parameter(excluded = true)
    public String getContextpath() {
        return protocolConfig.getContextpath();
    }

    public void setContextpath(String contextpath) {
        protocolConfig.setContextpath(contextpath);
    }

    public String getThreadpool() {
        return protocolConfig.getThreadpool();
    }

    public void setThreadpool(String threadpool) {
        protocolConfig.setThreadpool(threadpool);
    }

    public Integer getThreads() {
        return protocolConfig.getThreads();
    }

    public void setThreads(Integer threads) {
        protocolConfig.setThreads(threads);
    }

    public Integer getIothreads() {
        return protocolConfig.getIothreads();
    }

    public void setIothreads(Integer iothreads) {
        protocolConfig.setIothreads(iothreads);
    }

    public Integer getQueues() {
        return protocolConfig.getQueues();
    }

    public void setQueues(Integer queues) {
        protocolConfig.setQueues(queues);
    }

    public Integer getAccepts() {
        return protocolConfig.getAccepts();
    }

    public void setAccepts(Integer accepts) {
        protocolConfig.setAccepts(accepts);
    }

    public String getCodec() {
        return protocolConfig.getCodec();
    }

    public void setCodec(String codec) {
        protocolConfig.setCodec(codec);
    }

    public String getSerialization() {
        return protocolConfig.getSerialization();
    }

    public void setSerialization(String serialization) {
        protocolConfig.setSerialization(serialization);
    }

    public String getCharset() {
        return protocolConfig.getCharset();
    }

    public void setCharset(String charset) {
        protocolConfig.setCharset(charset);
    }

    public Integer getPayload() {
        return protocolConfig.getPayload();
    }

    public void setPayload(Integer payload) {
        protocolConfig.setPayload(payload);
    }

    public Integer getBuffer() {
        return protocolConfig.getBuffer();
    }

    public void setBuffer(Integer buffer) {
        protocolConfig.setBuffer(buffer);
    }

    public Integer getHeartbeat() {
        return protocolConfig.getHeartbeat();
    }

    public void setHeartbeat(Integer heartbeat) {
        protocolConfig.setHeartbeat(heartbeat);
    }

    public String getServer() {
        return protocolConfig.getServer();
    }

    public void setServer(String server) {
        protocolConfig.setServer(server);
    }

    public String getClient() {
        return protocolConfig.getClient();
    }

    public void setClient(String client) {
        protocolConfig.setClient(client);
    }

    public String getAccesslog() {
        return protocolConfig.getAccesslog();
    }

    public void setAccesslog(String accesslog) {
        protocolConfig.setAccesslog(accesslog);
    }

    public String getTelnet() {
        return protocolConfig.getTelnet();
    }

    public void setTelnet(String telnet) {
        protocolConfig.setTelnet(telnet);
    }

    @Parameter(escaped = true)
    public String getPrompt() {
        return protocolConfig.getPrompt();
    }

    public void setPrompt(String prompt) {
        protocolConfig.setPrompt(prompt);
    }

    public String getStatus() {
        return protocolConfig.getStatus();
    }

    public void setStatus(String status) {
        protocolConfig.setStatus(status);
    }

    public Boolean isRegister() {
        return protocolConfig.isRegister();
    }

    public void setRegister(Boolean register) {
        protocolConfig.setRegister(register);
    }

    public String getTransporter() {
        return protocolConfig.getTransporter();
    }

    public void setTransporter(String transporter) {
        protocolConfig.setTransporter(transporter);
    }

    public String getExchanger() {
        return protocolConfig.getExchanger();
    }

    public void setExchanger(String exchanger) {
        protocolConfig.setExchanger(exchanger);
    }

    @Parameter(excluded = true)
    @Deprecated
    public String getDispather() {
        return protocolConfig.getDispather();
    }

    @Deprecated
    public void setDispather(String dispather) {
        protocolConfig.setDispather(dispather);
    }

    public String getDispatcher() {
        return protocolConfig.getDispatcher();
    }

    public void setDispatcher(String dispatcher) {
        protocolConfig.setDispatcher(dispatcher);
    }

    public String getNetworker() {
        return protocolConfig.getNetworker();
    }

    public void setNetworker(String networker) {
        protocolConfig.setNetworker(networker);
    }

    public Map<String, String> getParameters() {
        return protocolConfig.getParameters();
    }

    public void setParameters(Map<String, String> parameters) {
        protocolConfig.setParameters(parameters);
    }

    public Boolean isDefault() {
        return protocolConfig.isDefault();
    }

    public void setDefault(Boolean isDefault) {
        protocolConfig.setDefault(isDefault);
    }

    public Boolean getKeepAlive() {
        return protocolConfig.getKeepAlive();
    }

    public void setKeepAlive(Boolean keepAlive) {
        protocolConfig.setKeepAlive(keepAlive);
    }

    public String getOptimizer() {
        return protocolConfig.getOptimizer();
    }

    public void setOptimizer(String optimizer) {
        protocolConfig.setOptimizer(optimizer);
    }

    public String getExtension() {
        return protocolConfig.getExtension();
    }

    public void setExtension(String extension) {
        protocolConfig.setExtension(extension);
    }

    public void destroy() {
        protocolConfig.destroy();
    }

    @Parameter(excluded = true)
    public String getId() {
        return protocolConfig.getId();
    }

    public void setId(String id) {
        protocolConfig.setId(id);
    }
}
