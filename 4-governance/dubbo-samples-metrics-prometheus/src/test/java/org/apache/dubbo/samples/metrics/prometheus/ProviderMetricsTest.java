package org.apache.dubbo.samples.metrics.prometheus;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/dubbo-demo-consumer.xml"})
public class ProviderMetricsTest {

    private  final String port = "20889";

    @Test
    public void test() throws Exception {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet("http://localhost:" + port + "/metrics");
            CloseableHttpResponse response = client.execute(request);
            InputStream inputStream = response.getEntity().getContent();
            String text = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
            Assert.assertTrue(text.contains("jvm_gc_memory_promoted_bytes_total"));
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
}
