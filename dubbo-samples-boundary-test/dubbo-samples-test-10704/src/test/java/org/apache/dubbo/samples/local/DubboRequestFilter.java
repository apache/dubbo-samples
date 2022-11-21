package org.apache.dubbo.samples.local;


import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.service.GenericService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Activate(group = {CommonConstants.PROVIDER, CommonConstants.CONSUMER})
public class DubboRequestFilter implements Filter {

    private final static Logger log = LoggerFactory.getLogger(DubboRequestFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String client = CommonConstants.PROVIDER;
        if (RpcContext.getServiceContext().isConsumerSide()) {
            client = CommonConstants.CONSUMER;
        }
        String baselog = "Client[" + client + "],InterfaceName=[" + invocation.getInvoker().getInterface().getSimpleName() + "],MethodName=[" + invocation.getMethodName() + "]";
        log.info("DUBBO - 服务调用: {},Parameter={}", baselog, invocation.getArguments());

        long startTime = System.currentTimeMillis();
        // 执行接口调用逻辑
        Result result = invoker.invoke(invocation);
        // 调用耗时
        long elapsed = System.currentTimeMillis() - startTime;
        // 如果发生异常 则打印异常日志
        if (result.hasException() && invoker.getInterface().equals(GenericService.class)) {
            log.error("DUBBO - 服务异常: {},Exception={}", baselog, result.getException());
        } else {
            log.info("DUBBO - 服务响应: {},SpendTime=[{}ms]", baselog, elapsed);
        }
        return result;
    }

}
