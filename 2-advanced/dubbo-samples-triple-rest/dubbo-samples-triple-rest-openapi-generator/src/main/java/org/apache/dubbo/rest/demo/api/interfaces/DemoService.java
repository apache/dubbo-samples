package org.apache.dubbo.rest.demo.api.interfaces;

import org.apache.dubbo.rest.demo.model.Error;
import org.apache.dubbo.rest.demo.model.HelloRequest;
import org.apache.dubbo.rest.demo.model.User;
import org.apache.dubbo.rest.demo.model.*;
import java.util.List;
import java.util.Map;
import java.time.OffsetDateTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.annotation.Generated;


@Generated(value = "org.openapitools.codegen.languages.JavaDubboServerCodegen", comments = "Generator version: 7.16.0-SNAPSHOT")

public interface DemoService {

    /**
     * Hello with user and count
     * Returns a greeting message with user information and count
     *
     * @param helloRequest  (required)
     * @return String
     */
    String hello(
        HelloRequest helloRequest
    );

    /**
     * Hello with user only
     * Returns a greeting message with user information
     *
     * @param user  (required)
     * @return String
     */
    String helloUser(
        User user
    );
}
