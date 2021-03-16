package com.apache.dubbo.sample.basic;

public class IGreeter2Impl implements IGreeter2 {
    @Override
    public String sayHello0(String request) {
        StringBuilder respBuilder = new StringBuilder(request);
        for (int i = 0; i < 20; i++) {
            respBuilder.append(respBuilder);
        }
        request = respBuilder.toString();
        return request;
    }
}
