package org.apache.dubbo.integration.swagger;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.jaxrs.listing.BaseApiListingResource;
import org.apache.dubbo.config.annotation.Service;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by kimmking on 2017-6-13.
 */
@Service
public class DubboSwaggerApiListingResource extends BaseApiListingResource implements DubboSwaggerService {

    @Context
    ServletContext context;

    @Override
    public Response getListingJson(Application app, ServletConfig sc,
                                   HttpHeaders headers, UriInfo uriInfo) {
        Response response =  getListingJsonResponse(app, context, sc, headers, uriInfo);
        response.getHeaders().add("Access-Control-Allow-Origin", "*");
        response.getHeaders().add("Access-Control-Allow-Headers", "x-requested-with, ssi-token");
        response.getHeaders().add("Access-Control-Max-Age", "3600");
        response.getHeaders().add("Access-Control-Allow-Methods","GET,POST,PUT,DELETE,OPTIONS");
        return response;
    }
}