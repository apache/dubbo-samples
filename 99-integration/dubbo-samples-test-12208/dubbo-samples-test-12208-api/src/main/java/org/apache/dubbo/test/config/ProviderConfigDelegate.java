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

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ModuleConfig;
import com.alibaba.dubbo.config.MonitorConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.support.Parameter;

import java.util.List;
import java.util.Map;

public class ProviderConfigDelegate {
    private final ProviderConfig providerConfig;

    public ProviderConfigDelegate() {
        this.providerConfig = new ProviderConfig();
    }

    @Deprecated
    public void setProtocol(String protocol) {
        providerConfig.setProtocol(protocol);
    }

    @Parameter(excluded = true)
    public Boolean isDefault() {
        return providerConfig.isDefault();
    }

    @Deprecated
    public void setDefault(Boolean isDefault) {
        providerConfig.setDefault(isDefault);
    }

    @Parameter(excluded = true)
    public String getHost() {
        return providerConfig.getHost();
    }

    public void setHost(String host) {
        providerConfig.setHost(host);
    }

    @Parameter(excluded = true)
    public Integer getPort() {
        return providerConfig.getPort();
    }

    @Deprecated
    public void setPort(Integer port) {
        providerConfig.setPort(port);
    }

    @Parameter(excluded = true)
    @Deprecated
    public String getPath() {
        return providerConfig.getPath();
    }

    @Deprecated
    public void setPath(String path) {
        providerConfig.setPath(path);
    }

    @Parameter(excluded = true)
    public String getContextpath() {
        return providerConfig.getContextpath();
    }

    public void setContextpath(String contextpath) {
        providerConfig.setContextpath(contextpath);
    }

    public String getThreadpool() {
        return providerConfig.getThreadpool();
    }

    public void setThreadpool(String threadpool) {
        providerConfig.setThreadpool(threadpool);
    }

    public Integer getThreads() {
        return providerConfig.getThreads();
    }

    public void setThreads(Integer threads) {
        providerConfig.setThreads(threads);
    }

    public Integer getIothreads() {
        return providerConfig.getIothreads();
    }

    public void setIothreads(Integer iothreads) {
        providerConfig.setIothreads(iothreads);
    }

    public Integer getQueues() {
        return providerConfig.getQueues();
    }

    public void setQueues(Integer queues) {
        providerConfig.setQueues(queues);
    }

    public Integer getAccepts() {
        return providerConfig.getAccepts();
    }

    public void setAccepts(Integer accepts) {
        providerConfig.setAccepts(accepts);
    }

    public String getCodec() {
        return providerConfig.getCodec();
    }

    public void setCodec(String codec) {
        providerConfig.setCodec(codec);
    }

    public String getCharset() {
        return providerConfig.getCharset();
    }

    public void setCharset(String charset) {
        providerConfig.setCharset(charset);
    }

    public Integer getPayload() {
        return providerConfig.getPayload();
    }

    public void setPayload(Integer payload) {
        providerConfig.setPayload(payload);
    }

    public Integer getBuffer() {
        return providerConfig.getBuffer();
    }

    public void setBuffer(Integer buffer) {
        providerConfig.setBuffer(buffer);
    }

    public String getServer() {
        return providerConfig.getServer();
    }

    public void setServer(String server) {
        providerConfig.setServer(server);
    }

    public String getClient() {
        return providerConfig.getClient();
    }

    public void setClient(String client) {
        providerConfig.setClient(client);
    }

    public String getTelnet() {
        return providerConfig.getTelnet();
    }

    public void setTelnet(String telnet) {
        providerConfig.setTelnet(telnet);
    }

    @Parameter(escaped = true)
    public String getPrompt() {
        return providerConfig.getPrompt();
    }

    public void setPrompt(String prompt) {
        providerConfig.setPrompt(prompt);
    }

    public String getStatus() {
        return providerConfig.getStatus();
    }

    public void setStatus(String status) {
        providerConfig.setStatus(status);
    }

    public String getCluster() {
        return providerConfig.getCluster();
    }

    public Integer getConnections() {
        return providerConfig.getConnections();
    }

    public Integer getTimeout() {
        return providerConfig.getTimeout();
    }

    public Integer getRetries() {
        return providerConfig.getRetries();
    }

    public String getLoadbalance() {
        return providerConfig.getLoadbalance();
    }

    public Boolean isAsync() {
        return providerConfig.isAsync();
    }

    public Integer getActives() {
        return providerConfig.getActives();
    }

    public String getTransporter() {
        return providerConfig.getTransporter();
    }

    public void setTransporter(String transporter) {
        providerConfig.setTransporter(transporter);
    }

