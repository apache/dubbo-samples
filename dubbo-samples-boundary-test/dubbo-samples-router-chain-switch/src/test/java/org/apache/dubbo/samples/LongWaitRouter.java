package org.apache.dubbo.samples;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.utils.Holder;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.cluster.router.RouterSnapshotNode;
import org.apache.dubbo.rpc.cluster.router.state.AbstractStateRouter;
import org.apache.dubbo.rpc.cluster.router.state.BitList;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class LongWaitRouter<T> extends AbstractStateRouter<T> {
    public LongWaitRouter(URL url) {
        super(url);
    }

    private final AtomicReference<BitList<Invoker<T>>> expectedInvokers = new AtomicReference<>();

    private static final AtomicBoolean foundFailed = new AtomicBoolean(false);
    private static final AtomicBoolean end = new AtomicBoolean(false);

    private static final AtomicInteger invokeCount = new AtomicInteger(0);



    @Override
    protected BitList<Invoker<T>> doRoute(BitList<Invoker<T>> invokers, URL url, Invocation invocation, boolean needToPrintMessage, Holder<RouterSnapshotNode<T>> routerSnapshotNodeHolder, Holder<String> messageHolder) throws RpcException {
        invokeCount.incrementAndGet();
        if (expectedInvokers.get() != null) {
            if (expectedInvokers.get().getOriginList() != invokers.getOriginList()) {
                foundFailed.set(true);
            }
        }
        return invokers;
    }

    @Override
    public void notify(BitList<Invoker<T>> invokers) {
        if (expectedInvokers.get() != null && !end.get()) {
            int i = invokeCount.get();
            try {
                while (invokeCount.get() - i < 1000 && !end.get()) {
                    Thread.sleep(1);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        expectedInvokers.set(invokers);
    }

    public static boolean isFoundFailed() {
        return foundFailed.get();
    }

    public static void setEnd() {
        end.set(true);
    }
}
