package com.alibaba.dubbo.samples.notify.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.dubbo.samples.notify.api.Notify;

public class NotifyImpl implements Notify{

    public Map<Integer, String> ret = new HashMap<>();
    public void onreturn(String name, int id) {
        ret.put(id, name);
        System.out.println("onreturn: " + name);
    }

    public void onthrow(Throwable ex, String name, int id) {
        System.out.println("onthrow: " + name);
    }
}
