import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.RpcStatus;
import org.apache.dubbo.rpc.cluster.LoadBalance;
import org.apache.dubbo.rpc.cluster.loadbalance.AbstractLoadBalance;
import org.apache.dubbo.rpc.cluster.loadbalance.LeastActiveLoadBalance;
import org.apache.dubbo.rpc.cluster.loadbalance.RandomLoadBalance;
import org.apache.dubbo.samples.UserLoadBalance;
import org.junit.Assert;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.Assert.*;


public class UserLoadBalanceTest {

    Invocation invocation;

    Invoker<UserLoadBalanceTest> invoker1;
    Invoker<UserLoadBalanceTest> invoker2;
    Invoker<UserLoadBalanceTest> invoker3;
    Invoker<UserLoadBalanceTest> invoker4;
    Invoker<UserLoadBalanceTest> invoker5;

    List<Invoker<UserLoadBalanceTest>> invokers = new ArrayList<Invoker<UserLoadBalanceTest>>();
    @org.junit.Test
    public void doSelect() {

        int runs = 1000;
        Map<Invoker, AtomicLong> counter = getInvokeCounter(runs, RandomLoadBalance.NAME);
        for (Map.Entry<Invoker, AtomicLong> entry : counter.entrySet()) {
            Long count = entry.getValue().get();
            Assert.assertTrue("abs diff should < avg",Math.abs(count - runs / (0f + invokers.size())) < runs / (0f + invokers.size()));
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j <= i; j++) {
                RpcStatus.beginCount(invokers.get(i).getUrl(), invocation.getMethodName());
            }
        }
        counter = getInvokeCounter(runs, LeastActiveLoadBalance.NAME);
        for (Map.Entry<Invoker, AtomicLong> entry : counter.entrySet()) {
            Long count = entry.getValue().get();
        }
        Assert.assertEquals(runs, counter.get(invoker1).intValue());
        Assert.assertEquals(0, counter.get(invoker2).intValue());
        Assert.assertEquals(0, counter.get(invoker3).intValue());
        Assert.assertEquals(0, counter.get(invoker4).intValue());
        Assert.assertEquals(0, counter.get(invoker5).intValue());
    }


    public Map<Invoker, AtomicLong> getInvokeCounter(int runs, String loadbalanceName) {
        Map<Invoker, AtomicLong> counter = new ConcurrentHashMap<Invoker, AtomicLong>();
        LoadBalance lb = getLoadBalance(loadbalanceName);
        for (Invoker invoker : invokers) {
            counter.put(invoker, new AtomicLong(0));
        }
        URL url = invokers.get(0).getUrl();
        for (int i = 0; i < runs; i++) {
            Invoker sinvoker = lb.select(invokers, url, invocation);
            counter.get(sinvoker).incrementAndGet();
        }
        return counter;
    }

    protected AbstractLoadBalance getLoadBalance(String loadbalanceName) {
        return (AbstractLoadBalance) ExtensionLoader.getExtensionLoader(LoadBalance.class).getExtension(loadbalanceName);
    }

}
