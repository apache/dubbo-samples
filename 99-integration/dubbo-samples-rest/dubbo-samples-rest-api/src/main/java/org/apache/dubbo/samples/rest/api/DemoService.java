package org.apache.dubbo.samples.rest.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * @author shenfeng
 */
@Path("/demo")
public interface DemoService {
    @Path("/hello")
    @GET
    @Consumes({MediaType.TEXT_PLAIN})
    String hello(@QueryParam("name") String name);
}
