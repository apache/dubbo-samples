package org.apache.dubbo.samples.provider;

import org.apache.dubbo.common.deploy.ModuleDeployListener;
import org.apache.dubbo.qos.command.impl.Ls;
import org.apache.dubbo.rpc.model.ModuleModel;

import java.util.concurrent.atomic.AtomicBoolean;

public class DemoModuleDeployListener implements ModuleDeployListener {
    private final static AtomicBoolean failed = new AtomicBoolean(false);
    @Override
    public void onInitialize(ModuleModel scopeModel) {

    }

    @Override
    public void onStarting(ModuleModel scopeModel) {

    }

    @Override
    public void onStarted(ModuleModel scopeModel) {
        String result = new Ls(scopeModel.getApplicationModel().getFrameworkModel()).execute(null, null);
        System.out.println(result);
        if (result.contains("(Y)")) {
            failed.set(true);
        }
    }

    @Override
    public void onStopping(ModuleModel scopeModel) {

    }

    @Override
    public void onStopped(ModuleModel scopeModel) {

    }

    @Override
    public void onFailure(ModuleModel scopeModel, Throwable cause) {

    }

    public static boolean isFailed() {
        return failed.get();
    }
}
