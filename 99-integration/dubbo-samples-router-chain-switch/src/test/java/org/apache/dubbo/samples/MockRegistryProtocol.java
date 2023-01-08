package org.apache.dubbo.samples;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.registry.Registry;
import org.apache.dubbo.registry.integration.DynamicDirectory;
import org.apache.dubbo.registry.integration.InterfaceCompatibleRegistryProtocol;
import org.apache.dubbo.rpc.cluster.Cluster;
import org.apache.dubbo.rpc.cluster.ClusterInvoker;

public class MockRegistryProtocol extends InterfaceCompatibleRegistryProtocol {

    @Override
    public <T> ClusterInvoker<T> getServiceDiscoveryInvoker(Cluster cluster, Registry registry, Class<T> type, URL url) {
        DynamicDirectory<T> directory = new MockServiceDiscoveryRegistryDirectory(type, url);
        return doCreateInvoker(directory, cluster, registry, type);
    }

    @Override
    public <T> ClusterInvoker<T> getInvoker(Cluster cluster, Registry registry, Class<T> type, URL url) {
        DynamicDirectory<T> directory = new MockRegistryDirectory(type, url);
        return doCreateInvoker(directory, cluster, registry, type);
    }
}
