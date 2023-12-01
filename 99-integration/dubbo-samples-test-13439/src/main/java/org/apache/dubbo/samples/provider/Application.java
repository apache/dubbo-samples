package org.apache.dubbo.samples.provider;

import org.apache.dubbo.config.MetadataReportConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.ProviderConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.samples.api.GreetService;

public class Application {


//    private static final String NACOS_ADDR = "nacos://localhost:8848";
//
//    private static final String NACOS_NAMESPACE_1 = NACOS_ADDR + "?namespace=DUBBO-1";
//
//    private static final String NACOS_NAMESPACE_2 = NACOS_ADDR + "?namespace=DUBBO-2";


    private static final String NACOS_ADDR = "nacos://10.21.32.98:8848";

    private static final String NACOS_NAMESPACE_1 = NACOS_ADDR + "?namespace=DUBBO-1";

    private static final String NACOS_NAMESPACE_2 = NACOS_ADDR + "?namespace=DUBBO-2";

    public static void main(String[] args) {

        ServiceConfig<GreetService> serviceConfig = new ServiceConfig<GreetService>();
        serviceConfig.setRef(new GreetServiceImpl());

        DubboBootstrap bootstrap = DubboBootstrap.getInstance()
                .application("GreetApplication")
                .registry(new RegistryConfig(NACOS_ADDR))
                .metadataReport(new MetadataReportConfig(NACOS_NAMESPACE_1))
                .metadataReport(new MetadataReportConfig(NACOS_NAMESPACE_2))
                .protocol(new ProtocolConfig("tri"))
                .service(serviceConfig);
        bootstrap.start();

    }

}
