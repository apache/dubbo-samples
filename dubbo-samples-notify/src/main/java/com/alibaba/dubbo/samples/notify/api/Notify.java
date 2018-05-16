package com.alibaba.dubbo.samples.notify.api;


/**
 * @author zmx ON 2018/4/26
 */
public interface Notify {

    public void onreturn(String name, int id);

    public void onthrow(Throwable ex, String name, int id);
}
