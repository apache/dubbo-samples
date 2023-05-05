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
import com.alibaba.dubbo.config.support.Parameter;

public class ArgumentConfigDelegate {
    private final ArgumentConfig argumentConfig;

    public ArgumentConfigDelegate() {
        this.argumentConfig = new ArgumentConfig();
    }

    @Parameter(excluded = true)
    public Integer getIndex() {
        return argumentConfig.getIndex();
    }

    public void setIndex(Integer index) {
        argumentConfig.setIndex(index);
    }

    @Parameter(excluded = true)
    public String getType() {
        return argumentConfig.getType();
    }

    public void setType(String type) {
        argumentConfig.setType(type);
    }

    public void setCallback(Boolean callback) {
        argumentConfig.setCallback(callback);
    }

    public Boolean isCallback() {
        return argumentConfig.isCallback();
    }
}
