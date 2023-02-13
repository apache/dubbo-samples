package org.apache.dubbo.samples.extensibility.protocol.common;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class JsonMethodInvoker implements MethodInvoker {

    private Object serviceImpl;

    private Method javaMethod;

    private String param;

    public void setServiceImpl(Object impl) {
        this.serviceImpl = impl;
    }

    public Object getServiceImpl() {
        return serviceImpl;
    }

    public void setJavaMethod(Method javaMethod) {
        this.javaMethod = javaMethod;
    }

    public String invoker() throws IllegalArgumentException,
            IllegalAccessException, InvocationTargetException {
        Object[] objs = JsonSerializer.serializer(param, javaMethod.getParameterTypes());
        Object res = javaMethod.invoke(serviceImpl, objs);
        if(res == null) return "";
        return JsonSerializer.toJsonStr(res);
    }

    public void setParam(String param) {
        this.param = param;
    }
}
