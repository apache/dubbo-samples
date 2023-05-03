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

import com.alibaba.dubbo.config.ModuleConfig;
import com.alibaba.dubbo.config.MonitorConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.support.Parameter;

import java.util.List;

public class ModuleConfigDelegate {
    private final ModuleConfig moduleConfig;

    public ModuleConfigDelegate() {
        this.moduleConfig = new ModuleConfig();
    }

    public ModuleConfigDelegate(String name) {
        this.moduleConfig = new ModuleConfig(name);
    }

    @Parameter(key = "module", required = true)
    public String getName() {

        return moduleConfig.getName();
    }

    public void setName(String name) {
        moduleConfig.setName(name);
    }

    @Parameter(key = "module.version")
    public String getVersion() {
        return moduleConfig.getVersion();
    }

    public void setVersion(String version) {
        moduleConfig.setVersion(version);
    }

    public String getOwner() {
        return moduleConfig.getOwner();
    }

    public void setOwner(String owner) {
        moduleConfig.setOwner(owner);
    }

    public String getOrganization() {
        return moduleConfig.getOrganization();
    }

    public void setOrganization(String organization) {
        moduleConfig.setOrganization(organization);
    }

    public RegistryConfig getRegistry() {
        return moduleConfig.getRegistry();
    }

    public void setRegistry(RegistryConfig registry) {
        moduleConfig.setRegistry(registry);
    }

    public List<RegistryConfig> getRegistries() {
        return moduleConfig.getRegistries();
    }

    public void setRegistries(List<? extends RegistryConfig> registries) {
        moduleConfig.setRegistries(registries);
    }

    public MonitorConfig getMonitor() {
        return moduleConfig.getMonitor();
    }

    public void setMonitor(String monitor) {
        moduleConfig.setMonitor(monitor);
    }

    public void setMonitor(MonitorConfig monitor) {
        moduleConfig.setMonitor(monitor);
    }

    public Boolean isDefault() {
        return moduleConfig.isDefault();
    }

    public void setDefault(Boolean isDefault) {
        moduleConfig.setDefault(isDefault);
    }

    @Parameter(excluded = true)
    public String getId() {
        return moduleConfig.getId();
    }

    public void setId(String id) {
        moduleConfig.setId(id);
    }
}
