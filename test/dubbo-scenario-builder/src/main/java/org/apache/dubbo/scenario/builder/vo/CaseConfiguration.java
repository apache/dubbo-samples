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

public class CaseConfiguration {
    private String from;
    private Map<String, String> props;
    private List<String> systemProps;
    private Map<String, ServiceComponent> services;
    private int timeout;
    private String ignoreFor;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Map<String, ServiceComponent> getServices() {
        return services;
    }

    public void setServices(Map<String, ServiceComponent> services) {
        this.services = services;
    }

    public Map<String, String> getProps() {
        return props;
    }

    public void setProps(Map<String, String> props) {
        this.props = props;
    }

    public List<String> getSystemProps() {
        return systemProps;
    }

    public void setSystemProps(List<String> systemProps) {
        this.systemProps = systemProps;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getIgnoreFor() {
        return ignoreFor;
    }

    public void setIgnoreFor(String ignoreFor) {
        this.ignoreFor = ignoreFor;
    }

    @Override
    public String toString() {
        return "CaseConfiguration{" +
                "from='" + from + '\'' +
                ", props=" + props +
                ", systemProps=" + systemProps +
                ", services=" + services +
                ", timeout=" + timeout +
                ", ignoreFor='" + ignoreFor + '\'' +
                '}';
    }
}
