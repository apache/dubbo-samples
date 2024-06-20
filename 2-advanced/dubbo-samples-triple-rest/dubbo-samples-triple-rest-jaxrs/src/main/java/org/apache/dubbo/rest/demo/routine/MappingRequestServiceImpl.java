package org.apache.dubbo.rest.demo.routine;

import org.apache.dubbo.config.annotation.DubboService;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@DubboService
@Path("/mapping")
public class MappingRequestServiceImpl implements MappingRequestService{
    @Override
    public String testInterface(String name) {
        return "Hello "+name;
    }
    @Override
    public String testPathZero(String name) {
        return "Hello "+name;
    }

    @Override
    public String testPathTwo(String name) {
        return "Hello "+name;
    }

    @Override
    public String testPathOne(String name) {
        return "Hello "+name;
    }
    @Override
    public String testPathParamTwo(String isbn, String type) {
        return isbn+type;
    }

    @Override
    public String testPathParam(String name, String zip) {
        return name+zip;
    }

    @Override
    public int testPathInt(int i) {
        return i;
    }

    @Override
    public String testPathAny(String name) {
        return "Hello "+name;
    }


    @Override
    public String testConsumesAJ(String name) {
        return "Hello "+name;
    }


    @Override
    public String testConsumesAll(String name) {
        return "Hello "+name;
    }

    @Override
    public String testProducesAJ(String name) {
        return "Hello "+name;
    }


    @Override
    public String testProducesAll(String name) {
        return "Hello "+name;
    }

    @GET
    @Path("/servicePath")
    @Consumes(MediaType.TEXT_PLAIN)
    @Override
    public String testService(@QueryParam("name") String name){
        return "Hello "+name;
    }


}
