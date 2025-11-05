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

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class BasicParamRequestIT extends BaseTest {

    @DubboReference(url = "tri://${dubbo.address:localhost}:50052")
    private BasicParamRequestService basicParamRequestService;

    @Test
    public void testPrimitive() {
        int result1 = basicParamRequestService.primitiveInt(1, 1);
        Assertions.assertEquals(2, result1);

        byte result2 = basicParamRequestService.primitiveByte((byte) 1, (byte) 1);
        Assertions.assertEquals((byte) 2, result2);

        long result3 = basicParamRequestService.primitiveLong(1L, 1L);
        Assertions.assertEquals(2L, result3);

        double result4 = basicParamRequestService.primitiveDouble(1.1, 1.2);
        Assertions.assertEquals(2.3, result4, 0.00001);

        short result5 = basicParamRequestService.primitiveShort((short) 1, (short) 1);
        Assertions.assertEquals((short) 2, result5);

        boolean result6 = basicParamRequestService.primitiveBoolean(true, false);
        Assertions.assertFalse(result6);

        char result7 = basicParamRequestService.primitiveChar('a', 'b');
        Assertions.assertEquals((char) ('a' + 'b'), result7);

        double result8 = basicParamRequestService.primitiveFloat(1.1f, 1.2f);
        Assertions.assertEquals(2.3f, result8, 0.00001f);
    }

    @Test
    public void test() {
        BigInteger result17 = basicParamRequestService.bigInt(BigInteger.ONE, BigInteger.ONE);
        Assertions.assertEquals(BigInteger.TWO, result17);

        BigDecimal result18 = basicParamRequestService.bigDecimal(BigDecimal.ONE, BigDecimal.ZERO);
        Assertions.assertEquals(BigDecimal.ONE, result18);

        int[] array1 = basicParamRequestService.intArray(new int[] {1, 2, 3});
        Assertions.assertArrayEquals(new int[] {1, 2, 3}, array1);

        long[] array2 = basicParamRequestService.longArray(new long[] {1L, 2L, 3L});
        Assertions.assertArrayEquals(new long[] {1L, 2L, 3L}, array2);
    }

    @Test
    public void testWrapper() {
        Boolean result9 = basicParamRequestService.wrapperBoolean(Boolean.TRUE, Boolean.FALSE);
        Assertions.assertEquals(Boolean.FALSE, result9);

        Byte result10 = basicParamRequestService.wrapperByte((byte) 1, (byte) 1);
        Assertions.assertEquals(Byte.valueOf((byte) 2), result10);

        Character result11 = basicParamRequestService.wrapperChar('a', 'b');
        Assertions.assertEquals(Character.valueOf((char) ('a' + 'b')), result11);

        Double result12 = basicParamRequestService.wrapperDouble(1.1, 1.2);
        Assertions.assertEquals(Double.valueOf(2.3), result12);

        Integer result13 = basicParamRequestService.wrapperInt(1, 1);
        Assertions.assertEquals(Integer.valueOf(2), result13);

        Long result14 = basicParamRequestService.wrapperLong(1L, 1L);
        Assertions.assertEquals(Long.valueOf(2L), result14);

        Short result16 = basicParamRequestService.wrapperShort((short) 1, (short) 1);
        Assertions.assertEquals(Short.valueOf((short) 2), result16);
    }

    @Test
    public void testDateTime() {
        Date date = basicParamRequestService.date(Date.from(Instant.parse("2023-03-08T09:30:05Z")));
        Assertions.assertEquals(Date.from(Instant.parse("2023-03-08T09:30:05Z")), date);

        Instant date1 = basicParamRequestService.date(Instant.parse("2023-03-08T09:30:05Z"));
        Assertions.assertEquals(Instant.parse("2023-03-08T09:30:05Z"), date1);

        Calendar calendar = Calendar.getInstance();
        Calendar date2 = basicParamRequestService.date(calendar);
        Assertions.assertEquals(date2, calendar);

        LocalDate date3 = basicParamRequestService.date(LocalDate.parse("2001-05-23"));
        Assertions.assertEquals(LocalDate.parse("2001-05-23"), date3);

        LocalTime date4 = basicParamRequestService.date(LocalTime.parse("09:30:05.123"));
        Assertions.assertEquals(LocalTime.parse("09:30:05.123"), date4);

        LocalDateTime date5 = basicParamRequestService.date(LocalDateTime.parse("2023-03-08T09:30:05"));
        Assertions.assertEquals(LocalDateTime.parse("2023-03-08T09:30:05"), date5);

        ZonedDateTime date6 = basicParamRequestService.date(ZonedDateTime.parse("2021-06-11T10:00:00+02:00"));
        Assertions.assertEquals(ZonedDateTime.parse("2021-06-11T10:00:00+02:00"), date6);
    }

    @Test
    public void testPrimitiveInt() {
        ResponseEntity<Integer> result = restClient.get()
                .uri(toUri("/primitiveInt?a={a}&b={b}"), 1, 1)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(Integer.class);
        Assertions.assertEquals(Integer.valueOf(2), result.getBody());
    }

    @Test
    public void testPrimitiveByte() {
        ResponseEntity<Byte> result = restClient.get()
                .uri(toUri("/primitiveByte?a={a}&b={b}"), 1, 1)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(Byte.class);
        Assertions.assertEquals(Byte.valueOf((byte) 2), result.getBody());
    }

    @Test
    public void testPrimitiveLong() {
        ResponseEntity<Long> result = restClient.get()
                .uri(toUri("/primitiveLong?a={a}&b={b}"), 1, 1)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(Long.class);
        Assertions.assertEquals(Long.valueOf(2), result.getBody());
    }

    @Test
    public void testPrimitiveDouble() {
        ResponseEntity<Double> result = restClient.get()
                .uri(toUri("/primitiveDouble?a={a}&b={b}"), 1.1, 1.2)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(Double.class);
        Assertions.assertEquals(Double.valueOf(2.3), result.getBody());
    }

    @Test
    public void testPrimitiveShort() {
        ResponseEntity<Short> result = restClient.get()
                .uri(toUri("/primitiveShort?a={a}&b={b}"), 1, 1)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(Short.class);
        Assertions.assertEquals(Short.valueOf((short) 2), result.getBody());
    }

    @Test
    public void testPrimitiveChar() {
        ResponseEntity<Character> result = restClient.get()
                .uri(toUri("/primitiveChar?a={a}&b={b}"), 'a', 'b')
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(Character.class);
        Assertions.assertEquals(Character.valueOf((char) ('a' + 'b')), result.getBody());
    }

    @Test
    public void testPrimitiveBoolean() {
        ResponseEntity<Boolean> result = restClient.get()
                .uri(toUri("/primitiveBoolean?a={a}&b={b}"), true, false)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(Boolean.class);
        Assertions.assertEquals(Boolean.FALSE, result.getBody());
    }

    @Test
    @SuppressWarnings("DataFlowIssue")
    public void testPrimitiveFloat() {
        ResponseEntity<Float> result = restClient.get()
                .uri(toUri("/primitiveFloat?a={a}&b={b}"), 1.1f, 1.2f)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(Float.class);
        Assertions.assertEquals(2.3f, result.getBody(), 0.00001f);
    }

    @Test
    public void testWrapperInt() {
        ResponseEntity<Integer> result = restClient.get()
                .uri(toUri("/wrapperInt?a={a}&b={b}"), 1, 1)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(Integer.class);
        Assertions.assertEquals(Integer.valueOf(2), result.getBody());
    }

    @Test
    public void testWrapperByte() {
        ResponseEntity<Byte> result = restClient.get()
                .uri(toUri("/wrapperByte?a={a}&b={b}"), 1, 1)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(Byte.class);
        Assertions.assertEquals(Byte.valueOf((byte) 2), result.getBody());
    }

    @Test
    public void testWrapperLong() {
        ResponseEntity<Long> result = restClient.get()
                .uri(toUri("/wrapperLong?a={a}&b={b}"), 1, 1)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(Long.class);
        Assertions.assertEquals(Long.valueOf(2), result.getBody());
    }

    @Test
    public void testWrapperDouble() {
        ResponseEntity<Double> result = restClient.get()
                .uri(toUri("/wrapperDouble?a={a}&b={b}"), 1.1, 1.2)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(Double.class);
        Assertions.assertEquals(Double.valueOf(2.3), result.getBody());
    }

    @Test
    public void testWrapperShort() {
        ResponseEntity<Short> result = restClient.get()
                .uri(toUri("/wrapperShort?a={a}&b={b}"), 1, 1)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(Short.class);
        Assertions.assertEquals(Short.valueOf((short) 2), result.getBody());
    }

    @Test
    public void testWrapperChar() {
        ResponseEntity<Character> result = restClient.get()
                .uri(toUri("/wrapperCharacter?a={a}&b={b}"), 'a', 'b')
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(Character.class);
        Assertions.assertEquals(Character.valueOf((char) ('a' + 'b')), result.getBody());
    }

    @Test
    public void testWrapperBoolean() {
        ResponseEntity<Boolean> result = restClient.get()
                .uri(toUri("/wrapperBoolean?a={a}&b={b}"), true, false)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(Boolean.class);
        Assertions.assertEquals(Boolean.FALSE, result.getBody());
    }

    @Test
    public void testBigInt() {
        ResponseEntity<BigInteger> result = restClient.get()
                .uri(toUri("/bigInt?a={a}&b={b}"), new BigInteger("3000000000"), new BigInteger("3000000000"))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(BigInteger.class);
        Assertions.assertEquals(new BigInteger("6000000000"), result.getBody());
    }

    @Test
    public void testBigDecimal() {
        ResponseEntity<BigDecimal> result = restClient.get()
                .uri(toUri("/bigDecimal?a={a}&b={b}"), new BigDecimal("1.1"), new BigDecimal("1.2"))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(BigDecimal.class);
        Assertions.assertEquals(new BigDecimal("2.3"), result.getBody());
    }

    @Test
    public void testIntArray() {
        ResponseEntity<int[]> result = restClient.get()
                .uri(toUri("/intArray?array={a}&array={b}"), 1, 1)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {});
        Assertions.assertArrayEquals(new int[] {1, 1}, result.getBody());
    }

    @Test
    public void testLongArray() {
        ResponseEntity<long[]> result = restClient.get()
                .uri(toUri("/longArray?array={a}&array={b}"), 1L, 1L)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {});
        Assertions.assertArrayEquals(new long[] {1L, 1L}, result.getBody());
    }

    @Test
    public void testDate() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date result = restClient.get()
                .uri(toUri("/date?date=2023-03-08 09:30:05"))
                .accept(MediaType.APPLICATION_JSON)
                .exchange((request, response) -> {
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        String str = mapper.readValue(response.getBody(), String.class);
                        return formatter.parse(str);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                });
        Assertions.assertEquals(formatter.parse("2023-03-08 09:30:05"), result);
    }

    @Test
    public void testLocalDate() {
        ResponseEntity<LocalDate> result = restClient.get()
                .uri(toUri("/localDate?localDate=2001-05-23"))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(LocalDate.class);
        Assertions.assertEquals(LocalDate.parse("2001-05-23"), result.getBody());
    }

    @Test
    public void testCalendar() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar result = restClient.get()
                .uri(toUri("/calendar?calendar=2023-03-08 09:30:05"))
                .exchange((request, response) -> {
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        String str = mapper.readValue(response.getBody(), String.class);
                        Date date = formatter.parse(str);
                        Calendar instance = Calendar.getInstance();
                        instance.setTime(date);
                        return instance;
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                });
        Calendar instance = Calendar.getInstance();
        instance.setTime(formatter.parse("2023-03-08 09:30:05"));
        Assertions.assertEquals(instance, result);
    }

    @Test
    public void testInstant() {
        ResponseEntity<Instant> result = restClient.get()
                .uri(toUri("/Instant?instant=2023-03-08T09:30:05Z"))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(Instant.class);
        Assertions.assertEquals(Instant.parse("2023-03-08T09:30:05Z"), result.getBody());
    }

    @Test
    public void testLocalTime() {
        ResponseEntity<LocalTime> result = restClient.get()
                .uri(toUri("/localTime?localTime=09:30:05.123"))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(LocalTime.class);
        Assertions.assertEquals(LocalTime.parse("09:30:05.123"), result.getBody());
    }

    @Test
    public void testLocalDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime result = restClient.get()
                .uri(toUri("/localDateTime?localDateTime=2024-04-28 10:00:00"))
                .exchange((request, response) -> {
                    ObjectMapper mapper = new ObjectMapper();
                    String str = mapper.readValue(response.getBody(), String.class);
                    return LocalDateTime.parse(str, formatter);
                });
        Assertions.assertEquals(LocalDateTime.parse("2024-04-28 10:00:00", formatter), result);
    }

    @Test
    public void testZonedDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        ZonedDateTime result = restClient.get()
                .uri(toUri("/zonedDateTime?zonedDateTime=2021-06-11 10:00:00"))
                .accept(MediaType.APPLICATION_JSON)
                .exchange((request, response) -> {
                    ObjectMapper mapper = new ObjectMapper();
                    String value = mapper.readValue(response.getBody(), String.class);
                    int i = value.indexOf('[');
                    return LocalDateTime.parse(value.substring(0, i), formatter)
                            .atZone(ZoneId.of(value.substring(i + 1, value.length() - 1)));
                });
        Assertions.assertEquals(LocalDateTime.parse("2021-06-11T10:00:00", formatter)
                .atZone(ZoneId.systemDefault()), result);
    }

    @Test
    public void testEnum() {
        ResponseEntity<Color> result = restClient.get()
                .uri(toUri("/enum?enum=RED"))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(Color.class);
        Assertions.assertEquals(Color.RED, result.getBody());
    }

    @Test
    public void testOptionalDouble() {
        ResponseEntity<Double> result = restClient.get()
                .uri(toUri("/optionalDouble?optionalDouble={a}"), 1.1)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(Double.class);
        Assertions.assertEquals(Double.valueOf("1.1"), result.getBody());
    }

    @Test
    public void testOptionalString() {
        ResponseEntity<String> result = restClient.get()
                .uri(toUri("/optionalString?optionalString={a}"), "Hello world")
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .toEntity(String.class);
        Assertions.assertEquals("Hello world", result.getBody());
    }

    @Test
    public void testOptionalInt() {
        ResponseEntity<Integer> result = restClient.get()
                .uri(toUri("/optionalInt?optionalInt={a}"), 1)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(Integer.class);
        Assertions.assertEquals(Integer.valueOf(1), result.getBody());
    }
}
