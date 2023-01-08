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
package org.apache.dubbo.samples;

import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.registry.integration.RegistryDirectory;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.cluster.router.state.BitList;

import static org.awaitility.Awaitility.await;

public class MockRegistryDirectory extends RegistryDirectory {
    public MockRegistryDirectory(Class serviceType, URL url) {
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
        try {
            semaphore.acquire(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        lock.writeLock().lock();
        semaphore.release(3);
        if (shouldWait.get()) {
            await().atMost(60, TimeUnit.SECONDS).until(() -> semaphore.availablePermits() == 0);
        }
        super.setInvokers(bitList);
        count.set(0);
        if (shouldWait.get()) {
            await().atMost(60, TimeUnit.SECONDS).until(() -> count.get() > 10);
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
