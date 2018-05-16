package com.alibaba.dubbo.samples.notify.api;



public interface Notify {

    public void onreturn(String name, int id);

    public void onthrow(Throwable ex, String name, int id);
}
