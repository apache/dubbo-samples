package org.apache.dubbo.samples;

import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.registry.client.ServiceDiscoveryRegistryDirectory;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.cluster.router.state.BitList;

import static org.awaitility.Awaitility.await;

public class MockServiceDiscoveryRegistryDirectory extends ServiceDiscoveryRegistryDirectory {
    public MockServiceDiscoveryRegistryDirectory(Class serviceType, URL url) {
        super(serviceType, url);
    }

    private final AtomicInteger count = new AtomicInteger(0);

    private final AtomicInteger waitCount = new AtomicInteger(0);

    private static final AtomicBoolean shouldWait = new AtomicBoolean(false);

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private final Semaphore semaphore = new Semaphore(3);

    @Override
    public List<Invoker> doList(BitList bitList, Invocation invocation) {
        count.incrementAndGet();
        if (semaphore.tryAcquire()) {
            try {
                lock.readLock().lock();
                return super.doList(bitList, invocation);
            } finally {
                waitCount.incrementAndGet();
                lock.readLock().unlock();
                semaphore.release();
            }
        } else {
            return super.doList(bitList, invocation);
        }
    }

    @Override
    protected void setInvokers(BitList bitList) {
        lock.writeLock().lock();
        await().until(() -> semaphore.availablePermits() == 0);
        super.setInvokers(bitList);
        count.set(0);
        if (shouldWait.get()) {
            await().atMost(60, TimeUnit.SECONDS).until(() -> count.get() > 0);
        }
        waitCount.set(0);
        lock.writeLock().unlock();
        if (shouldWait.get()) {
            await().atMost(60, TimeUnit.SECONDS).until(() -> waitCount.get() > 0);
        }

    }

    public static AtomicBoolean getShouldWait() {
        return shouldWait;
    }
}
