package org.apache.dubbo.sample.tri;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.service.GenericService;

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
        ref.setInterface("org.apache.dubbo.sample.tri.service.WrapGreeter");
        ref.setCheck(false);
        ref.setTimeout(3000);
        ref.setProtocol(CommonConstants.TRIPLE);
        ref.setGeneric("true");
        ref.setLazy(true);

        ReferenceConfig<GenericService> ref2 = new ReferenceConfig<>();
        ref2.setInterface("org.apache.dubbo.sample.tri.service.WrapGreeter");
        ref2.setCheck(false);
        ref2.setTimeout(15000);
        ref2.setProtocol(CommonConstants.TRIPLE);
        ref2.setGeneric("true");
        ref2.setRetries(0);
        ref2.setLazy(true);


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
    public void sayHelloUnaryRequestVoid() {
        Assert.assertNotNull(generic.$invoke("sayHelloRequestVoid", new String[0], new Object[0]));
    }

    @Test
    public void sayHelloUnaryResponseVoid() {
        generic.$invoke("sayHelloResponseVoid", new String[]{String.class.getName()},
                new Object[]{"requestVoid"});
    }

    @Test
    public void sayHelloUnary() {
        Assert.assertEquals("hello,unary", generic.$invoke("sayHello",
                new String[]{String.class.getName()}, new Object[]{"unary"}));
    }

    @Test(expected = RpcException.class)
    public void sayHelloException() {
        generic.$invoke("sayHelloException", new String[]{String.class.getName()}, new Object[]{"exception"});
    }

    @Test(expected = RpcException.class)
    public void notFoundMethod() {
        generic.$invoke("sayHelloNotExist", new String[]{String.class.getName()}, new Object[]{"unary long"});
    }

    @Test
    public void sayHelloLong() {
        int len = 2 << 12;
        final String resp = (String) longGeneric.$invoke("sayHelloLong", new String[]{int.class.getName()},
                new Object[]{len});
        Assert.assertEquals(len, resp.length());
    }


    @AfterClass
    public static void alterTest() {
        appDubboBootstrap.stop();
        DubboBootstrap.reset();
    }
}
