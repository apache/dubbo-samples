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
import com.alibaba.dubbo.config.MethodConfig;
import com.alibaba.dubbo.config.ModuleConfig;
import com.alibaba.dubbo.config.MonitorConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.alibaba.dubbo.config.support.Parameter;

import java.util.List;
import java.util.Map;

public class ServiceConfigDelegate {
    private final ServiceConfig serviceConfig;

    public ServiceConfigDelegate() {
        this.serviceConfig = new ServiceConfig();
    }

    public URL toUrl() {
        return serviceConfig.toUrl();
    }

    public List<URL> toUrls() {
        return serviceConfig.toUrls();
    }

    @Parameter(excluded = true)
    public boolean isExported() {
        return serviceConfig.isExported();
    }

    @Parameter(excluded = true)
    public boolean isUnexported() {
        return serviceConfig.isUnexported();
    }

    public void export() {
        serviceConfig.export();
    }

    public void unexport() {
        serviceConfig.unexport();
    }

    public Class<?> getInterfaceClass() {
        return serviceConfig.getInterfaceClass();
    }

    public void setInterfaceClass(Class interfaceClass) {
        serviceConfig.setInterfaceClass(interfaceClass);
    }

    public String getInterface() {
        return serviceConfig.getInterface();
    }

    public void setInterface(String interfaceName) {
        serviceConfig.setInterface(interfaceName);
    }

    public void setInterface(Class interfaceClass) {
        serviceConfig.setInterface(interfaceClass);
    }

    public Object getRef() {
        return serviceConfig.getRef();
    }

    public void setRef(Object ref) {
        serviceConfig.setRef(ref);
    }

    @Parameter(excluded = true)
    public String getPath() {
        return serviceConfig.getPath();
    }

    public void setPath(String path) {
        serviceConfig.setPath(path);
    }

    public List<MethodConfig> getMethods() {
        return serviceConfig.getMethods();
    }

    public void setMethods(List methods) {
        serviceConfig.setMethods(methods);
    }

    public ProviderConfig getProvider() {
        return serviceConfig.getProvider();
    }

    public void setProvider(ProviderConfig provider) {
        serviceConfig.setProvider(provider);
    }

    public String getGeneric() {
        return serviceConfig.getGeneric();
    }

    public void setGeneric(String generic) {
        serviceConfig.setGeneric(generic);
    }

//    public void setMock(Boolean mock) {
//        serviceConfig.setMock(mock);
//    }

    public void setMock(String mock) {
        serviceConfig.setMock(mock);
    }

    public List<URL> getExportedUrls() {
        return serviceConfig.getExportedUrls();
    }

    @Deprecated
    public List<ProviderConfig> getProviders() {
        return serviceConfig.getProviders();
    }

    @Deprecated
    public void setProviders(List providers) {
        serviceConfig.setProviders(providers);
    }

    @Parameter(excluded = true)
    public String getUniqueServiceName() {
        return serviceConfig.getUniqueServiceName();
    }

    public String getVersion() {
        return serviceConfig.getVersion();
    }

    public String getGroup() {
        return serviceConfig.getGroup();
    }

    public void setVersion(String version) {
        serviceConfig.setVersion(version);
    }

    public void setGroup(String group) {
        serviceConfig.setGroup(group);
    }

    public Integer getDelay() {
        return serviceConfig.getDelay();
    }

    public void setDelay(Integer delay) {
        serviceConfig.setDelay(delay);
    }

    public Boolean getExport() {
        return serviceConfig.getExport();
    }

    public void setExport(Boolean export) {
        serviceConfig.setExport(export);
    }

    public Integer getWeight() {
        return serviceConfig.getWeight();
    }

    public void setWeight(Integer weight) {
        serviceConfig.setWeight(weight);
    }

    @Parameter(escaped = true)
    public String getDocument() {
        return serviceConfig.getDocument();
    }

    public void setDocument(String document) {
        serviceConfig.setDocument(document);
    }

