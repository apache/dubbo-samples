/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.samples.extensibility.protocol.common;

import com.caucho.hessian.HessianException;
import com.caucho.hessian.client.HessianConnectionException;
import com.caucho.hessian.client.HessianProxyFactory;
import com.caucho.hessian.client.HessianURLConnectionFactory;
import com.caucho.hessian.io.HessianMethodSerializationException;
import com.caucho.hessian.server.HessianSkeleton;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.remoting.http.HttpBinder;
import org.apache.dubbo.remoting.http.HttpHandler;
import org.apache.dubbo.remoting.http.HttpServer;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.protocol.AbstractProxyProtocol;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HessianJsonProtocol extends AbstractProxyProtocol {


    private final Map<String, HttpServer> serverMap = new ConcurrentHashMap<String, HttpServer>();

    private final Map<String, HessianSkeleton> skeletonMap = new ConcurrentHashMap<String, HessianSkeleton>();

    private HttpBinder httpBinder;

    public HessianJsonProtocol() {
        super(HessianException.class);
    }

    public void setHttpBinder(HttpBinder httpBinder) {
        this.httpBinder = httpBinder;
    }

    public int getDefaultPort() {
        return 80;
    }

    private ThreadLocal<Boolean> ispost = new ThreadLocal<Boolean>();

    private class HessianHandler implements HttpHandler {
        private static final String PARAM = "param";

        public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            String uri = request.getRequestURI();
            HessianSkeleton skeleton = skeletonMap.get(uri);

            String method = request.getParameter("method");
            ispost.set(request.getMethod().equalsIgnoreCase("POST"));
            if (method != null && !"".equals(method.trim())) {
                try {
                    execHttp(request, response, skeleton, method);
                } catch (Exception e) {
                    response.setStatus(500);
                    response.getWriter().println(e.getMessage());
                }
                return;
            }

            if (!request.getMethod().equalsIgnoreCase("POST")) {
                response.setStatus(500);
            } else {
                RpcContext.getContext().setRemoteAddress(request.getRemoteAddr(), request.getRemotePort());
                try {
                    skeleton.invoke(request.getInputStream(), response.getOutputStream());
                } catch (Throwable e) {
                    throw new ServletException(e);
                }
            }
        }

        private void execHttp(HttpServletRequest request, HttpServletResponse response, HessianSkeleton skeleton, String method) throws Exception {
            Object impl = getServiceImpl(skeleton);
            Method[] methods = impl.getClass().getMethods();
            for (int i = 0; i < methods.length; i++) {
                Method javaMethod = methods[i];
                if (javaMethod.getName().equals(method)) {
                    String param = getParam(request);
                    String returnstr = jsonHandle(javaMethod, impl, param);
                    response.getWriter().print(returnstr);
                    break;
                }
            }
        }

        private String getParam(HttpServletRequest request) {
            String param = request.getParameter(PARAM);
            try {
                param = URLDecoder.decode(param, "utf8");
                if (!ispost.get()) {
                    param = new String(param.getBytes("iso8859-1"), "utf8");
                }
                ispost.set(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return param;
        }

        private Object getServiceImpl(HessianSkeleton skeleton) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
            Field field = skeleton.getClass().getDeclaredField("_service");
            boolean flag = field.isAccessible();
            field.setAccessible(true);
            Object obj = field.get(skeleton);
            field.setAccessible(flag);
            return obj;
        }

        private String jsonHandle(Method javaMethod, Object impl, String param) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
            JsonMethodInvoker jsonMethodinvoker = new JsonMethodInvoker();
            jsonMethodinvoker.setServiceImpl(impl);
            jsonMethodinvoker.setJavaMethod(javaMethod);
            jsonMethodinvoker.setParam(param);
            return jsonMethodinvoker.invoker();
        }

    }

    protected <T> Runnable doExport(T impl, Class<T> type, URL url) throws RpcException {
        String addr = url.getIp() + ":" + url.getPort();
        HttpServer server = serverMap.get(addr);
        if (server == null) {
            server = httpBinder.bind(url, new HessianHandler());
            serverMap.put(addr, server);
        }
        final String path = url.getAbsolutePath();
        HessianSkeleton skeleton = new HessianSkeleton(impl, type);
        skeletonMap.put(path, skeleton);
        return new Runnable() {
            public void run() {
                skeletonMap.remove(path);
            }
        };
    }

    @SuppressWarnings("unchecked")
    protected <T> T doRefer(Class<T> serviceType, URL url) throws RpcException {
        HessianProxyFactory hessianProxyFactory = new HessianProxyFactory();
        String client = url.getParameter(Constants.CLIENT_KEY, Constants.DEFAULT_HTTP_CLIENT);
        if ("httpclient".equals(client)) {
            hessianProxyFactory.setConnectionFactory(new HessianURLConnectionFactory());
        } else if (client != null && client.length() > 0 && !Constants.DEFAULT_HTTP_CLIENT.equals(client)) {
            throw new IllegalStateException("Unsupported http protocol client=\"" + client + "\"!");
        }
        int timeout = url.getParameter(Constants.TIMEOUT_KEY, Constants.DEFAULT_TIMEOUT);
        hessianProxyFactory.setConnectTimeout(timeout);
        hessianProxyFactory.setReadTimeout(timeout);
        return (T) hessianProxyFactory.create(serviceType, url.setProtocol("http").toJavaURL(), Thread.currentThread().getContextClassLoader());
    }

    protected int getErrorCode(Throwable e) {
        if (e instanceof HessianConnectionException) {
            if (e.getCause() != null) {
                Class<?> cls = e.getCause().getClass();
                if (SocketTimeoutException.class.equals(cls)) {
                    return RpcException.TIMEOUT_EXCEPTION;
                }
            }
            return RpcException.NETWORK_EXCEPTION;
        } else if (e instanceof HessianMethodSerializationException) {
            return RpcException.SERIALIZATION_EXCEPTION;
        }
        return super.getErrorCode(e);
    }

    public void destroy() {
        super.destroy();
        for (String key : new ArrayList<String>(serverMap.keySet())) {
            HttpServer server = serverMap.remove(key);
            if (server != null) {
                try {
                    if (logger.isInfoEnabled()) {
                        logger.info("Close hessian server " + server.getUrl());
                    }
                    server.close();
                } catch (Throwable t) {
                    logger.warn(t.getMessage(), t);
                }
            }
        }
    }
}
