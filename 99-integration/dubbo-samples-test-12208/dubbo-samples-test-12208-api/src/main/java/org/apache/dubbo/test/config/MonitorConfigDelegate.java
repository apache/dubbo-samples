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

import com.alibaba.dubbo.config.MonitorConfig;
import com.alibaba.dubbo.config.support.Parameter;

import java.util.Map;

public class MonitorConfigDelegate {
    private final MonitorConfig monitorConfig;

    public MonitorConfigDelegate() {
        this.monitorConfig = new MonitorConfig();
    }

    public MonitorConfigDelegate(String name) {
        this.monitorConfig = new MonitorConfig(name);
    }

    @Parameter(excluded = true)
    public String getAddress() {
        return monitorConfig.getAddress();
    }

    public void setAddress(String address) {
        monitorConfig.setAddress(address);
    }

    @Parameter(excluded = true)
    public String getProtocol() {
        return monitorConfig.getProtocol();
    }

    public void setProtocol(String protocol) {
        monitorConfig.setProtocol(protocol);
    }

    @Parameter(excluded = true)
    public String getUsername() {
        return monitorConfig.getUsername();
    }

    public void setUsername(String username) {
        monitorConfig.setUsername(username);
    }

    @Parameter(excluded = true)
    public String getPassword() {
        return monitorConfig.getPassword();
    }

    public void setPassword(String password) {
        monitorConfig.setPassword(password);
    }

    public String getGroup() {
        return monitorConfig.getGroup();
    }

    public void setGroup(String group) {
        monitorConfig.setGroup(group);
    }

    public String getVersion() {
        return monitorConfig.getVersion();
    }

    public void setVersion(String version) {
        monitorConfig.setVersion(version);
    }

    public Map<String, String> getParameters() {
        return monitorConfig.getParameters();
    }

    public void setParameters(Map<String, String> parameters) {
        monitorConfig.setParameters(parameters);
    }

    public Boolean isDefault() {
        return monitorConfig.isDefault();
    }

    public void setDefault(Boolean isDefault) {
        monitorConfig.setDefault(isDefault);
    }

    public void setInterval(String interval) {
        monitorConfig.setInterval(interval);
    }

    public String getInterval() {
        return monitorConfig.getInterval();
    }

    @Parameter(excluded = true)
    public String getId() {
        return monitorConfig.getId();
    }

    public void setId(String id) {
        monitorConfig.setId(id);
    }
}
