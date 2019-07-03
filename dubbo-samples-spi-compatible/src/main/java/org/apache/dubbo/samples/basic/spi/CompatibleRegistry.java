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

import org.apache.dubbo.registry.zookeeper.ZookeeperRegistryFactory;
import org.apache.dubbo.remoting.zookeeper.ZookeeperTransporter;
import org.apache.dubbo.remoting.zookeeper.curator.CuratorZookeeperTransporter;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.registry.NotifyListener;
import com.alibaba.dubbo.registry.Registry;

import java.util.List;
import java.util.stream.Collectors;

public class CompatibleRegistry implements Registry {

    private URL url;
    private org.apache.dubbo.registry.Registry delegate;

    public CompatibleRegistry(URL url) {
        this.url = url;
        ZookeeperTransporter transporter = new CuratorZookeeperTransporter();
        ZookeeperRegistryFactory registryFactory = new ZookeeperRegistryFactory();
        registryFactory.setZookeeperTransporter(transporter);
        delegate = registryFactory.createRegistry(url.setProtocol("zookeeper"));
    }

    @Override
    public URL getUrl() {
        return url;
    }

    @Override
    public boolean isAvailable() {
        return delegate.isAvailable();
    }

    @Override
    public void destroy() {
        delegate.destroy();
    }

    @Override
    public void register(URL url) {
        System.out.println("Registering url to Compatible Registry......");
        delegate.register(url);
    }

    @Override
    public void unregister(URL url) {
        System.out.println("Unregistering url from Compatible Registry......");
        delegate.unregister(url);
    }

    @Override
    public void subscribe(URL url, NotifyListener notifyListener) {
        System.out.println("Subscribing url from Compatible Registry......");
        delegate.subscribe(url, new NotifyListener.ReverseCompatibleNotifyListener(notifyListener));
    }

    @Override
    public void unsubscribe(URL url, NotifyListener notifyListener) {
        System.out.println("Unsubscribing url from Compatible Registry......");
        delegate.subscribe(url, new NotifyListener.ReverseCompatibleNotifyListener(notifyListener));
    }

    @Override
    public List<URL> lookup(URL url) {
        return delegate.lookup(url).stream().map(URL::new).collect(Collectors.toList());
    }
}
