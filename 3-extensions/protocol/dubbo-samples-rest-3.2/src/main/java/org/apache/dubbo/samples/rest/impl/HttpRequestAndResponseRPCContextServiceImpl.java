package org.apache.dubbo.samples.rest.impl;

import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.protocol.rest.netty.NettyHttpResponse;
import org.apache.dubbo.rpc.protocol.rest.request.RequestFacade;
import org.apache.dubbo.samples.rest.api.HttpRequestAndResponseRPCContextService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service("httpRequestAndResponseRPCContextService")
public class HttpRequestAndResponseRPCContextServiceImpl implements HttpRequestAndResponseRPCContextService {
    @Override
    public String httpRequestParam(String hello) {
        Object request = RpcContext.getServerAttachment().getRequest();
        return ((RequestFacade) request).getParameter("name");
    }

    @Override
    public String httpRequestHeader(String hello) {
        Object request = RpcContext.getServerAttachment().getRequest();
        return ((RequestFacade) request).getHeader("header");
    }

    @Override
    public List<String> httpResponseHeader(String hello) {
        Object response = RpcContext.getServerAttachment().getResponse();
        Map<String, List<String>> outputHeaders = ((NettyHttpResponse) response).getOutputHeaders();
        String responseKey = "response";
        outputHeaders.put(responseKey, Arrays.asList(hello));


        return outputHeaders.get(responseKey);
    }
}
