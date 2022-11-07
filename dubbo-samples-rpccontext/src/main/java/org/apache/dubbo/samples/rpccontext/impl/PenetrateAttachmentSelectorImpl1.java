package org.apache.dubbo.samples.rpccontext.impl;

import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.PenetrateAttachmentSelector;

import java.util.Map;

public class PenetrateAttachmentSelectorImpl1 implements PenetrateAttachmentSelector {

    @Override
    public Map<String, Object> select(Invocation invocation) {
        return null;
    }

    @Override
    public Map<String, Object> selectReverse(Invocation invocation) {
        return null;
    }
}
