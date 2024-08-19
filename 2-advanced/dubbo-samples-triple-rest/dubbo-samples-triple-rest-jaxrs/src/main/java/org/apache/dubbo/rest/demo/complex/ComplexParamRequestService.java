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

package org.apache.dubbo.rest.demo.complex;

import org.apache.dubbo.rest.demo.pojo.Person;
import org.apache.dubbo.rest.demo.pojo.User;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Path("/complex")
public interface ComplexParamRequestService {

    @POST
    @Path("/list")
    List<User> list(List<User> list);

    @POST
    @Path("/set")
    Set<User> set(Set<User> users);

    @POST
    @Path("/array")
    User[] array(User[] users);

    @POST
    @Path("/stringMap")
    Map<String, User> stringMap(Map<String, User> userMap);

    @GET
    @Path("/testMapParam")
    @Produces({MediaType.APPLICATION_JSON})
    List<String> testMapParam(@QueryParam("map") Map<String, String> map);

    @GET
    @Path("/testMapHeader")
    @Produces(MediaType.TEXT_PLAIN)
    String testMapHeader(@HeaderParam("headers") String headerMap);

    @POST
    @Path("/xml")
    Person testXml(Person person);

    @GET
    @Path("/cookie")
    @Produces(MediaType.TEXT_PLAIN)
    String testCookie(@Context Cookie cookie);

    @POST
    @Path("/testMapForm")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    List<String> testMapForm(MultivaluedMap<String, String> params);

    @GET
    @Path("/httpHeader")
    String testHeader(@Context HttpHeaders headers);

    @GET
    @Path("/uri")
    @Produces(MediaType.TEXT_PLAIN)
    String testUriInfo(@Context UriInfo uriInfo);

    @POST
    @Path("/annoForm")
    @Produces(MediaType.TEXT_PLAIN)
    String testForm(@org.jboss.resteasy.annotations.Form Person person);
}
