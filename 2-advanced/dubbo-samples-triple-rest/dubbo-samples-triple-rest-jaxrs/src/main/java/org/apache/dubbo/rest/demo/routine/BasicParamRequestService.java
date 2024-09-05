
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

import org.apache.dubbo.rest.demo.pojo.Color;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

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

@Path("/")
public interface BasicParamRequestService {

    @GET
    @Path("/primitiveInt")
    int primitiveInt(@QueryParam("a") int a, @QueryParam("b") int b);

    @GET
    @Path("/primitiveLong")
    long primitiveLong(@QueryParam("a") long a, @QueryParam("b") Long b);

    @GET
    @Path("/primitiveByte")
    byte primitiveByte(@QueryParam("a") byte a, @QueryParam("b") byte b);

    @GET
    @Path("/primitiveDouble")
    double primitiveDouble(@QueryParam("a") double a, @QueryParam("b") double b);

    @GET
    @Path("/primitiveShort")
    short primitiveShort(@QueryParam("a") short a, @QueryParam("b") short b);

    @GET
    @Path("/primitiveChar")
    char primitiveChar(@QueryParam("a") char a, @QueryParam("b") char b);

    @GET
    @Path("/primitiveBoolean")
    boolean primitiveBoolean(@QueryParam("a") boolean a, @QueryParam("b") boolean b);

    @GET
    @Path("/primitiveFloat")
    double primitiveFloat(@QueryParam("a") float a, @QueryParam("b") float b);

    @GET
    @Path("/wrapperInt")
    Integer wrapperInt(@QueryParam("a") Integer a, @QueryParam("b") Integer b);

    @GET
    @Path("/wrapperLong")
    Long wrapperLong(@QueryParam("a") Long a, @QueryParam("b") Long b);

    @GET
    @Path("/wrapperByte")
    Byte wrapperByte(@QueryParam("a") Byte a, @QueryParam("b") Byte b);

    @GET
    @Path("/wrapperDouble")
    Double wrapperDouble(@QueryParam("a") Double a, @QueryParam("b") Double b);

    @GET
    @Path("/wrapperShort")
    Short wrapperShort(@QueryParam("a") Short a, @QueryParam("b") Short b);

    @GET
    @Path("/wrapperCharacter")
    Character wrapperChar(@QueryParam("a") Character a, @QueryParam("b") Character b);

    @GET
    @Path("/wrapperBoolean")
    Boolean wrapperBoolean(@QueryParam("a") Boolean a, @QueryParam("b") Boolean b);

    @GET
    @Path("/intArray")
    int[] intArray(@QueryParam("array") int[] array);

    @GET
    @Path("/longArray")
    long[] longArray(@QueryParam("array") long[] array);

    @GET
    @Path("/bigInt")
    BigInteger bigInt(@QueryParam("a") BigInteger a, @QueryParam("b") BigInteger b);

    @GET
    @Path("/bigDecimal")
    BigDecimal bigDecimal(@QueryParam("a") BigDecimal a, @QueryParam("b") BigDecimal b);

    @GET
    @Path("/date")
    Date date(@QueryParam("date") Date date);

    @GET
    @Path("/calendar")
    Calendar date(@QueryParam("calendar") Calendar calendar);

    @GET
    @Path("/Instant")
    Instant date(@QueryParam("instant") Instant instant);

    @GET
    @Path("/localDate")
    LocalDate date(@QueryParam("localDate") LocalDate localDate);

    @GET
    @Path("/localTime")
    LocalTime date(@QueryParam("localTime") LocalTime localTime);

    @GET
    @Path("/localDateTime")
    LocalDateTime date(@QueryParam("localDateTime") LocalDateTime localDateTime);

    @GET
    @Path("/zonedDateTime")
    ZonedDateTime date(@QueryParam("zonedDateTime") ZonedDateTime zonedDateTime);

    @GET
    @Path("/optionalString")
    @Produces(MediaType.TEXT_PLAIN)
    Optional<String> optionalString(@QueryParam("optionalString") Optional<String> optional);

    @GET
    @Path("/optionalInt")
    Optional<Integer> optionalInteger(@QueryParam("optionalInt") Optional<Integer> optional);

    @GET
    @Path("/optionalDouble")
    Optional<Double> optionalDouble(@QueryParam("optionalDouble") Optional<Double> optional);

    @GET
    @Path("/enum")
    Color testEnum(@QueryParam("enum") Color color);
}
