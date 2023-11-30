package org.apache.dubbo.samples.test;

import org.apache.dubbo.rpc.service.GenericException;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.Map;

public class GenericServiceImpl implements GenericService {
    @Override
    public Object $invoke(String method, String[] parameterTypes, Object[] args) throws GenericException {
        if (method.equals("sayHello") && parameterTypes.length == 1 && parameterTypes[0].equals("org.apache.dubbo.samples.test.User")) {
            return "hello, " + ((Map)args[0]).get("name");
        }
        return null;
    }
}