    public String getToken() {
        return serviceConfig.getToken();
    }

    public void setToken(String token) {
        serviceConfig.setToken(token);
    }

    public void setToken(Boolean token) {
        serviceConfig.setToken(token);
    }

    public Boolean isDeprecated() {
        return serviceConfig.isDeprecated();
    }

    public void setDeprecated(Boolean deprecated) {
        serviceConfig.setDeprecated(deprecated);
    }

    public Boolean isDynamic() {
        return serviceConfig.isDynamic();
    }

    public void setDynamic(Boolean dynamic) {
        serviceConfig.setDynamic(dynamic);
    }

    public List<ProtocolConfig> getProtocols() {
        return serviceConfig.getProtocols();
    }

    public void setProtocols(List<? extends ProtocolConfig> protocols) {
        serviceConfig.setProtocols(protocols);
    }

    public ProtocolConfig getProtocol() {
        return serviceConfig.getProtocol();
    }

    public void setProtocol(ProtocolConfig protocol) {
        serviceConfig.setProtocol(protocol);
    }

    public String getAccesslog() {
        return serviceConfig.getAccesslog();
    }

    public void setAccesslog(String accesslog) {
        serviceConfig.setAccesslog(accesslog);
    }

    public void setAccesslog(Boolean accesslog) {
        serviceConfig.setAccesslog(accesslog);
    }

    public Integer getExecutes() {
        return serviceConfig.getExecutes();
    }

    public void setExecutes(Integer executes) {
        serviceConfig.setExecutes(executes);
    }

    @Parameter(key = "service.filter", append = true)
    public String getFilter() {
        return serviceConfig.getFilter();
    }

    @Parameter(key = "exporter.listener", append = true)
    public String getListener() {
        return serviceConfig.getListener();
    }

    public void setListener(String listener) {
        serviceConfig.setListener(listener);
    }

    public Boolean isRegister() {
        return serviceConfig.isRegister();
    }

    public void setRegister(Boolean register) {
        serviceConfig.setRegister(register);
    }

    public Integer getWarmup() {
        return serviceConfig.getWarmup();
    }

    public void setWarmup(Integer warmup) {
        serviceConfig.setWarmup(warmup);
    }

    public String getSerialization() {
        return serviceConfig.getSerialization();
    }

    public void setSerialization(String serialization) {
        serviceConfig.setSerialization(serialization);
    }

    @Parameter(key = "dubbo.tag")
    public String getTag() {
        return serviceConfig.getTag();
    }

    public void setTag(String tag) {
        serviceConfig.setTag(tag);
    }

    @Deprecated
    public String getLocal() {
        return serviceConfig.getLocal();
    }

    @Deprecated
    public void setLocal(Boolean local) {
        serviceConfig.setLocal(local);
    }

    @Deprecated
    public void setLocal(String local) {
        serviceConfig.setLocal(local);
    }

    public String getStub() {
        return serviceConfig.getStub();
    }

    public void setStub(Boolean stub) {
        serviceConfig.setStub(stub);
    }

    public void setStub(String stub) {
        serviceConfig.setStub(stub);
    }

    public String getCluster() {
        return serviceConfig.getCluster();
    }

    public void setCluster(String cluster) {
        serviceConfig.setCluster(cluster);
    }

    public String getProxy() {
        return serviceConfig.getProxy();
    }

    public void setProxy(String proxy) {
        serviceConfig.setProxy(proxy);
    }

    public Integer getConnections() {
        return serviceConfig.getConnections();
    }

    public void setConnections(Integer connections) {
        serviceConfig.setConnections(connections);
    }

    public void setFilter(String filter) {
        serviceConfig.setFilter(filter);
    }

    public String getLayer() {
        return serviceConfig.getLayer();
    }

    public void setLayer(String layer) {
        serviceConfig.setLayer(layer);
    }

    public ApplicationConfig getApplication() {
        return serviceConfig.getApplication();
    }

