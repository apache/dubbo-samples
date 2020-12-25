/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.dubbo.scenario.builder.vo;


import java.util.List;

public class ServiceComponent {
    private String image;
    private String hostname;
    private String version;
    private boolean removeOnExit = false;
    private List<String> links;
    private List<String> expose;
    private List<String> ports;
    private List<String> entrypoint;
    private List<String> environment;
    private List<String> volumes;
    private List<String> volumes_from;
    private List<String> depends_on;
    private List<String> healthcheck;

    // app attrs
    private String type;
    private String basedir;
    private String mainClass;
    private List<String> waitPortsBeforeRun;
    private List<String> checkPortsAfterRun;
    private String checkLog;
    private int checkTimeout;
    private List<String> tests;
    private List<String> systemProps;
    private List<String> jvmFlags;
    private JavaDebugOption javaDebug;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getVersion() {
        return version;
    }

    public boolean isRemoveOnExit() {
        return removeOnExit;
    }

    public void setRemoveOnExit(boolean removeOnExit) {
        this.removeOnExit = removeOnExit;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }

    public List<String> getExpose() {
        return expose;
    }

    public void setExpose(List<String> expose) {
        this.expose = expose;
    }

    public List<String> getEntrypoint() {
        return entrypoint;
    }

    public void setEntrypoint(List<String> entrypoint) {
        this.entrypoint = entrypoint;
    }

    public List<String> getEnvironment() {
        return environment;
    }

    public void setEnvironment(List<String> environment) {
        this.environment = environment;
    }

    public List<String> getDepends_on() {
        return depends_on;
    }

    public void setDepends_on(List<String> depends_on) {
        this.depends_on = depends_on;
    }

    public List<String> getHealthcheck() {
        return healthcheck;
    }

    public void setHealthcheck(List<String> healthcheck) {
        this.healthcheck = healthcheck;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBasedir() {
        return basedir;
    }

    public void setBasedir(String basedir) {
        this.basedir = basedir;
    }

    public String getMainClass() {
        return mainClass;
    }

    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }

    public List<String> getCheckPortsAfterRun() {
        return checkPortsAfterRun;
    }

    public void setCheckPortsAfterRun(List<String> checkPortsAfterRun) {
        this.checkPortsAfterRun = checkPortsAfterRun;
    }

    public List<String> getWaitPortsBeforeRun() {
        return waitPortsBeforeRun;
    }

    public void setWaitPortsBeforeRun(List<String> waitPortsBeforeRun) {
        this.waitPortsBeforeRun = waitPortsBeforeRun;
    }

    public String getCheckLog() {
        return checkLog;
    }

    public void setCheckLog(String checkLog) {
        this.checkLog = checkLog;
    }

    public int getCheckTimeout() {
        return checkTimeout;
    }

    public void setCheckTimeout(int checkTimeout) {
        this.checkTimeout = checkTimeout;
    }

    public List<String> getTests() {
        return tests;
    }

    public void setTests(List<String> tests) {
        this.tests = tests;
    }

    public List<String> getSystemProps() {
        return systemProps;
    }

    public void setSystemProps(List<String> systemProps) {
        this.systemProps = systemProps;
    }

    public List<String> getJvmFlags() {
        return jvmFlags;
    }

    public void setJvmFlags(List<String> jvmFlags) {
        this.jvmFlags = jvmFlags;
    }

    public List<String> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<String> volumes) {
        this.volumes = volumes;
    }

    public List<String> getVolumes_from() {
        return volumes_from;
    }

    public void setVolumes_from(List<String> volumes_from) {
        this.volumes_from = volumes_from;
    }

    public JavaDebugOption getJavaDebug() {
        return javaDebug;
    }

    public void setJavaDebug(JavaDebugOption javaDebug) {
        this.javaDebug = javaDebug;
    }

    public List<String> getPorts() {
        return ports;
    }

    public void setPorts(List<String> ports) {
        this.ports = ports;
    }
}
