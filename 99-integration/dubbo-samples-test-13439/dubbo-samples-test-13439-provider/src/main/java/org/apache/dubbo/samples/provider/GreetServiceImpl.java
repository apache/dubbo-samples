package org.apache.dubbo.samples.provider;

import org.apache.dubbo.samples.api.GreetService;

public class GreetServiceImpl implements GreetService {

    @Override
    public String greet(String msg) {
        return "Hello:"+msg;
    }

}