    public void setApplication(ApplicationConfig application) {
        serviceConfig.setApplication(application);
    }

    public ModuleConfig getModule() {
        return serviceConfig.getModule();
    }

    public void setModule(ModuleConfig module) {
        serviceConfig.setModule(module);
    }

    public RegistryConfig getRegistry() {
        return serviceConfig.getRegistry();
    }

    public void setRegistry(RegistryConfig registry) {
        serviceConfig.setRegistry(registry);
    }

    public List<RegistryConfig> getRegistries() {
        return serviceConfig.getRegistries();
    }

    public void setRegistries(List<? extends RegistryConfig> registries) {
        serviceConfig.setRegistries(registries);
    }

    public MonitorConfig getMonitor() {
        return serviceConfig.getMonitor();
    }

    public void setMonitor(String monitor) {
        serviceConfig.setMonitor(monitor);
    }

    public void setMonitor(MonitorConfig monitor) {
        serviceConfig.setMonitor(monitor);
    }

    public String getOwner() {
        return serviceConfig.getOwner();
    }

    public void setOwner(String owner) {
        serviceConfig.setOwner(owner);
    }

    public Integer getCallbacks() {
        return serviceConfig.getCallbacks();
    }

    public void setCallbacks(Integer callbacks) {
        serviceConfig.setCallbacks(callbacks);
    }

    public String getOnconnect() {
        return serviceConfig.getOnconnect();
    }

    public void setOnconnect(String onconnect) {
        serviceConfig.setOnconnect(onconnect);
    }

    public String getOndisconnect() {
        return serviceConfig.getOndisconnect();
    }

    public void setOndisconnect(String ondisconnect) {
        serviceConfig.setOndisconnect(ondisconnect);
    }

    public String getScope() {
        return serviceConfig.getScope();
    }

    public void setScope(String scope) {
        serviceConfig.setScope(scope);
    }

    public Integer getForks() {
        return serviceConfig.getForks();
    }

    public void setForks(Integer forks) {
        serviceConfig.setForks(forks);
    }

    public Integer getTimeout() {
        return serviceConfig.getTimeout();
    }

    public void setTimeout(Integer timeout) {
        serviceConfig.setTimeout(timeout);
    }

    public Integer getRetries() {
        return serviceConfig.getRetries();
    }

    public void setRetries(Integer retries) {
        serviceConfig.setRetries(retries);
    }

    public String getLoadbalance() {
        return serviceConfig.getLoadbalance();
    }

    public void setLoadbalance(String loadbalance) {
        serviceConfig.setLoadbalance(loadbalance);
    }

    public Boolean isAsync() {
        return serviceConfig.isAsync();
    }

    public void setAsync(Boolean async) {
        serviceConfig.setAsync(async);
    }

    public Integer getActives() {
        return serviceConfig.getActives();
    }

    public void setActives(Integer actives) {
        serviceConfig.setActives(actives);
    }

    public Boolean getSent() {
        return serviceConfig.getSent();
    }

    public void setSent(Boolean sent) {
        serviceConfig.setSent(sent);
    }

    @Parameter(escaped = true)
    public String getMock() {
        return serviceConfig.getMock();
    }

    public String getMerger() {
        return serviceConfig.getMerger();
    }

    public void setMerger(String merger) {
        serviceConfig.setMerger(merger);
    }

    public String getCache() {
        return serviceConfig.getCache();
    }

    public void setCache(String cache) {
        serviceConfig.setCache(cache);
    }

    public String getValidation() {
        return serviceConfig.getValidation();
    }

    public void setValidation(String validation) {
        serviceConfig.setValidation(validation);
    }

    public Map<String, String> getParameters() {
        return serviceConfig.getParameters();
    }

    public void setParameters(Map<String, String> parameters) {
        serviceConfig.setParameters(parameters);
    }

    @Parameter(excluded = true)
    public String getId() {
        return serviceConfig.getId();
    }

    public void setId(String id) {
        serviceConfig.setId(id);
    }
}
