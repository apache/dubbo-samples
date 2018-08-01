package org.apache.dubbo.samples.generic.service;

public class GreetingServiceImpl implements GreetingService {

    @Override
    public String sayHello(long[] num, int[] arg, char[] name) {
        return num.toString() + " " + arg.toString() + " " + name.toString();
    }

}
