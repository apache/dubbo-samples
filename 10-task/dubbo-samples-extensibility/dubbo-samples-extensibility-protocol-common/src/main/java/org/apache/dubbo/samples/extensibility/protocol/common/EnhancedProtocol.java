package org.apache.dubbo.samples.extensibility.protocol.common;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.*;
import org.apache.dubbo.rpc.model.FrameworkModel;
import org.apache.dubbo.rpc.protocol.tri.TripleProtocol;

import java.util.List;

public class EnhancedProtocol implements Protocol {

    public EnhancedProtocol(FrameworkModel frameworkModel) {
        this.protocol = new TripleProtocol(frameworkModel);
    }

    private final Protocol protocol;

    @Override
    public int getDefaultPort() {
        return this.protocol.getDefaultPort();
    }

    @Override
    public <T> Exporter<T> export(Invoker<T> invoker) throws RpcException {
        // do something
        //invoker.getUrl().setProtocol("tri");
        return this.protocol.export(invoker);
    }

    @Override
    public <T> Invoker<T> refer(Class<T> type, URL url) throws RpcException {
        // do something
        //url.setProtocol("tri");
        return this.protocol.refer(type, url);
    }

    @Override
    public void destroy() {
        this.protocol.destroy();
    }

    @Override
    public List<ProtocolServer> getServers() {
        return protocol.getServers();
    }
}
