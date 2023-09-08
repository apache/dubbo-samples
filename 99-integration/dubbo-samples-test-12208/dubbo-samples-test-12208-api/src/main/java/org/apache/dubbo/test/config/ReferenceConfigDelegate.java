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

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.MethodConfig;
import com.alibaba.dubbo.config.ModuleConfig;
import com.alibaba.dubbo.config.MonitorConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.support.Parameter;

import java.util.List;
import java.util.Map;

public class ReferenceConfigDelegate {
    private final ReferenceConfig referenceConfig;

    public ReferenceConfigDelegate() {
        this.referenceConfig = new ReferenceConfig();
    }

    public URL toUrl() {
        return referenceConfig.toUrl();
    }

    public List<URL> toUrls() {
        return referenceConfig.toUrls();
    }

    public Object get() {
        return referenceConfig.get();
    }

    public void destroy() {
        referenceConfig.destroy();
    }

    public Class<?> getInterfaceClass() {
        return referenceConfig.getInterfaceClass();
    }

    @Deprecated
    public void setInterfaceClass(Class interfaceClass) {
        referenceConfig.setInterfaceClass(interfaceClass);
    }

    public String getInterface() {
        return referenceConfig.getInterface();
    }

    public void setInterface(Class interfaceClass) {
        referenceConfig.setInterface(interfaceClass);
    }

    public void setInterface(String interfaceName) {
        referenceConfig.setInterface(interfaceName);
    }

    public String getClient() {
        return referenceConfig.getClient();
    }

    public void setClient(String client) {
        referenceConfig.setClient(client);
    }

    @Parameter(excluded = true)
    public String getUrl() {
        return referenceConfig.getUrl();
    }

    public void setUrl(String url) {
        referenceConfig.setUrl(url);
    }

    public List<MethodConfig> getMethods() {
        return referenceConfig.getMethods();
    }

    public void setMethods(List methods) {
        referenceConfig.setMethods(methods);
    }

    public ConsumerConfig getConsumer() {
        return referenceConfig.getConsumer();
    }

    public void setConsumer(ConsumerConfig consumer) {
        referenceConfig.setConsumer(consumer);
    }

    public String getProtocol() {
        return referenceConfig.getProtocol();
    }

    public void setProtocol(String protocol) {
        referenceConfig.setProtocol(protocol);
    }

    @Parameter(excluded = true)
    public String getUniqueServiceName() {
        return referenceConfig.getUniqueServiceName();
    }

    public String getVersion() {
        return referenceConfig.getVersion();
    }

    public String getGroup() {
        return referenceConfig.getGroup();
    }

    public Boolean isCheck() {
        return referenceConfig.isCheck();
    }

    public void setCheck(Boolean check) {
        referenceConfig.setCheck(check);
    }

    public Boolean isInit() {
        return referenceConfig.isInit();
    }

    public void setInit(Boolean init) {
        referenceConfig.setInit(init);
    }

    @Parameter(excluded = true)
    public Boolean isGeneric() {
        return referenceConfig.isGeneric();
    }

    public void setGeneric(Boolean generic) {
        referenceConfig.setGeneric(generic);
    }

    public String getGeneric() {
        return referenceConfig.getGeneric();
    }

    public void setGeneric(String generic) {
        referenceConfig.setGeneric(generic);
    }

    @Deprecated
    public Boolean isInjvm() {
        return referenceConfig.isInjvm();
    }

    @Deprecated
    public void setInjvm(Boolean injvm) {
        referenceConfig.setInjvm(injvm);
    }

    @Parameter(key = "reference.filter", append = true)
    public String getFilter() {
        return referenceConfig.getFilter();
    }

    @Parameter(key = "invoker.listener", append = true)
    public String getListener() {
        return referenceConfig.getListener();
    }

    public void setListener(String listener) {
        referenceConfig.setListener(listener);
    }

    @Parameter(key = "lazy")
    public Boolean getLazy() {
        return referenceConfig.getLazy();
    }

    public void setLazy(Boolean lazy) {
        referenceConfig.setLazy(lazy);
    }

    public void setOnconnect(String onconnect) {
        referenceConfig.setOnconnect(onconnect);
    }

    public void setOndisconnect(String ondisconnect) {
        referenceConfig.setOndisconnect(ondisconnect);
    }

    @Parameter(key = "dubbo.stub.event")
    public Boolean getStubevent() {
        return referenceConfig.getStubevent();
    }

    @Parameter(key = "reconnect")
    public String getReconnect() {
        return referenceConfig.getReconnect();
    }

    public void setReconnect(String reconnect) {
        referenceConfig.setReconnect(reconnect);
    }

    @Parameter(key = "sticky")
    public Boolean getSticky() {
        return referenceConfig.getSticky();
    }

    public void setSticky(Boolean sticky) {
        referenceConfig.setSticky(sticky);
    }

    public void setVersion(String version) {
        referenceConfig.setVersion(version);
    }

    public void setGroup(String group) {
        referenceConfig.setGroup(group);
    }

    @Deprecated
    public String getLocal() {
        return referenceConfig.getLocal();
    }

