package org.apache.dubbo.rest.openapi.advance;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.remoting.http12.rest.OpenAPI;
import org.apache.dubbo.remoting.http12.rest.Operation;
import org.apache.dubbo.remoting.http12.rest.Schema;

@DubboService
@OpenAPI(infoTitle = "Dubbo OpenAPI", infoDescription = "This API provides greeting services for users.", infoVersion = "v1", docDescription = "API for greeting users with their names and titles.")
public class DemoServiceImpl implements DemoService {

    @Operation(description = "Returns a greeting message with the provided name.")
    @Override
    public String hello(@Schema(description = "Name of the person to greet") String name) {
        return "Hello " + name;
    }

    @Operation(description = "Returns a greeting message with user's title and name, along with a count.")
    @Override
    public String hello(
            @Schema(description = "User object containing title and name") User user,
            @Schema(description = "Number of times to greet") int count) {
        return "Hello " + user.getTitle() + ". " + user.getName() + ", " + count;
    }

    @Operation(description = "Returns a greeting message with user's title and name.")
    @Override
    public String helloUser(@Schema(description = "User object containing title and name") User user) {
        return "Hello " + user.getTitle() + ". " + user.getName();
    }
}
