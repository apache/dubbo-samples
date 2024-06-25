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

import org.springframework.web.bind.annotation.PathVariable;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/mapping")
public interface MappingRequestService {

    @GET
    @Path("/path")
    String testInterface(@QueryParam("name") String name);

    String testService(@QueryParam("name") String name);

    @GET
    @Path("/")
    String testPathZero(@QueryParam("name") String name);

    @GET
    @Path("/library/books")
    String testPathTwo(@QueryParam("name") String name);

    @GET
    @Path("/say/")
    String testPathOne(@QueryParam("name") String name);

    @GET
    @Path("/library/{isbn}/{type}")
    String testPathParamTwo (@PathVariable("isbn") String isbn,@PathVariable("type") String type);

    @GET
    @Path("/foo{name}-{zip}bar")
    String testPathParam(@PathVariable("name") String name,@PathVariable("zip") String zip);

    @GET
    @Path("{var:\\d+}/stuff")
    int testPathInt(@PathVariable("var") int i);

    @GET
    @Path("/{var:.*}/stuff")
    String testPathAny(@PathVariable("var") String name);


    @GET
    @Path("/consumeAj")
    @Consumes("application/json")
    @Produces("text/plain")
    String testConsumesAJ(@QueryParam("name") String name);


    @GET
    @Path("/consumeAll")
    @Consumes("*/*")
    @Produces("text/plain")
    String testConsumesAll(@QueryParam("name") String name);

    @GET
    @Path("/producesAJ")
    @Produces("application/json")
    String testProducesAJ(@QueryParam("name") String name);


    @GET
    @Path("/producesAll")
    @Produces("*/*")
    String testProducesAll(@QueryParam("name") String name);


}
