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
import com.alibaba.dubbo.config.MonitorConfig;
import com.alibaba.dubbo.config.RegistryConfig;

import java.util.List;
import java.util.Map;

public class ApplicationConfigDelegate {
    private final ApplicationConfig applicationConfig;

    public ApplicationConfigDelegate() {
        this.applicationConfig = new ApplicationConfig();
    }

    public ApplicationConfigDelegate(String name) {
        this.applicationConfig = new ApplicationConfig(name);
    }

    public String getName() {
        return applicationConfig.getName();
    }

    public void setName(String name) {
        applicationConfig.setName(name);
    }

    public String getVersion() {
        return applicationConfig.getVersion();
    }

    public void setVersion(String version) {
        applicationConfig.setVersion(version);
    }

    public String getOwner() {
        return applicationConfig.getOwner();
    }

    public void setOwner(String owner) {
        applicationConfig.setOwner(owner);
    }

    public String getOrganization() {
        return applicationConfig.getOrganization();
    }

    public void setOrganization(String organization) {
        applicationConfig.setOrganization(organization);
    }

    public String getArchitecture() {
        return applicationConfig.getArchitecture();
    }

    public void setArchitecture(String architecture) {
        applicationConfig.setArchitecture(architecture);
    }

    public String getEnvironment() {
        return applicationConfig.getEnvironment();
    }

    public void setEnvironment(String environment) {
        applicationConfig.setEnvironment(environment);
    }

    public RegistryConfig getRegistry() {
        return applicationConfig.getRegistry();
    }

    public void setRegistry(RegistryConfig registry) {
        applicationConfig.setRegistry(registry);
    }

    public List<RegistryConfig> getRegistries() {
        return applicationConfig.getRegistries();
    }

    public void setRegistries(List<? extends RegistryConfig> registries) {
        applicationConfig.setRegistries(registries);
    }

    public MonitorConfig getMonitor() {
        return applicationConfig.getMonitor();
    }

    public void setMonitor(MonitorConfig monitor) {
        applicationConfig.setMonitor(monitor);
    }

    public void setMonitor(String monitor) {
        applicationConfig.setMonitor(monitor);
    }

    public String getCompiler() {
        return applicationConfig.getCompiler();
    }

    public void setCompiler(String compiler) {
        applicationConfig.setCompiler(compiler);
    }

    public String getLogger() {
        return applicationConfig.getLogger();
    }

    public void setLogger(String logger) {
        applicationConfig.setLogger(logger);
    }

    public Boolean isDefault() {
        return applicationConfig.isDefault();
    }

    public void setDefault(Boolean isDefault) {
        applicationConfig.setDefault(isDefault);
    }

    public String getDumpDirectory() {
        return applicationConfig.getDumpDirectory();
    }

    public void setDumpDirectory(String dumpDirectory) {
        applicationConfig.setDumpDirectory(dumpDirectory);
    }

    public Boolean getQosEnable() {
        return applicationConfig.getQosEnable();
    }

    public void setQosEnable(Boolean qosEnable) {
        applicationConfig.setQosEnable(qosEnable);
    }

    public Integer getQosPort() {
        return applicationConfig.getQosPort();
    }

    public void setQosPort(Integer qosPort) {
        applicationConfig.setQosPort(qosPort);
    }

    public Boolean getQosAcceptForeignIp() {
        return applicationConfig.getQosAcceptForeignIp();
    }

    public void setQosAcceptForeignIp(Boolean qosAcceptForeignIp) {
        applicationConfig.setQosAcceptForeignIp(qosAcceptForeignIp);
    }

    public Map<String, String> getParameters() {
        return applicationConfig.getParameters();
    }

    public void setParameters(Map<String, String> parameters) {
        applicationConfig.setParameters(parameters);
    }

    public String getId() {
        return applicationConfig.getId();
    }

    public void setId(String id) {
        applicationConfig.setId(id);
    }
}
