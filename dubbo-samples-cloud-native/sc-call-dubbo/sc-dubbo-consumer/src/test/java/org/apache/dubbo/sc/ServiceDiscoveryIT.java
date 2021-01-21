package org.apache.dubbo.sc;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

public class ServiceDiscoveryIT {

    private static String consumerAddress = System.getProperty("consumer.address", "127.0.0.1");

    @Test
    public void test() {

        String url = String.format("http://%s:8099/dubbo/rest/user", consumerAddress);
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);

        System.out.println("result: " + result);
        Assert.assertEquals("{\"id\":1,\"name\":\"username1\"}", result);

    }

}
