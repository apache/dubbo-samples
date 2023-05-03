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

import com.alibaba.dubbo.config.ArgumentConfig;
import com.alibaba.dubbo.config.MethodConfig;
import com.alibaba.dubbo.config.support.Parameter;

import java.util.List;
import java.util.Map;

public class MethodConfigDelegate {
    private final MethodConfig methodConfig;

    public MethodConfigDelegate() {
        this.methodConfig = new MethodConfig();
    }

    @Parameter(excluded = true)
    public String getName() {
        return methodConfig.getName();
    }

    public void setName(String name) {
        methodConfig.setName(name);
    }

    public Integer getStat() {
        return methodConfig.getStat();
    }

    @Deprecated
    public void setStat(Integer stat) {
        methodConfig.setStat(stat);
    }

    @Deprecated
    public Boolean isRetry() {
        return methodConfig.isRetry();
    }

    @Deprecated
    public void setRetry(Boolean retry) {
        methodConfig.setRetry(retry);
    }

    @Deprecated
    public Boolean isReliable() {
        return methodConfig.isReliable();
    }

    @Deprecated
    public void setReliable(Boolean reliable) {
        methodConfig.setReliable(reliable);
    }

    public Integer getExecutes() {
        return methodConfig.getExecutes();
    }

    public void setExecutes(Integer executes) {
        methodConfig.setExecutes(executes);
    }

    public Boolean getDeprecated() {
        return methodConfig.getDeprecated();
    }

    public void setDeprecated(Boolean deprecated) {
        methodConfig.setDeprecated(deprecated);
    }

    public List<ArgumentConfig> getArguments() {
        return methodConfig.getArguments();
    }

    public void setArguments(List<? extends ArgumentConfig> arguments) {
        methodConfig.setArguments(arguments);
    }

    public Boolean getSticky() {
        return methodConfig.getSticky();
    }

    public void setSticky(Boolean sticky) {
        methodConfig.setSticky(sticky);
    }

    @Parameter(key = "onreturn.instance", excluded = true, attribute = true)
    public Object getOnreturn() {
        return methodConfig.getOnreturn();
    }

    public void setOnreturn(Object onreturn) {
        methodConfig.setOnreturn(onreturn);
    }

    @Parameter(key = "onreturn.method", excluded = true, attribute = true)
    public String getOnreturnMethod() {
        return methodConfig.getOnreturnMethod();
    }

    public void setOnreturnMethod(String onreturnMethod) {
        methodConfig.setOnreturnMethod(onreturnMethod);
    }

    @Parameter(key = "onthrow.instance", excluded = true, attribute = true)
    public Object getOnthrow() {
        return methodConfig.getOnthrow();
    }

    public void setOnthrow(Object onthrow) {
        methodConfig.setOnthrow(onthrow);
    }

    @Parameter(key = "onthrow.method", excluded = true, attribute = true)
    public String getOnthrowMethod() {
        return methodConfig.getOnthrowMethod();
    }

    public void setOnthrowMethod(String onthrowMethod) {
        methodConfig.setOnthrowMethod(onthrowMethod);
    }

    @Parameter(key = "oninvoke.instance", excluded = true, attribute = true)
    public Object getOninvoke() {
        return methodConfig.getOninvoke();
    }

    public void setOninvoke(Object oninvoke) {
        methodConfig.setOninvoke(oninvoke);
    }

    @Parameter(key = "oninvoke.method", excluded = true, attribute = true)
    public String getOninvokeMethod() {
        return methodConfig.getOninvokeMethod();
    }

    public void setOninvokeMethod(String oninvokeMethod) {
        methodConfig.setOninvokeMethod(oninvokeMethod);
    }

    public Boolean isReturn() {
        return methodConfig.isReturn();
    }

    public void setReturn(Boolean isReturn) {
        methodConfig.setReturn(isReturn);
    }

    public Integer getForks() {
        return methodConfig.getForks();
    }

    public void setForks(Integer forks) {
        methodConfig.setForks(forks);
    }

    public Integer getTimeout() {
        return methodConfig.getTimeout();
    }

    public void setTimeout(Integer timeout) {
        methodConfig.setTimeout(timeout);
    }

    public Integer getRetries() {
        return methodConfig.getRetries();
    }

    public void setRetries(Integer retries) {
        methodConfig.setRetries(retries);
    }

    public String getLoadbalance() {
        return methodConfig.getLoadbalance();
    }

    public void setLoadbalance(String loadbalance) {
        methodConfig.setLoadbalance(loadbalance);
    }

    public Boolean isAsync() {
        return methodConfig.isAsync();
    }

    public void setAsync(Boolean async) {
        methodConfig.setAsync(async);
    }

    public Integer getActives() {
        return methodConfig.getActives();
    }

    public void setActives(Integer actives) {
        methodConfig.setActives(actives);
    }

    public Boolean getSent() {
        return methodConfig.getSent();
    }

    public void setSent(Boolean sent) {
        methodConfig.setSent(sent);
    }

    @Parameter(escaped = true)
    public String getMock() {
        return methodConfig.getMock();
    }

    public void setMock(Boolean mock) {
        methodConfig.setMock(mock);
    }

    public void setMock(String mock) {
        methodConfig.setMock(mock);
    }

    public String getMerger() {
        return methodConfig.getMerger();
    }

    public void setMerger(String merger) {
        methodConfig.setMerger(merger);
    }

    public String getCache() {
        return methodConfig.getCache();
    }

    public void setCache(String cache) {
        methodConfig.setCache(cache);
    }

    public String getValidation() {
        return methodConfig.getValidation();
    }

    public void setValidation(String validation) {
        methodConfig.setValidation(validation);
    }

    public Map<String, String> getParameters() {
        return methodConfig.getParameters();
    }

    public void setParameters(Map<String, String> parameters) {
        methodConfig.setParameters(parameters);
    }

    @Parameter(excluded = true)
    public String getId() {
        return methodConfig.getId();
    }

    public void setId(String id) {
        methodConfig.setId(id);
    }
}
