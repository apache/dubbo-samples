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
package org.apache.dubbo.samples.rpc.nativethrift;

import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.samples.rpc.nativethrift.api.DemoService;

import org.apache.thrift.TException;

import java.util.Map;

public class DemoServiceImpl implements DemoService.Iface {
    @Override
    public boolean echoBool(boolean arg) throws TException {
        return arg;
    }

    @Override
    public byte echoByte(byte arg) throws TException {
        return arg;
    }

    @Override
    public short echoI16(short arg) throws TException {
        return arg;
    }

    @Override
    public int echoI32(int arg) throws TException {
        Map<String, String> attachments = RpcContext.getContext().getAttachments();
        String parm = attachments.get("parm");
        System.out.println("parm：" + parm);
        return arg;
    }

    @Override
    public long echoI64(long arg) throws TException {
        return arg;
    }

    @Override
    public double echoDouble(double arg) throws TException {
        return arg;
    }

    @Override
    public String echoString(String arg) throws TException {
        return arg;
    }
}
