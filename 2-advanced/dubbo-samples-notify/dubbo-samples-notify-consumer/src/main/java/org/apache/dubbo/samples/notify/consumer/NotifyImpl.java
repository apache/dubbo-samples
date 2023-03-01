package org.apache.dubbo.samples.notify.consumer;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.apache.dubbo.samples.notify.Notify;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class NotifyImpl implements Notify {

    public Map<Integer, Object> ret = new HashMap<>();

    @Override
    public void onReturn(String name, int id) {
        ret.put(id, name);
        System.out.println("onReturn: " + name);
    }

    @Override
    public void onThrow(Throwable ex, int id) {
        ret.put(id, ex);
        System.out.println("onThrow: " + ex);
    }
}
