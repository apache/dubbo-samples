package com.alibaba.dubbo.sample.callback.api;

/**
 * CallbackService
 */
public interface CallbackService {

    void addListener(String key, CallbackListener listener);

}
