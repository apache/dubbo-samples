package org.apache.dubbo.samples.extensibility.protocol.common;

import java.lang.reflect.InvocationTargetException;

public interface MethodInvoker {

    public String invoker() throws IllegalArgumentException,IllegalAccessException, InvocationTargetException;
}
