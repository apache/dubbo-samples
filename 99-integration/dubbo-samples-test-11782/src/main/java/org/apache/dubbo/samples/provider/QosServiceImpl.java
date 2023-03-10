package org.apache.dubbo.samples.provider;

import org.apache.dubbo.qos.command.impl.Offline;
import org.apache.dubbo.rpc.model.FrameworkModel;
import org.apache.dubbo.samples.api.QosService;

public class QosServiceImpl implements QosService {
    @Override
    public void offline() {
        new Offline(FrameworkModel.defaultModel()).offline(".*");
    }
}
