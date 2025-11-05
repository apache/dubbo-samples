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

import org.apache.dubbo.remoting.http12.HttpMethods;
import org.apache.dubbo.remoting.http12.HttpRequest;
import org.apache.dubbo.remoting.http12.HttpResponse;
import org.apache.dubbo.rest.demo.pojo.User;

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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@Path("/param")
public interface ParamTransferRequestService {

    @GET
    @Path("/query")
    @Produces(MediaType.TEXT_PLAIN)
    String sayHello(@QueryParam("name") String name);

    @GET
    @Path("/queryList")
    List<String> sayQueryList(@QueryParam("name") List<String> values);

    @GET
    @Path("/queryMap")
    Map<String, String> sayQueryMap(@QueryParam("name") Map<String, String> value);

    @GET
    @Path("/queryStringMap")
    Map<String, List<String>> sayQueryStringMap(@QueryParam("name") Map<String, List<String>> value);

    @GET
    @Path("/noAnnoParam")
    @Produces(MediaType.TEXT_PLAIN)
    String sayNoAnnoParam(String name);

    @GET
    @Path("/noAnnoListParam")
    List<String> sayNoAnnoListParam(List<String> value);

    @GET
    @Path("/noAnnoStringMapParam")
    Map<String, String> sayNoAnnoStringMapParam(Map<String, String> value);

    @GET
    @Path("/noAnnoArrayParam")
    String[] sayNoAnnoArrayParam(String[] value);

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
    @Path("/header/map")
    @Produces(MediaType.TEXT_PLAIN)
    String sayHeader(@HeaderParam("name") Map<String, String> value);

    @GET
    @Path("/cookie")
    String sayCookie(@CookieParam("cookieId") String cookieId);

    @GET
    @Path("/cookie/list")
    List<String> sayCookie(@CookieParam("cookieId") List<String> values);

    @GET
    @Path("/cookie/map")
    Map<String, String> sayCookie(@CookieParam("cookieId") Map<String, String> value);

    @GET
    @Path("/matrix/string/{m}")
    @Produces(MediaType.TEXT_PLAIN)
    String sayMatrixString(@PathParam("m") String m, @MatrixParam("name") String name);

    @GET
    @Path("/matrix/list/{m}")
    List<String> sayMatrixList(@PathParam("m") String m, @MatrixParam("name") List<String> values);

    @GET
    @Path("/matrix/map/{m}")
    Map<String, List<String>> sayMatrixMap(
            @PathParam("m") String m, @MatrixParam("name") Map<String, List<String>> valueMap);

    @POST
    @Path("/bodyUser")
    User sayUser(User users);

    @POST
    @Path("/bodyList")
    List<Long> sayList(List<Long> list);

    @POST
    @Path("/bodyStringMap")
    Map<String, String> sayStringMap(Map<String, String> value);

    @POST
    @Path("/output")
    String sayOutput(OutputStream out) throws IOException;

    @GET
    @Path("/httpMethod")
    @Produces(MediaType.TEXT_PLAIN)
    String sayHttpMethod(@Context HttpMethods methods);

    @GET
    @Path("/http")
    @Produces(MediaType.TEXT_PLAIN)
    void sayHttpRequest(@Context HttpRequest request, @Context HttpResponse response);

}