    public String getExchanger() {
        return providerConfig.getExchanger();
    }

    public void setExchanger(String exchanger) {
        providerConfig.setExchanger(exchanger);
    }

    @Parameter(excluded = true)
    @Deprecated
    public String getDispather() {
        return providerConfig.getDispather();
    }

    @Deprecated
    public void setDispather(String dispather) {
        providerConfig.setDispather(dispather);
    }

    public String getDispatcher() {
        return providerConfig.getDispatcher();
    }

    public void setDispatcher(String dispatcher) {
        providerConfig.setDispatcher(dispatcher);
    }

    public String getNetworker() {
        return providerConfig.getNetworker();
    }

    public void setNetworker(String networker) {
        providerConfig.setNetworker(networker);
    }

    public Integer getWait() {
        return providerConfig.getWait();
    }

    public void setWait(Integer wait) {
        providerConfig.setWait(wait);
    }

    public String getVersion() {
        return providerConfig.getVersion();
    }

    public void setVersion(String version) {
        providerConfig.setVersion(version);
    }

    public String getGroup() {
        return providerConfig.getGroup();
    }

    public void setGroup(String group) {
        providerConfig.setGroup(group);
    }

    public Integer getDelay() {
        return providerConfig.getDelay();
    }

    public void setDelay(Integer delay) {
        providerConfig.setDelay(delay);
    }

    public Boolean getExport() {
        return providerConfig.getExport();
    }

    public void setExport(Boolean export) {
        providerConfig.setExport(export);
    }

    public Integer getWeight() {
        return providerConfig.getWeight();
    }

    public void setWeight(Integer weight) {
        providerConfig.setWeight(weight);
    }

    @Parameter(escaped = true)
    public String getDocument() {
        return providerConfig.getDocument();
    }

    public void setDocument(String document) {
        providerConfig.setDocument(document);
    }

    public String getToken() {
        return providerConfig.getToken();
    }

    public void setToken(String token) {
        providerConfig.setToken(token);
    }

    public void setToken(Boolean token) {
        providerConfig.setToken(token);
    }

    public Boolean isDeprecated() {
        return providerConfig.isDeprecated();
    }

    public void setDeprecated(Boolean deprecated) {
        providerConfig.setDeprecated(deprecated);
    }

    public Boolean isDynamic() {
        return providerConfig.isDynamic();
    }

    public void setDynamic(Boolean dynamic) {
        providerConfig.setDynamic(dynamic);
    }

    public List<ProtocolConfig> getProtocols() {
        return providerConfig.getProtocols();
    }

    public void setProtocols(List<? extends ProtocolConfig> protocols) {
        providerConfig.setProtocols(protocols);
    }

    public ProtocolConfig getProtocol() {
        return providerConfig.getProtocol();
    }

    public void setProtocol(ProtocolConfig protocol) {
        providerConfig.setProtocol(protocol);
    }

    public String getAccesslog() {
        return providerConfig.getAccesslog();
    }

    public void setAccesslog(String accesslog) {
        providerConfig.setAccesslog(accesslog);
    }

    public void setAccesslog(Boolean accesslog) {
        providerConfig.setAccesslog(accesslog);
    }

    public Integer getExecutes() {
        return providerConfig.getExecutes();
    }

    public void setExecutes(Integer executes) {
        providerConfig.setExecutes(executes);
    }

    @Parameter(key = "service.filter", append = true)
    public String getFilter() {
        return providerConfig.getFilter();
    }

    @Parameter(key = "exporter.listener", append = true)
    public String getListener() {
        return providerConfig.getListener();
    }

    public void setListener(String listener) {
        providerConfig.setListener(listener);
    }

    public Boolean isRegister() {
        return providerConfig.isRegister();
    }

    public void setRegister(Boolean register) {
        providerConfig.setRegister(register);
    }

    public Integer getWarmup() {
        return providerConfig.getWarmup();
    }

    public void setWarmup(Integer warmup) {
        providerConfig.setWarmup(warmup);
    }

    public String getSerialization() {
        return providerConfig.getSerialization();
    }

    public void setSerialization(String serialization) {
        providerConfig.setSerialization(serialization);
    }

    @Parameter(key = "dubbo.tag")
    public String getTag() {
        return providerConfig.getTag();
    }

    public void setTag(String tag) {
        providerConfig.setTag(tag);
    }

    @Deprecated
    public String getLocal() {
        return providerConfig.getLocal();
    }

    @Deprecated
    public void setLocal(Boolean local) {
        providerConfig.setLocal(local);
    }

