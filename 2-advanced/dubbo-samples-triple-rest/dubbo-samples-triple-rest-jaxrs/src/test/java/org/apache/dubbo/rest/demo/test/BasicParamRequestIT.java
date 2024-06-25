/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.rest.demo.test;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rest.demo.pojo.Color;
import org.apache.dubbo.rest.demo.routine.BasicParamRequestService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BasicParamRequestIT {
    private static final String providerAddress = System.getProperty("dubbo.address", "localhost");

    @DubboReference
    private BasicParamRequestService basicParamRequestService;

    @Test
    public void testPrimitive() {
        int result1 = basicParamRequestService.primitiveInt(1, 1);
        Assert.assertEquals(2, result1);

        byte result2 = basicParamRequestService.primitiveByte((byte) 1, (byte) 1);
        Assert.assertEquals((byte) 2, result2);

        long result3 = basicParamRequestService.primitiveLong(1L, 1L);
        Assert.assertEquals(2L, result3);

        double result4 = basicParamRequestService.primitiveDouble(1.1, 1.2);
        Assert.assertEquals(2.3,result4,0.00001);

        short result5 = basicParamRequestService.primitiveShort((short) 1, (short) 1);
        Assert.assertEquals((short)2,result5);

        boolean result6 = basicParamRequestService.primitiveBoolean(true, false);
        Assert.assertFalse(result6);

        char result7 = basicParamRequestService.primitiveChar('a', 'b');
        Assert.assertEquals((char)('a'+'b'), result7);

        double result8 = basicParamRequestService.primitiveFloat(1.1f, 1.2f);
        Assert.assertEquals(2.3f,result8,0.00001f);
    }

    @Test
    public void test(){
        BigInteger result17 = basicParamRequestService.bigInt(BigInteger.ONE, BigInteger.ONE);
        Assert.assertEquals(BigInteger.TWO,result17);

        BigDecimal result18 = basicParamRequestService.bigDecimal(BigDecimal.ONE, BigDecimal.ZERO);
        Assert.assertEquals(BigDecimal.ONE,result18);

        int[] array1 = basicParamRequestService.intArray(new int[]{1, 2, 3});
        Assert.assertArrayEquals(new int[]{1,2,3},array1);

        long[] array2 = basicParamRequestService.longArray(new long[]{1L, 2L, 3L});
        Assert.assertArrayEquals(new long[]{1L,2L,3L},array2);
    }

    @Test
     public void testWrapper() {
        Boolean result9 = basicParamRequestService.wrapperBoolean(Boolean.TRUE, Boolean.FALSE);
        Assert.assertEquals(Boolean.FALSE, result9);

        Byte result10 = basicParamRequestService.wrapperByte((byte) 1, (byte) 1);
        Assert.assertEquals(Byte.valueOf((byte) 2), result10);

        Character result11 = basicParamRequestService.wrapperChar('a', 'b');
        Assert.assertEquals(Character.valueOf((char) ('a' + 'b')), result11);

        Double result12 = basicParamRequestService.wrapperDouble(1.1, 1.2);
        Assert.assertEquals(Double.valueOf(2.3), result12);

        Integer result13 = basicParamRequestService.wrapperInt(1, 1);
        Assert.assertEquals(Integer.valueOf(2), result13);

        Long result14 = basicParamRequestService.wrapperLong(1L, 1L);
        Assert.assertEquals(Long.valueOf(2L), result14);

        Short result16 = basicParamRequestService.wrapperShort((short) 1, (short) 1);
        Assert.assertEquals(Short.valueOf((short) 2), result16);
    }

     @Test
     public void testDateTime(){
         Date date = basicParamRequestService.date(Date.from(Instant.parse("2023-03-08T09:30:05Z")));
         Assert.assertEquals(Date.from(Instant.parse("2023-03-08T09:30:05Z")),date);

         Instant date1 = basicParamRequestService.date(Instant.parse("2023-03-08T09:30:05Z"));
         Assert.assertEquals(Instant.parse("2023-03-08T09:30:05Z"),date1);

         Calendar calendar = Calendar.getInstance();
         Calendar date2 = basicParamRequestService.date(calendar);
         Assert.assertEquals(date2,calendar);

         LocalDate date3 = basicParamRequestService.date(LocalDate.parse("2001-05-23"));
         Assert.assertEquals(LocalDate.parse("2001-05-23"),date3);

         LocalTime date4 = basicParamRequestService.date(LocalTime.parse("09:30:05.123"));
         Assert.assertEquals(LocalTime.parse("09:30:05.123"),date4);

         LocalDateTime date5 = basicParamRequestService.date(LocalDateTime.parse("2023-03-08T09:30:05"));
         Assert.assertEquals(LocalDateTime.parse("2023-03-08T09:30:05"),date5);

         ZonedDateTime date6 = basicParamRequestService.date(ZonedDateTime.parse("2021-06-11T10:00:00+02:00"));
         Assert.assertEquals(ZonedDateTime.parse("2021-06-11T10:00:00+02:00"),date6);
     }

    @Test
    public void testPrimitiveInt() {
        RestClient defaultClient = RestClient.create();
        ResponseEntity<Integer> result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/primitiveInt?a={a}&b={b}", 1, 1)
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(Integer.class);
        Assert.assertEquals(Integer.valueOf(2), result.getBody());
    }

    @Test
    public void testPrimitiveByte() {
        RestClient defaultClient = RestClient.create();
        ResponseEntity<Byte> result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/primitiveByte?a={a}&b={b}", 1, 1)
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(Byte.class);
        Assert.assertEquals(Byte.valueOf((byte) 2), result.getBody());
    }

    @Test
    public void testPrimitiveLong() {
        RestClient defaultClient = RestClient.create();
        ResponseEntity<Long> result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/primitiveLong?a={a}&b={b}", 1, 1)
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(Long.class);
        Assert.assertEquals(Long.valueOf(2), result.getBody());
    }

    @Test
    public void testPrimitiveDouble() {
        RestClient defaultClient = RestClient.create();
        ResponseEntity<Double> result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/primitiveDouble?a={a}&b={b}", 1.1, 1.2)
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(Double.class);
        Assert.assertEquals(Double.valueOf(2.3), result.getBody());
    }

    @Test
    public void testPrimitiveShort() {
        RestClient defaultClient = RestClient.create();
        ResponseEntity<Short> result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/primitiveShort?a={a}&b={b}", 1, 1)
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(Short.class);
        Assert.assertEquals(Short.valueOf((short) 2), result.getBody());
    }

    @Test
    public void testPrimitiveChar() {
        RestClient defaultClient = RestClient.create();
        ResponseEntity<Character> result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/primitiveChar?a={a}&b={b}", 'a', 'b')
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(Character.class);
        Assert.assertEquals(Character.valueOf((char) ('a'+'b')), result.getBody());
    }

    @Test
    public void testPrimitiveBoolean() {
        RestClient defaultClient = RestClient.create();
        ResponseEntity<Boolean> result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/primitiveBoolean?a={a}&b={b}", true, false)
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(Boolean.class);
        Assert.assertEquals(Boolean.FALSE, result.getBody());
    }

    @Test
    public void testPrimitiveFloat() {
        RestClient defaultClient = RestClient.create();
        ResponseEntity<Float> result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/primitiveFloat?a={a}&b={b}", 1.1f, 1.2f)
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(Float.class);
        Assert.assertEquals(2.3f,result.getBody(),0.00001f);
    }

    @Test
    public void testWrapperInt() {
        RestClient defaultClient = RestClient.create();
        ResponseEntity<Integer> result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/wrapperInt?a={a}&b={b}", 1, 1)
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(Integer.class);
        Assert.assertEquals(Integer.valueOf(2), result.getBody());
    }

    @Test
    public void testWrapperByte() {
        RestClient defaultClient = RestClient.create();
        ResponseEntity<Byte> result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/wrapperByte?a={a}&b={b}", 1, 1)
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(Byte.class);
        Assert.assertEquals(Byte.valueOf((byte) 2), result.getBody());
    }

    @Test
    public void testWrapperLong() {
        RestClient defaultClient = RestClient.create();
        ResponseEntity<Long> result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/wrapperLong?a={a}&b={b}", 1, 1)
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(Long.class);
        Assert.assertEquals(Long.valueOf(2), result.getBody());
    }

    @Test
    public void testWrapperDouble() {
        RestClient defaultClient = RestClient.create();
        ResponseEntity<Double> result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/wrapperDouble?a={a}&b={b}", 1.1, 1.2)
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(Double.class);
        Assert.assertEquals(Double.valueOf(2.3), result.getBody());
    }

    @Test
    public void testWrapperShort() {
        RestClient defaultClient = RestClient.create();
        ResponseEntity<Short> result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/wrapperShort?a={a}&b={b}", 1, 1)
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(Short.class);
        Assert.assertEquals(Short.valueOf((short) 2), result.getBody());
    }

    @Test
    public void testWrapperChar() {
        RestClient defaultClient = RestClient.create();
        ResponseEntity<Character> result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/wrapperCharacter?a={a}&b={b}", 'a', 'b')
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(Character.class);
        Assert.assertEquals(Character.valueOf((char) ('a'+'b')), result.getBody());
    }

    @Test
    public void testWrapperBoolean() {
        RestClient defaultClient = RestClient.create();
        ResponseEntity<Boolean> result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/wrapperBoolean?a={a}&b={b}", true, false)
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(Boolean.class);
        Assert.assertEquals(Boolean.FALSE, result.getBody());
    }

    @Test
    public void testBigInt() {
        RestClient defaultClient = RestClient.create();
        ResponseEntity<BigInteger> result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/bigInt?a={a}&b={b}",new BigInteger("3000000000"),new BigInteger("3000000000"))
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(BigInteger.class);
        Assert.assertEquals(new BigInteger("6000000000"), result.getBody());
    }


    @Test
    public void testBigDecimal() {
        RestClient defaultClient = RestClient.create();
        ResponseEntity<BigDecimal> result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/bigDecimal?a={a}&b={b}", new BigDecimal("1.1"), new BigDecimal("1.2"))
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(BigDecimal.class);
        Assert.assertEquals(new BigDecimal("2.3"),result.getBody());
    }

    @Test
    public void testIntArray() {
        RestClient defaultClient = RestClient.create();
        ResponseEntity<int[]> result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/intArray?array={a}&array={b}", 1, 1)
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(new ParameterizedTypeReference<int[]>() {});
        Assert.assertArrayEquals(new int[]{1,1}, result.getBody());
    }

    @Test
    public void testLongArray() {
        RestClient defaultClient = RestClient.create();
        ResponseEntity<long[]> result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/longArray?array={a}&array={b}", 1L, 1L)
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(new ParameterizedTypeReference<long[]>() {});
        Assert.assertArrayEquals(new long[]{1L,1L}, result.getBody());
    }

    @Test
    public void testDate() throws ParseException {
        RestClient defaultClient = RestClient.create();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/date?date=2023-03-08 09:30:05")
                .header("Content-type", "application/json")
                .exchange((request,response)->{
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        String str = mapper.readValue(response.getBody(), String.class);
                        return formatter.parse(str);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                });
        Assert.assertEquals(formatter.parse("2023-03-08 09:30:05"),result);
    }

    @Test
    public void testLocalDate() {
        RestClient defaultClient = RestClient.create();
        ResponseEntity<LocalDate> result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/localDate?localDate=2001-05-23")
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(LocalDate.class);
        Assert.assertEquals(LocalDate.parse("2001-05-23"),result.getBody());
    }

    @Test
    public void testCalendar() throws ParseException {
        RestClient defaultClient = RestClient.create();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/calendar?calendar=2023-03-08 09:30:05")
                .exchange((request,response)->{
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        String str = mapper.readValue(response.getBody(), String.class);
                        Date date =  formatter.parse(str);
                        Calendar instance = Calendar.getInstance();
                        instance.setTime(date);
                        return instance;
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                });
        Calendar instance = Calendar.getInstance();
        instance.setTime(formatter.parse("2023-03-08 09:30:05"));
        Assert.assertEquals(instance,result);
    }
    @Test
    public void testInstant() {
        RestClient defaultClient = RestClient.create();
        ResponseEntity<Instant> result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/Instant?instant=2023-03-08T09:30:05Z")
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(Instant.class);
        Assert.assertEquals(Instant.parse("2023-03-08T09:30:05Z"),result.getBody());
    }

    @Test
    public void testLocalTime() {
        RestClient defaultClient = RestClient.create();
        ResponseEntity<LocalTime> result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/localTime?localTime=09:30:05.123")
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(LocalTime.class);
        Assert.assertEquals(LocalTime.parse("09:30:05.123"),result.getBody());
    }

    @Test
    public void testLocalDateTime() {
        RestClient defaultClient = RestClient.create();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/localDateTime?localDateTime=2024-04-28 10:00:00")
                .exchange((request,response)->{
                    ObjectMapper mapper = new ObjectMapper();
                    String str = mapper.readValue(response.getBody(), String.class);
                    return LocalDateTime.parse(str, formatter);
                });
        Assert.assertEquals(LocalDateTime.parse("2024-04-28 10:00:00",formatter),result);
    }

    @Test
    public void testZonedDateTime() {
        RestClient defaultClient = RestClient.create();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        ZonedDateTime result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/zonedDateTime?zonedDateTime=2021-06-11 10:00:00")
                .header("Content-type", "application/json")
                .exchange((request,response)->{
                    ObjectMapper mapper = new ObjectMapper();
                    String value = mapper.readValue(response.getBody(), String.class);
                    int i = value.indexOf('[');
                    return LocalDateTime.parse(value.substring(0, i), formatter).atZone(ZoneId.of(value.substring(i + 1, value.length() - 1)));
                });
        Assert.assertEquals(LocalDateTime.parse("2021-06-11T10:00:00",formatter).atZone(ZoneId.systemDefault()),result);
    }

    @Test
    public void testEnum() {
        RestClient defaultClient = RestClient.create();
        ResponseEntity<Color> result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/enum?enum=RED")
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(Color.class);
        Assert.assertEquals(Color.RED,result.getBody());
    }

    @Test
    public void testOptionalDouble() {
        RestClient defaultClient = RestClient.create();
        ResponseEntity<Double> result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/optionalDouble?optionalDouble={a}",1.1)
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(Double.class);
        Assert.assertEquals(Double.valueOf("1.1"),result.getBody());
    }


    @Test
    public void testOptionalString() {
        RestClient defaultClient = RestClient.create();
        ResponseEntity<String> result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/optionalString?optionalString={a}","Hello world")
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(String.class);
        Assert.assertEquals("Hello world",result.getBody());
    }

    @Test
    public void testOptionalInt() {
        RestClient defaultClient = RestClient.create();
        ResponseEntity<Integer> result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/optionalInt?optionalInt={a}",1)
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(Integer.class);
        Assert.assertEquals(Integer.valueOf(1),result.getBody());
    }
}
