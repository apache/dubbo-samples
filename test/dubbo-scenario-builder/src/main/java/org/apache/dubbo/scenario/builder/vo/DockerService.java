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
import java.util.Map;

public class DockerService {
    private String name;
    private String imageName;
    // docker-compose build dir
    private String build;
    private String hostname;
    private boolean removeOnExit;
    private List<String> links;
    private List<String> expose;
    private List<String> ports;
    private List<String> entrypoint;
    private Map<String, String> healthcheck;
    private Map<String, String> depends_on;
    private List<String> environment;
    private List<String> volumes;
    private List<String> volumes_from;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public boolean isRemoveOnExit() {
        return removeOnExit;
    }

    public void setRemoveOnExit(boolean removeOnExit) {
        this.removeOnExit = removeOnExit;
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

    public Map<String, String> getHealthcheck() {
        return healthcheck;
    }

    public void setHealthcheck(Map<String, String> healthcheck) {
        this.healthcheck = healthcheck;
    }

    public Map<String, String> getDepends_on() {
        return depends_on;
    }

    public void setDepends_on(Map<String, String> depends_on) {
        this.depends_on = depends_on;
    }

    public List<String> getEnvironment() {
        return environment;
    }

    public void setEnvironment(List<String> environment) {
        this.environment = environment;
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

    public List<String> getPorts() {
        return ports;
    }

    public void setPorts(List<String> ports) {
        this.ports = ports;
    }

    @Override
    public String toString() {
        return "DockerService{" +
                "name='" + name + '\'' +
                ", imageName='" + imageName + '\'' +
                ", build='" + build + '\'' +
                ", hostname='" + hostname + '\'' +
                ", removeOnExit=" + removeOnExit +
                ", links=" + links +
                ", expose=" + expose +
                ", ports=" + ports +
                ", entrypoint=" + entrypoint +
                ", healthcheck=" + healthcheck +
                ", depends_on=" + depends_on +
                ", environment=" + environment +
                ", volumes=" + volumes +
                ", volumes_from=" + volumes_from +
                '}';
    }
}
