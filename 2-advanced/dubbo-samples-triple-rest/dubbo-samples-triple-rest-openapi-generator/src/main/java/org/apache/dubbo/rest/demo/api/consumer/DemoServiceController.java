package org.apache.dubbo.rest.demo.api.consumer;

import org.apache.dubbo.rest.demo.model.Error;
import org.apache.dubbo.rest.demo.model.HelloRequest;
import org.apache.dubbo.rest.demo.model.User;
import org.apache.dubbo.rest.demo.model.*;
import org.apache.dubbo.rest.demo.api.interfaces.DemoService;
import java.util.List;
import java.util.Map;
import java.time.OffsetDateTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Generated;


@Generated(value = "org.openapitools.codegen.languages.JavaDubboServerCodegen", comments = "Generator version: 7.16.0-SNAPSHOT")

@RestController
@RequestMapping("/DemoService")
public class DemoServiceController {

    @DubboReference
    private DemoService demoService;

    @RequestMapping(method = RequestMethod.POST, value = "/hello")
    public String hello(
        @RequestParam(name = "helloRequest") HelloRequest helloRequest
    ) {
        return demoService.hello(helloRequest);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/helloUser")
    public String helloUser(
        @RequestParam(name = "user") User user
    ) {
        return demoService.helloUser(user);
    }
}
