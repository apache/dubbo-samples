package org.apache.dubbo.samples.notify;

public interface Notify {

    void onReturn(String name, int id);

    void onThrow(Throwable ex, int id);
}
