package org.apache.dubbo.samples.webservice;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:spring/dubbo-samples-webservice-consumer.xml")
public class WebserviceIT {

    @Autowired
    @Qualifier("demoService")
    private DemoService consumer;

    @Test
    public void test() throws Exception {
        Assert.assertEquals("Hello, word: webservice", consumer.hello("webservice"));
    }
}
