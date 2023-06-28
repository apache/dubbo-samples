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
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.ModuleConfig;
import com.alibaba.dubbo.config.MonitorConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.support.Parameter;

import java.util.List;
import java.util.Map;

public class ConsumerConfigDelegate {
    private final ConsumerConfig consumerConfig;

    public ConsumerConfigDelegate() {
        this.consumerConfig = new ConsumerConfig();
    }

    public void setTimeout(Integer timeout) {
        consumerConfig.setTimeout(timeout);
    }

    public Boolean isDefault() {
        return consumerConfig.isDefault();
    }

    public void setDefault(Boolean isDefault) {
        consumerConfig.setDefault(isDefault);
    }

    public String getClient() {
        return consumerConfig.getClient();
    }

    public void setClient(String client) {
        consumerConfig.setClient(client);
    }

    public String getThreadpool() {
        return consumerConfig.getThreadpool();
    }

    public void setThreadpool(String threadpool) {
        consumerConfig.setThreadpool(threadpool);
    }

    public Boolean getDefault() {
        return consumerConfig.getDefault();
    }

    public Integer getCorethreads() {
        return consumerConfig.getCorethreads();
    }

    public void setCorethreads(Integer corethreads) {
        consumerConfig.setCorethreads(corethreads);
    }

    public Integer getThreads() {
        return consumerConfig.getThreads();
    }

    public void setThreads(Integer threads) {
        consumerConfig.setThreads(threads);
    }

    public Integer getQueues() {
        return consumerConfig.getQueues();
    }

    public void setQueues(Integer queues) {
        consumerConfig.setQueues(queues);
    }

    public Boolean isCheck() {
        return consumerConfig.isCheck();
    }

    public void setCheck(Boolean check) {
        consumerConfig.setCheck(check);
    }

    public Boolean isInit() {
        return consumerConfig.isInit();
    }

    public void setInit(Boolean init) {
        consumerConfig.setInit(init);
    }

    @Parameter(excluded = true)
    public Boolean isGeneric() {
        return consumerConfig.isGeneric();
    }

    public void setGeneric(Boolean generic) {
        consumerConfig.setGeneric(generic);
    }

    public String getGeneric() {
        return consumerConfig.getGeneric();
    }

    public void setGeneric(String generic) {
        consumerConfig.setGeneric(generic);
    }

    @Deprecated
    public Boolean isInjvm() {
        return consumerConfig.isInjvm();
    }

    @Deprecated
    public void setInjvm(Boolean injvm) {
        consumerConfig.setInjvm(injvm);
    }

    @Parameter(key = "reference.filter", append = true)
    public String getFilter() {
        return consumerConfig.getFilter();
    }

    @Parameter(key = "invoker.listener", append = true)
    public String getListener() {
        return consumerConfig.getListener();
    }

    public void setListener(String listener) {
        consumerConfig.setListener(listener);
    }

    @Parameter(key = "lazy")
    public Boolean getLazy() {
        return consumerConfig.getLazy();
    }

    public void setLazy(Boolean lazy) {
        consumerConfig.setLazy(lazy);
    }

    public void setOnconnect(String onconnect) {
        consumerConfig.setOnconnect(onconnect);
    }

    public void setOndisconnect(String ondisconnect) {
        consumerConfig.setOndisconnect(ondisconnect);
    }

    @Parameter(key = "dubbo.stub.event")
    public Boolean getStubevent() {
        return consumerConfig.getStubevent();
    }

    @Parameter(key = "reconnect")
    public String getReconnect() {
        return consumerConfig.getReconnect();
    }

    public void setReconnect(String reconnect) {
        consumerConfig.setReconnect(reconnect);
    }

    @Parameter(key = "sticky")
    public Boolean getSticky() {
        return consumerConfig.getSticky();
    }

    public void setSticky(Boolean sticky) {
        consumerConfig.setSticky(sticky);
    }

    public String getVersion() {
        return consumerConfig.getVersion();
    }

    public void setVersion(String version) {
        consumerConfig.setVersion(version);
    }

    public String getGroup() {
        return consumerConfig.getGroup();
    }

    public void setGroup(String group) {
        consumerConfig.setGroup(group);
    }

    @Deprecated
    public String getLocal() {
        return consumerConfig.getLocal();
    }

    @Deprecated
    public void setLocal(Boolean local) {
        consumerConfig.setLocal(local);
    }

    @Deprecated
    public void setLocal(String local) {
        consumerConfig.setLocal(local);
    }

    public String getStub() {
        return consumerConfig.getStub();
    }

    public void setStub(Boolean stub) {
        consumerConfig.setStub(stub);
    }

    public void setStub(String stub) {
        consumerConfig.setStub(stub);
    }

