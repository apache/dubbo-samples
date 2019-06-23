package org.apache.dubbo.samples;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.cluster.LoadBalance;
import org.apache.dubbo.rpc.cluster.loadbalance.RandomLoadBalance;

import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author daofeng.xjf
 *
 * 负载均衡扩展接口
 * 必选接口，核心接口
 * 此类可以修改实现，不可以移动类或者修改包名
 * 选手需要基于此类实现自己的负载均衡算法
 */
public class UserLoadBalance extends RandomLoadBalance {

    @Override
    public <T> Invoker<T> doSelect(List<Invoker<T>> invokers, URL url, Invocation invocation) throws RpcException {


        // 循环当前注册的多个invoker 优先选择与自己IP相同的服务
        for (Invoker t : invokers) {
            try {
                InetAddress addr = InetAddress.getLocalHost();
                String ip=addr.getHostAddress().toString(); //获取本机ip

                URL u = t.getUrl();

                if (u.getIp().equals(ip)) {
                    return  t;
                }
            } catch (Exception e) {
            }
        }

        return super.doSelect(invokers, url, invocation);

    }

}
