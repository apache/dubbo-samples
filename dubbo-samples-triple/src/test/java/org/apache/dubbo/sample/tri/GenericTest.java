package org.apache.dubbo.sample.tri;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.service.GenericService;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class GenericTest {
    private static GenericService generic;

    @BeforeClass
    public static void init() {
        ReferenceConfig<GenericService> ref = new ReferenceConfig<>();
        ref.setInterface("org.apache.dubbo.sample.tri.WrapGreeter");
        ref.setCheck(false);
        ref.setTimeout(30000);
        ref.setProtocol(CommonConstants.TRIPLE);
        ref.setGeneric("true");
        ref.setLazy(true);
        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(new ApplicationConfig("demo-consumer"))
                .registry(new RegistryConfig(TriSampleConstants.ZK_ADDRESS))
                .reference(ref)
                .start();
        generic = ref.get();
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
        int len = 2 << 24;
        final String resp = (String) generic.$invoke("sayHelloLong", new String[]{int.class.getName()}, new Object[]{len});
        Assert.assertEquals(len, resp.length());
    }
}
