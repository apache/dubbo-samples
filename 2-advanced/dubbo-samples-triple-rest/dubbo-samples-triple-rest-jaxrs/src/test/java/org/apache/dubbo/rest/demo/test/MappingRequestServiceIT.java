package org.apache.dubbo.rest.demo.test;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rest.demo.routine.MappingRequestService;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClient;

import java.io.IOException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MappingRequestServiceIT {
    private static final String providerAddress = System.getProperty("dubbo.address", "localhost");


    @DubboReference
    private MappingRequestService mappingRequestService;

    @Test
    public void test(){
        String result1 = mappingRequestService.testService("world");
        Assert.assertEquals("Hello world",result1);

        String result2 = mappingRequestService.testInterface("world");
        Assert.assertEquals("Hello world",result2);

        String result3 = mappingRequestService.testPathOne("world");
        Assert.assertEquals("Hello world",result3);

        int result4 = mappingRequestService.testPathInt(1);
        Assert.assertEquals(1,result4);

        String result5 = mappingRequestService.testPathParam("a","b");
        Assert.assertEquals("ab",result5);

        String result6 = mappingRequestService.testPathTwo("world");
        Assert.assertEquals("Hello world",result6);

        String result7 = mappingRequestService.testPathParamTwo("a","b");
        Assert.assertEquals("ab",result7);

        String result8 = mappingRequestService.testPathZero("world");
        Assert.assertEquals("Hello world",result8);

        String result9 = mappingRequestService.testPathAny("world");
        Assert.assertEquals("Hello world",result9);

        String result10 = mappingRequestService.testConsumesAJ("world");
        Assert.assertEquals("Hello world",result10);

        String result11 = mappingRequestService.testConsumesAll("world");
        Assert.assertEquals("Hello world",result11);

        String result12 = mappingRequestService.testProducesAJ("world");
        Assert.assertEquals("Hello world",result12);

        String result13 = mappingRequestService.testProducesAll("world");
        Assert.assertEquals("Hello world",result13);
    }

    @Test
    public void testInterfacePath(){
        ResponseEntity<String> response = RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/mapping/path?name={name}","world")
                .header( "Content-type","text/plain")
                .retrieve()
                .toEntity(String.class);
        Assert.assertEquals("Hello world",response.getBody());
    }

    @Test
    public void testServicePath(){
        ResponseEntity<String> response = RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/mapping/servicePath?name={name}","world")
                .header( "Content-type","text/plain")
                .retrieve()
                .toEntity(String.class);
        Assert.assertEquals("Hello world",response.getBody());
    }

    @Test
    public void testPathZero(){
        ResponseEntity<String> response = RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/mapping/?name={name}","world")
                .header( "Content-type","text/plain")
                .retrieve()
                .toEntity(String.class);
        Assert.assertEquals("Hello world",response.getBody());
    }

    @Test
    public void testPathOne(){
        ResponseEntity<String> response = RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/mapping/say/?name={name}","world")
                .header( "Content-type","text/plain")
                .retrieve()
                .toEntity(String.class);
        Assert.assertEquals("Hello world",response.getBody());
    }

    @Test
    public void testPathTwo(){
        ResponseEntity<String> response = RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/mapping/library/books?name={name}","world")
                .header( "Content-type","text/plain")
                .retrieve()
                .toEntity(String.class);
        Assert.assertEquals("Hello world",response.getBody());
    }
    @Test
    public void testPathParamTwo(){
        ResponseEntity<String> response = RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/mapping/library/{isbn}/{type}","ISN1111","CHINESE")
                .header( "Content-type","text/plain")
                .retrieve()
                .toEntity(String.class);
        Assert.assertEquals("ISN1111CHINESE",response.getBody());
    }

    @Test
    public void testPathParam(){
        ResponseEntity<String> response = RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/mapping/foo{name}-{zip}bar","F","G")
                .header( "Content-type","text/plain")
                .retrieve()
                .toEntity(String.class);
        Assert.assertEquals("FG",response.getBody());
    }

    @Test
    public void testPathInt(){
        ResponseEntity<Integer> response = RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/mapping/1/stuff")
                .header( "Content-type","application/json")
                .retrieve()
                .toEntity(Integer.class);
        Assert.assertEquals(Integer.valueOf(1),response.getBody());
    }

    @Test
    public void testPathAny(){
        ResponseEntity<String> response = RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/mapping/{name}/stuff","world")
                .header( "Content-type","text/plain")
                .retrieve()
                .toEntity(String.class);
        Assert.assertEquals("Hello world",response.getBody());
    }

    @Test
    public void testConsumeAj(){
        ResponseEntity<String> response = RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/mapping/consumeAj?name={name}","world")
                .header( "Content-type","application/json")
                .retrieve()
                .toEntity(String.class);
        Assert.assertEquals("Hello world",response.getBody());
    }



    @Test
    public void testConsumeAll(){
        ResponseEntity<String> response = RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/mapping/consumeAll?name={name}","world")
                .header( "Content-type","text/plain")
                .retrieve()
                .toEntity(String.class);
        Assert.assertEquals("Hello world",response.getBody());
    }



    @Test
    public void testProducesAJ() throws IOException {
        ResponseEntity<String> response = RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/mapping/producesAJ?name={name}","world")
                .header( "Content-type","text/plain")
                .retrieve()
                .toEntity(String.class);
        String value = new ObjectMapper().writeValueAsString("Hello world");
        Assert.assertEquals(value,response.getBody());
    }


    @Test
    public void testProducesAll(){
        ResponseEntity<String> response = RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/mapping/producesAll?name={name}","world")
                .header( "Content-type","text/plain")
                .retrieve()
                .toEntity(String.class);
        Assert.assertEquals("Hello world",response.getBody());
    }


}
