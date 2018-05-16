package com.alibaba.dubbo.samples.generic.impl;

import com.alibaba.dubbo.samples.generic.api.IUserService;
import com.alibaba.dubbo.samples.generic.api.IUserService.Params;
import com.alibaba.dubbo.samples.generic.api.IUserService.User;

/**
 * @author zmx ON 2018/4/19
 */
public class UserServiceImpl implements IUserService {

    public User get(Params params) {
        return new User(1, "charles");
    }
}
