package com.alibaba.dubbo.samples.generic.api;

/**
 * @author zmx ON 2018/4/19
 */
public interface IService<P, V> {
    V get(P params);
}
