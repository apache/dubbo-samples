package org.apache.dubbo.samples.merge;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.registry.AddressListener;
import org.apache.dubbo.registry.client.ServiceDiscoveryRegistryDirectory;
import org.apache.dubbo.rpc.cluster.Directory;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Activate
public class MyAddressListener implements AddressListener {
    private final static AtomicInteger addressSize = new AtomicInteger(0);

    @Override
    public List<URL> notify(List<URL> addresses, URL consumerUrl, Directory registryDirectory) {
        if (registryDirectory instanceof ServiceDiscoveryRegistryDirectory) {
            addressSize.set(addresses.size());
        }
        return addresses;
    }

    public static int getAddressSize() {
        return addressSize.get();
    }
}
