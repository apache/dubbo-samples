package org.apache.dubbo.samples.rpc.nativethrift;

import com.alibaba.dubbo.rpc.RpcContext;

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
