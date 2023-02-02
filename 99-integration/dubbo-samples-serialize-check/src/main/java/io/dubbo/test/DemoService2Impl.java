/*
 *
 *   Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements.  See the NOTICE file distributed with
 *   this work for additional information regarding copyright ownership.
 *   The ASF licenses this file to You under the Apache License, Version 2.0
 *   (the "License"); you may not use this file except in compliance with
 *   the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */
package io.dubbo.test;


import java.util.Map;

import org.junit.Assert;

import io.dubbo.test2.OthersSerializable;

public class DemoService2Impl implements DemoService2 {
    @Override
    public Object request(Object request) {
        return request;
    }

    @Override
    public User returnUser() {
        User user = new User();
        user.setName("returnUser");

        Parent parent = new Parent();
        parent.setName("returnUser");

        user.setParent(parent);
        return user;
    }

    @Override
    public void sendUser(User user) {
        Assert.assertEquals("sendUser", user.getName());
        Assert.assertEquals("sendUser", user.getParent().getName());
    }

    @Override
    public User requestUser(User user) {
        Assert.assertEquals("requestUser", user.getName());
        Assert.assertEquals("requestUser", user.getParent().getName());

        return user;
    }

    @Override
    public <T extends User> T returnUserGeneric() {
        User2 user = new User2();
        user.setName("returnUserGeneric");

        Parent parent = new Parent();
        parent.setName("returnUserGeneric");

        user.setParent(parent);
        return (T) user;
    }

    @Override
    public <T extends User> void sendUserGeneric(T user) {
        Assert.assertEquals("sendUserGeneric", user.getName());
        Assert.assertEquals("sendUserGeneric", user.getParent().getName());
    }

    @Override
    public <T extends User> T requestUserGeneric(T user) {
        Assert.assertEquals("requestUserGeneric", user.getName());
        Assert.assertEquals("requestUserGeneric", user.getParent().getName());
        return user;
    }

    @Override
    public NotSerializable returnNotSerializable() {
        NotSerializable notSerializable = new NotSerializable();
        notSerializable.setName("returnNotSerializable");
        return notSerializable;
    }

    @Override
    public void sendNotSerializable(NotSerializable notSerializable) {
        if (notSerializable instanceof Map) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public NotSerializable requestNotSerializable(NotSerializable notSerializable) {
        return notSerializable;
    }

    @Override
    public Object returnOtherPackage() {
        OthersSerializable othersSerializable = new OthersSerializable();
        othersSerializable.setName("returnOtherPackage");
        return othersSerializable;
    }

    @Override
    public void sendOtherPackage(Object otherPackage) {
        if (otherPackage instanceof Map) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Object requestOtherPackage(Object otherPackage) {
        return otherPackage;
    }

    @Override
    public Object returnOtherPackageNotSerializable() {
        io.dubbo.test2.NotSerializable notSerializable = new io.dubbo.test2.NotSerializable();
        notSerializable.setName("returnOtherPackageNotSerializable");
        return notSerializable;
    }

    @Override
    public void sendOtherPackageNotSerializable(Object otherPackageNotSerializable) {
        if (otherPackageNotSerializable instanceof Map) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Object requestOtherPackageNotSerializable(Object otherPackageNotSerializable) {
        return otherPackageNotSerializable;
    }
}
