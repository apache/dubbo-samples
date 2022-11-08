package org.apache.dubbo.sample.tri;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.sample.tri.api.PojoGreeter;
import org.apache.dubbo.sample.tri.stub.GreeterImpl;
import org.apache.dubbo.sample.tri.pojo.PojoGreeterImpl;
import org.apache.dubbo.sample.tri.interop.client.GrpcServer;
import org.apache.dubbo.sample.tri.util.EmbeddedZooKeeper;
import org.apache.dubbo.sample.tri.util.TriSampleConstants;

import java.io.IOException;


/**
 * for Integration testing
 */
public class TestServers {

    public static void main(String[] args) throws IOException, InterruptedException {
        new EmbeddedZooKeeper(TriSampleConstants.ZK_PORT, false).start();

        // grpc
        GrpcServer server = new GrpcServer(TriSampleConstants.GRPC_SERVER_PORT);
        server.initialize();
        server.start();

        ServiceConfig<Greeter> pbService = new ServiceConfig<>();
        pbService.setInterface(Greeter.class);
        GreeterImpl greeterImpl = new GreeterImpl("tri-stub");
        pbService.setRef(greeterImpl);


        ServiceConfig<PojoGreeter> wrapService = new ServiceConfig<>();
        wrapService.setInterface(PojoGreeter.class);
        wrapService.setRef(new PojoGreeterImpl());

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(new ApplicationConfig("demo-provider"))
                .registry(new RegistryConfig(TriSampleConstants.ZK_ADDRESS))
                .protocol(new ProtocolConfig(CommonConstants.TRIPLE, TriSampleConstants.SERVER_PORT))
                .service(pbService)
                .service(wrapService)
                .start()
                .await();
    }
}
