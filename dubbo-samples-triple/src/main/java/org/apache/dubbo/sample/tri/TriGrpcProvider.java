package org.apache.dubbo.sample.tri;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.sample.tri.grpc.GrpcProvider;
import org.apache.dubbo.sample.tri.service.impl.PbGreeterImpl;
import org.apache.dubbo.sample.tri.service.PbGreeterManual;
import org.apache.dubbo.sample.tri.service.WrapGreeter;
import org.apache.dubbo.sample.tri.service.impl.WrapGreeterImpl;

import java.io.IOException;


/**
 * for Integration testing
 */
public class TriGrpcProvider {

    public static void main(String[] args) throws IOException, InterruptedException {
        new Thread(()->{
            try {
                GrpcProvider.main(new String[0]);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new EmbeddedZooKeeper(TriSampleConstants.ZK_PORT, false).start();

        ServiceConfig<PbGreeter> pbService = new ServiceConfig<>();
        pbService.setInterface(PbGreeter.class);
        PbGreeterImpl greeterImpl = new PbGreeterImpl();
        pbService.setRef(greeterImpl);

        ServiceConfig<PbGreeterManual> pbManualService = new ServiceConfig<>();
        pbManualService.setInterface(PbGreeterManual.class);
        pbManualService.setRef(new PbGreeterImpl());

        ServiceConfig<WrapGreeter> wrapService = new ServiceConfig<>();
        wrapService.setInterface(WrapGreeter.class);
        wrapService.setRef(new WrapGreeterImpl());

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(new ApplicationConfig("demo-provider"))
                .registry(new RegistryConfig(TriSampleConstants.ZK_ADDRESS))
                .protocol(new ProtocolConfig(CommonConstants.TRIPLE, TriSampleConstants.SERVER_PORT))
                .service(pbService)
                .service(pbManualService)
                .service(wrapService)
                .start()
                .await();
    }
}
