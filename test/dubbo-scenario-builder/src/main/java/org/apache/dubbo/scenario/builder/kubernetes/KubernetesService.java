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

package org.apache.dubbo.scenario.builder.kubernetes;

import java.util.List;
import java.util.Map;

public class KubernetesService {

    private String type;
    private String name;

    private String imageName;

    private String hostname;

    private List<String> expose;

    private Map<String, String> healthcheck;

    private List<String> healthcheckExec;

    private Map<String,String> volumesMounts;

    private Map<String,String> volumes;


    private Map<String,String> environment;

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

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public List<String> getExpose() {
        return expose;
    }

    public void setExpose(List<String> expose) {
        this.expose = expose;
    }

    public Map<String, String> getHealthcheck() {
        return healthcheck;
    }

    public void setHealthcheck(Map<String, String> healthcheck) {
        this.healthcheck = healthcheck;
    }

    public List<String> getHealthcheckExec() {
        return healthcheckExec;
    }

    public void setHealthcheckExec(List<String> healthcheckExec) {
        this.healthcheckExec = healthcheckExec;
    }

    public Map<String, String> getVolumesMounts() {
        return volumesMounts;
    }

    public void setVolumesMounts(Map<String, String> volumesMounts) {
        this.volumesMounts = volumesMounts;
    }

    public Map<String, String> getVolumes() {
        return volumes;
    }

    public void setVolumes(Map<String, String> volumes) {
        this.volumes = volumes;
    }

    public Map<String, String> getEnvironment() {
        return environment;
    }

    public void setEnvironment(Map<String, String> environment) {
        this.environment = environment;
    }

    @Override
    public String toString() {
        return "KubernetesDeployment{" +
                "name='" + name + '\'' +
                ", imageName='" + imageName + '\'' +
                ", hostname='" + hostname + '\'' +
                ", expose=" + expose +
                ", healthcheck=" + healthcheck +
                ", healthcheckExec=" + healthcheckExec +
                ", volumesMounts=" + volumesMounts +
                ", volumes=" + volumes +
                '}';
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
