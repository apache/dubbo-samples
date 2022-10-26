package org.apache.dubbo.samples.rpccontext.impl;

import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.PenetrateAttachmentSelector;
import org.apache.dubbo.rpc.RpcContextAttachment;

import java.util.Map;

public class PenetrateAttachmentSelectorImpl1 implements PenetrateAttachmentSelector {

    @Override
    public Map<String, Object> select(Invocation invocation, RpcContextAttachment clientAttachment, RpcContextAttachment serverAttachment) {
        return null;
    }

    @Override
    public Map<String, Object> selectReverse(Invocation invocation, RpcContextAttachment clientResponse, RpcContextAttachment serverResponse) {
        return null;
    }
}
