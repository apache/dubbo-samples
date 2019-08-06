package org.apache.dubbo.samples;


import org.apache.dubbo.samples.callback.api.CallbackListener;


public class CallbackListenerImpl implements CallbackListener {

    @Override
    public void changed(String msg) {
        System.out.println("receive msg from server :" + msg);
    }

}
