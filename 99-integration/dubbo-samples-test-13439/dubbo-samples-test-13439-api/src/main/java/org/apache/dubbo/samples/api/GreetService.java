package org.apache.dubbo.samples.api;

public interface GreetService {

    String greet(String msg);

    String HOST = System.getProperty("nacos.address") + ":" + System.getProperty("nacos.port");

    String NACOS_ADDR = "nacos://"+HOST;

    String NACOS_NAMESPACE_CONSOLE_PATH = "http://"+HOST+"/nacos/v1/console/namespaces";

    String NACOS_NAMESPACE_NAME_1 = "DUBBO-TEST-1";

    String NACOS_NAMESPACE_NAME_2 = "DUBBO-TEST-2";

    String NACOS_NAMESPACE_1_PATH = NACOS_ADDR + "?namespace="+NACOS_NAMESPACE_NAME_1;

    String NACOS_NAMESPACE_2_PATH = NACOS_ADDR + "?namespace="+NACOS_NAMESPACE_NAME_2;

    int PORT = 10001;
}