    @Deprecated
    public void setLocal(String local) {
        providerConfig.setLocal(local);
    }

    public String getStub() {
        return providerConfig.getStub();
    }

    public void setStub(Boolean stub) {
        providerConfig.setStub(stub);
    }

    public void setStub(String stub) {
        providerConfig.setStub(stub);
    }

    public void setCluster(String cluster) {
        providerConfig.setCluster(cluster);
    }

    public String getProxy() {
        return providerConfig.getProxy();
    }

    public void setProxy(String proxy) {
        providerConfig.setProxy(proxy);
    }

    public void setConnections(Integer connections) {
        providerConfig.setConnections(connections);
    }

    public void setFilter(String filter) {
        providerConfig.setFilter(filter);
    }

    public String getLayer() {
        return providerConfig.getLayer();
    }

    public void setLayer(String layer) {
        providerConfig.setLayer(layer);
    }

    public ApplicationConfig getApplication() {
        return providerConfig.getApplication();
    }

    public void setApplication(ApplicationConfig application) {
        providerConfig.setApplication(application);
    }

    public ModuleConfig getModule() {
        return providerConfig.getModule();
    }

    public void setModule(ModuleConfig module) {
        providerConfig.setModule(module);
    }

    public RegistryConfig getRegistry() {
        return providerConfig.getRegistry();
    }

    public void setRegistry(RegistryConfig registry) {
        providerConfig.setRegistry(registry);
    }

    public List<RegistryConfig> getRegistries() {
        return providerConfig.getRegistries();
    }

    public void setRegistries(List<? extends RegistryConfig> registries) {
        providerConfig.setRegistries(registries);
    }

    public MonitorConfig getMonitor() {
        return providerConfig.getMonitor();
    }

    public void setMonitor(String monitor) {
        providerConfig.setMonitor(monitor);
    }

    public void setMonitor(MonitorConfig monitor) {
        providerConfig.setMonitor(monitor);
    }

    public String getOwner() {
        return providerConfig.getOwner();
    }

    public void setOwner(String owner) {
        providerConfig.setOwner(owner);
    }

    public Integer getCallbacks() {
        return providerConfig.getCallbacks();
    }

    public void setCallbacks(Integer callbacks) {
        providerConfig.setCallbacks(callbacks);
    }

    public String getOnconnect() {
        return providerConfig.getOnconnect();
    }

    public void setOnconnect(String onconnect) {
        providerConfig.setOnconnect(onconnect);
    }

    public String getOndisconnect() {
        return providerConfig.getOndisconnect();
    }

    public void setOndisconnect(String ondisconnect) {
        providerConfig.setOndisconnect(ondisconnect);
    }

    public String getScope() {
        return providerConfig.getScope();
    }

    public void setScope(String scope) {
        providerConfig.setScope(scope);
    }

    public Integer getForks() {
        return providerConfig.getForks();
    }

    public void setForks(Integer forks) {
        providerConfig.setForks(forks);
    }

    public void setTimeout(Integer timeout) {
        providerConfig.setTimeout(timeout);
    }

    public void setRetries(Integer retries) {
        providerConfig.setRetries(retries);
    }

    public void setLoadbalance(String loadbalance) {
        providerConfig.setLoadbalance(loadbalance);
    }

    public void setAsync(Boolean async) {
        providerConfig.setAsync(async);
    }

    public void setActives(Integer actives) {
        providerConfig.setActives(actives);
    }

    public Boolean getSent() {
        return providerConfig.getSent();
    }

    public void setSent(Boolean sent) {
        providerConfig.setSent(sent);
    }

    @Parameter(escaped = true)
    public String getMock() {
        return providerConfig.getMock();
    }

    public void setMock(Boolean mock) {
        providerConfig.setMock(mock);
    }

    public void setMock(String mock) {
        providerConfig.setMock(mock);
    }

    public String getMerger() {
        return providerConfig.getMerger();
    }

    public void setMerger(String merger) {
        providerConfig.setMerger(merger);
    }

    public String getCache() {
        return providerConfig.getCache();
    }

    public void setCache(String cache) {
        providerConfig.setCache(cache);
    }

    public String getValidation() {
        return providerConfig.getValidation();
    }

    public void setValidation(String validation) {
        providerConfig.setValidation(validation);
    }

    public Map<String, String> getParameters() {
        return providerConfig.getParameters();
    }

    public void setParameters(Map<String, String> parameters) {
        providerConfig.setParameters(parameters);
    }

    @Parameter(excluded = true)
    public String getId() {
        return providerConfig.getId();
    }

    public void setId(String id) {
        providerConfig.setId(id);
    }
}
