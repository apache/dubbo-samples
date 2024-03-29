package org.apache.dubbo.rest.demo.expansion.filter;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class FilterServiceImpl implements FilterService{

    @Override
    public String filterGet(String name) {
        return name;
    }

}