    @Deprecated
    public void setLocal(Boolean local) {
        referenceConfig.setLocal(local);
    }

    @Deprecated
    public void setLocal(String local) {
        referenceConfig.setLocal(local);
    }

    public String getStub() {
        return referenceConfig.getStub();
    }

    public void setStub(Boolean stub) {
        referenceConfig.setStub(stub);
    }

    public void setStub(String stub) {
        referenceConfig.setStub(stub);
    }

    public String getCluster() {
        return referenceConfig.getCluster();
    }

    public void setCluster(String cluster) {
        referenceConfig.setCluster(cluster);
    }

    public String getProxy() {
        return referenceConfig.getProxy();
    }

    public void setProxy(String proxy) {
        referenceConfig.setProxy(proxy);
    }

    public Integer getConnections() {
        return referenceConfig.getConnections();
    }

    public void setConnections(Integer connections) {
        referenceConfig.setConnections(connections);
    }

    public void setFilter(String filter) {
        referenceConfig.setFilter(filter);
    }

    public String getLayer() {
        return referenceConfig.getLayer();
    }

    public void setLayer(String layer) {
        referenceConfig.setLayer(layer);
    }

    public ApplicationConfig getApplication() {
        return referenceConfig.getApplication();
    }

    public void setApplication(ApplicationConfig application) {
        referenceConfig.setApplication(application);
    }

    public ModuleConfig getModule() {
        return referenceConfig.getModule();
    }

    public void setModule(ModuleConfig module) {
        referenceConfig.setModule(module);
    }

    public RegistryConfig getRegistry() {
        return referenceConfig.getRegistry();
    }

    public void setRegistry(RegistryConfig registry) {
        referenceConfig.setRegistry(registry);
    }

    public List<RegistryConfig> getRegistries() {
        return referenceConfig.getRegistries();
    }

    public void setRegistries(List<? extends RegistryConfig> registries) {
        referenceConfig.setRegistries(registries);
    }

    public MonitorConfig getMonitor() {
        return referenceConfig.getMonitor();
    }

    public void setMonitor(String monitor) {
        referenceConfig.setMonitor(monitor);
    }

    public void setMonitor(MonitorConfig monitor) {
        referenceConfig.setMonitor(monitor);
    }

    public String getOwner() {
        return referenceConfig.getOwner();
    }

    public void setOwner(String owner) {
        referenceConfig.setOwner(owner);
    }

    public Integer getCallbacks() {
        return referenceConfig.getCallbacks();
    }

    public void setCallbacks(Integer callbacks) {
        referenceConfig.setCallbacks(callbacks);
    }

    public String getOnconnect() {
        return referenceConfig.getOnconnect();
    }

    public String getOndisconnect() {
        return referenceConfig.getOndisconnect();
    }

    public String getScope() {
        return referenceConfig.getScope();
    }

    public void setScope(String scope) {
        referenceConfig.setScope(scope);
    }

    public Integer getForks() {
        return referenceConfig.getForks();
    }

    public void setForks(Integer forks) {
        referenceConfig.setForks(forks);
    }

    public Integer getTimeout() {
        return referenceConfig.getTimeout();
    }

    public void setTimeout(Integer timeout) {
        referenceConfig.setTimeout(timeout);
    }

    public Integer getRetries() {
        return referenceConfig.getRetries();
    }

    public void setRetries(Integer retries) {
        referenceConfig.setRetries(retries);
    }

    public String getLoadbalance() {
        return referenceConfig.getLoadbalance();
    }

    public void setLoadbalance(String loadbalance) {
        referenceConfig.setLoadbalance(loadbalance);
    }

    public Boolean isAsync() {
        return referenceConfig.isAsync();
    }

    public void setAsync(Boolean async) {
        referenceConfig.setAsync(async);
    }

    public Integer getActives() {
        return referenceConfig.getActives();
    }

    public void setActives(Integer actives) {
        referenceConfig.setActives(actives);
    }

    public Boolean getSent() {
        return referenceConfig.getSent();
    }

    public void setSent(Boolean sent) {
        referenceConfig.setSent(sent);
    }

    @Parameter(escaped = true)
    public String getMock() {
        return referenceConfig.getMock();
    }

    public void setMock(Boolean mock) {
        referenceConfig.setMock(mock);
    }

    public void setMock(String mock) {
        referenceConfig.setMock(mock);
    }

    public String getMerger() {
        return referenceConfig.getMerger();
    }

    public void setMerger(String merger) {
        referenceConfig.setMerger(merger);
    }

    public String getCache() {
        return referenceConfig.getCache();
    }

    public void setCache(String cache) {
        referenceConfig.setCache(cache);
    }

    public String getValidation() {
        return referenceConfig.getValidation();
    }

    public void setValidation(String validation) {
        referenceConfig.setValidation(validation);
    }

    public Map<String, String> getParameters() {
        return referenceConfig.getParameters();
    }

    public void setParameters(Map<String, String> parameters) {
        referenceConfig.setParameters(parameters);
    }

    @Parameter(excluded = true)
    public String getId() {
        return referenceConfig.getId();
    }

    public void setId(String id) {
        referenceConfig.setId(id);
    }
}
