
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


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
@Path("/")
public interface BasicParamRequestService {
    @GET
    @Path("/primitive")
    int primitiveInt(@QueryParam("a") int a, @QueryParam("b") int b);

    @GET
    @Path("/primitiveLong")
    long primitiveLong(@QueryParam("a") long a, @QueryParam("b") Long b);

    @GET
    @Path("/primitiveByte")
    byte primitiveByte(@QueryParam("a") byte a, @QueryParam("b") byte b);


    @GET
    @Path("/primitiveDouble")
    double primitiveDouble(@QueryParam("a") double a,@QueryParam("b") double b);

    @GET
    @Path("/primitiveString")
    @Produces(MediaType.TEXT_PLAIN)
    String primitiveString(@QueryParam("a") String a,@QueryParam("b") String b);
}
