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

import org.apache.dubbo.rest.demo.pojo.Person;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/param")
public interface ParamTransferRequestService {

    @GET
    @Path("/query")
    @Produces(MediaType.TEXT_PLAIN)
    String sayHello(@QueryParam("name") String name);

    @POST
    @Path("/form")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    String sayForm(@FormParam("form") String name);

    @GET
    @Path("/path/{id}")
    String sayPath(@PathParam("id") String id);

    @GET
    @Path("/header")
    String sayHeader(@HeaderParam("name") String name);


    @GET
    @Path("/cookie")
    String sayCookie(@CookieParam("cookieId") String cookieId);

    @GET
    @Path("/matrix")
    @Consumes(MediaType.APPLICATION_JSON)
    String sayMatrix(@MatrixParam("name") String name);

    @GET
    @Path("/xml")
    @Produces(MediaType.TEXT_XML)
    Person testXml();
}