    public String getCluster() {
        return consumerConfig.getCluster();
    }

    public void setCluster(String cluster) {
        consumerConfig.setCluster(cluster);
    }

    public String getProxy() {
        return consumerConfig.getProxy();
    }

    public void setProxy(String proxy) {
        consumerConfig.setProxy(proxy);
    }

    public Integer getConnections() {
        return consumerConfig.getConnections();
    }

    public void setConnections(Integer connections) {
        consumerConfig.setConnections(connections);
    }

    public void setFilter(String filter) {
        consumerConfig.setFilter(filter);
    }

    public String getLayer() {
        return consumerConfig.getLayer();
    }

    public void setLayer(String layer) {
        consumerConfig.setLayer(layer);
    }

    public ApplicationConfig getApplication() {
        return consumerConfig.getApplication();
    }

    public void setApplication(ApplicationConfig application) {
        consumerConfig.setApplication(application);
    }

    public ModuleConfig getModule() {
        return consumerConfig.getModule();
    }

    public void setModule(ModuleConfig module) {
        consumerConfig.setModule(module);
    }

    public RegistryConfig getRegistry() {
        return consumerConfig.getRegistry();
    }

    public void setRegistry(RegistryConfig registry) {
        consumerConfig.setRegistry(registry);
    }

    public List<RegistryConfig> getRegistries() {
        return consumerConfig.getRegistries();
    }

    public void setRegistries(List<? extends RegistryConfig> registries) {
        consumerConfig.setRegistries(registries);
    }

    public MonitorConfig getMonitor() {
        return consumerConfig.getMonitor();
    }

    public void setMonitor(String monitor) {
        consumerConfig.setMonitor(monitor);
    }

    public void setMonitor(MonitorConfig monitor) {
        consumerConfig.setMonitor(monitor);
    }

    public String getOwner() {
        return consumerConfig.getOwner();
    }

    public void setOwner(String owner) {
        consumerConfig.setOwner(owner);
    }

    public Integer getCallbacks() {
        return consumerConfig.getCallbacks();
    }

    public void setCallbacks(Integer callbacks) {
        consumerConfig.setCallbacks(callbacks);
    }

    public String getOnconnect() {
        return consumerConfig.getOnconnect();
    }

    public String getOndisconnect() {
        return consumerConfig.getOndisconnect();
    }

    public String getScope() {
        return consumerConfig.getScope();
    }

    public void setScope(String scope) {
        consumerConfig.setScope(scope);
    }

    public Integer getForks() {
        return consumerConfig.getForks();
    }

    public void setForks(Integer forks) {
        consumerConfig.setForks(forks);
    }

    public Integer getTimeout() {
        return consumerConfig.getTimeout();
    }

    public Integer getRetries() {
        return consumerConfig.getRetries();
    }

    public void setRetries(Integer retries) {
        consumerConfig.setRetries(retries);
    }

    public String getLoadbalance() {
        return consumerConfig.getLoadbalance();
    }

    public void setLoadbalance(String loadbalance) {
        consumerConfig.setLoadbalance(loadbalance);
    }

    public Boolean isAsync() {
        return consumerConfig.isAsync();
    }

    public void setAsync(Boolean async) {
        consumerConfig.setAsync(async);
    }

    public Integer getActives() {
        return consumerConfig.getActives();
    }

    public void setActives(Integer actives) {
        consumerConfig.setActives(actives);
    }

    public Boolean getSent() {
        return consumerConfig.getSent();
    }

    public void setSent(Boolean sent) {
        consumerConfig.setSent(sent);
    }

    @Parameter(escaped = true)
    public String getMock() {
        return consumerConfig.getMock();
    }

    public void setMock(Boolean mock) {
        consumerConfig.setMock(mock);
    }

    public void setMock(String mock) {
        consumerConfig.setMock(mock);
    }

    public String getMerger() {
        return consumerConfig.getMerger();
    }

    public void setMerger(String merger) {
        consumerConfig.setMerger(merger);
    }

    public String getCache() {
        return consumerConfig.getCache();
    }

    public void setCache(String cache) {
        consumerConfig.setCache(cache);
    }

    public String getValidation() {
        return consumerConfig.getValidation();
    }

    public void setValidation(String validation) {
        consumerConfig.setValidation(validation);
    }

    public Map<String, String> getParameters() {
        return consumerConfig.getParameters();
    }

    public void setParameters(Map<String, String> parameters) {
        consumerConfig.setParameters(parameters);
    }

    @Parameter(excluded = true)
    public String getId() {
        return consumerConfig.getId();
    }

    public void setId(String id) {
        consumerConfig.setId(id);
    }
}
