package org.apache.dubbo.async.boot.provider;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.samples.async.boot.HiService;

/**
 * @author:ax1an9
 * @date: 24/3/2023
 * @time: 9:37 PM
 */
@DubboService
public class HiServiceImpl implements HiService {
    @Override
    public String sayHello(String name) {
        //zSystem.out.println("call : "+name);
        return "hi, your name is: "+name;
    }
}
