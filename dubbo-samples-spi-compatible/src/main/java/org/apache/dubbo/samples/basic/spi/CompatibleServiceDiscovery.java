/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.dubbo.samples.basic.spi;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.registry.client.ServiceDiscovery;
import org.apache.dubbo.registry.client.ServiceInstance;

import java.util.Set;

public class CompatibleServiceDiscovery implements ServiceDiscovery {

    protected ServiceInstance serviceInstance;

    protected URL url;

    //@Override
    public ServiceInstance getLocalInstance() {
        return serviceInstance;
    }

    @Override
    public void register(ServiceInstance serviceInstance) throws RuntimeException {
        this.serviceInstance = serviceInstance;
    }

    @Override
    public void update(ServiceInstance serviceInstance) throws RuntimeException {
        this.serviceInstance = serviceInstance;
    }

    @Override
    public void initialize(URL registryURL) throws Exception {
        url = registryURL;
    }

    @Override
    public void destroy() throws Exception {

    }

    //For compatible, 3.0 service discovery need isDestroy to judgement.
    public boolean isDestroy() {
        return false;
    }

    @Override
    public void unregister(ServiceInstance serviceInstance) throws RuntimeException {

    }

    @Override
    public Set<String> getServices() {
        return null;
    }

    @Override
    public URL getUrl() {
        return url;
    }
}
