package org.apache.dubbo.sample.tri;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.service.GenericService;

import org.apache.dubbo.sample.tri.api.PojoGreeter;
import org.apache.dubbo.sample.tri.util.TriSampleConstants;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TriGenericTest {

    private static GenericService generic;
    private static GenericService longGeneric;
    protected static DubboBootstrap appDubboBootstrap;

    @BeforeClass
    public static void init() {
        ReferenceConfig<GenericService> ref = new ReferenceConfig<>();
        ref.setInterface(PojoGreeter.class.getName());
        ref.setTimeout(3000);
        ref.setProtocol(CommonConstants.TRIPLE);
        ref.setGeneric("true");

        ReferenceConfig<GenericService> ref2 = new ReferenceConfig<>();
        ref2.setInterface(PojoGreeter.class.getName());
        ref2.setTimeout(15000);
        ref2.setProtocol(CommonConstants.TRIPLE);
        ref2.setGeneric("true");
        ref2.setRetries(0);

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        ApplicationConfig applicationConfig = new ApplicationConfig(TriGenericTest.class.getName());
        applicationConfig.setMetadataServicePort(TriSampleConstants.CONSUMER_METADATA_SERVICE_PORT);
        bootstrap.application(applicationConfig)
            .registry(new RegistryConfig(TriSampleConstants.ZK_ADDRESS))
            .reference(ref)
            .reference(ref2)
            .start();
        generic = ref.get();
        longGeneric = ref2.get();

        appDubboBootstrap = bootstrap;
    }

    @Test
    public void greetUnaryRequestVoid() {
        Assert.assertNotNull(generic.$invoke("sayGreeterRequestVoid", new String[0], new Object[0]));
    }

    @Test
    public void greetUnaryResponseVoid() {
        generic.$invoke("greetResponseVoid", new String[]{String.class.getName()},
            new Object[]{"requestVoid"});
    }

    @Test
    public void greetUnary() {
        Assert.assertEquals("hello,unary", generic.$invoke("greet",
            new String[]{String.class.getName()}, new Object[]{"unary"}));
    }

    @Test(expected = RpcException.class)
    public void greetException() {
        generic.$invoke("greetException", new String[]{String.class.getName()},
            new Object[]{"exception"});
    }

    @Test(expected = RpcException.class)
    public void notFoundMethod() {
        generic.$invoke("greetNotExist", new String[]{String.class.getName()},
            new Object[]{"unary long"});
    }

    @Test
    public void greetLong() {
        int len = 2 << 12;
        final String resp = (String) longGeneric.$invoke("greetLong",
            new String[]{int.class.getName()},
            new Object[]{len});
        Assert.assertEquals(len, resp.length());
    }

    @AfterClass
    public static void alterTest() {
        appDubboBootstrap.stop();
        DubboBootstrap.reset();
    }
}
