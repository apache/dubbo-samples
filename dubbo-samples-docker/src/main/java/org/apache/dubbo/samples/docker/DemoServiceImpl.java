package org.apache.dubbo.samples.docker;

/**
 * @author ken.lj
 * @date 2017/10/16
 */
public class DemoServiceImpl implements DemoService {
    @Override
    public String hello(String str) {
        return "str";
    }
}
