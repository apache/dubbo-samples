
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
package org.apache.dubbo.rest.demo.routine;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rest.demo.pojo.Color;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@DubboService
public class BasicParamRequestServiceImpl implements BasicParamRequestService {

    @Override
    public int primitiveInt(int a, int b) {
        return a + b;
    }

    @Override
    public long primitiveLong(long a, Long b) {
        return a + b;
    }

    @Override
    public byte primitiveByte(byte a, byte b) {
        return (byte) (a + b);
    }

    @Override
    public double primitiveDouble(double a, double b) {
        return a + b;
    }

    @Override
    public short primitiveShort(short a, short b) {
        return (short) (a + b);
    }

    @Override
    public char primitiveChar(char a, char b) {
        return (char) (a + b);
    }

    @Override
    public boolean primitiveBoolean(boolean a, boolean b) {
        return a & b;
    }

    @Override
    public double primitiveFloat(float a, float b) {
        return a + b;
    }

    @Override
    public Integer wrapperInt(Integer a, Integer b) {
        return a + b;
    }

    @Override
    public Long wrapperLong(Long a, Long b) {
        return a + b;
    }

    @Override
    public Byte wrapperByte(Byte a, Byte b) {
        return (byte) (a + b);
    }

    @Override
    public Double wrapperDouble(Double a, Double b) {
        return a + b;
    }

    @Override
    public Short wrapperShort(Short a, Short b) {
        return (short) (a + b);
    }

    @Override
    public Character wrapperChar(Character a, Character b) {
        return (char) (a + b);
    }

    @Override
    public Boolean wrapperBoolean(Boolean a, Boolean b) {
        return a & b;
    }

    @Override
    public int[] intArray(int[] array) {
        return array;
    }

    @Override
    public long[] longArray(long[] array) {
        return array;
    }

    @Override
    public BigInteger bigInt(BigInteger a, BigInteger b) {
        return a.add(b);
    }

    @Override
    public BigDecimal bigDecimal(BigDecimal a, BigDecimal b) {
        return a.add(b);
    }

    @Override
    public Date date(Date date) {
        return date;
    }

    @Override
    public Calendar date(Calendar calendar) {
        return calendar;
    }

    @Override
    public Instant date(Instant instant) {
        return instant;
    }

    @Override
    public LocalDate date(LocalDate localDate) {
        return localDate;
    }

    @Override
    public LocalTime date(LocalTime localTime) {
        return localTime;
    }

    @Override
    public LocalDateTime date(LocalDateTime localDateTime) {
        return localDateTime;
    }

    @Override
    public ZonedDateTime date(ZonedDateTime zonedDateTime) {
        System.out.println(zonedDateTime);
        return zonedDateTime;
    }

    @Override
    public Optional<String> optionalString(Optional<String> optional) {
        return optional;
    }

    @Override
    public Optional<Integer> optionalInteger(Optional<Integer> optional) {
        return optional;
    }

    @Override
    public Optional<Double> optionalDouble(Optional<Double> optional) {
        return optional;
    }

    @Override
    public Color testEnum(Color color) {
        return color;
    }

}
