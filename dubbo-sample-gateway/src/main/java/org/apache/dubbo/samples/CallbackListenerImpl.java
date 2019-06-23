package org.apache.dubbo.samples;


import org.apache.dubbo.samples.callback.api.CallbackListener;

/**
 * @author complone
 *
 * 客户端监听器
 * 可选接口
 * 用户可获取获取服务端的推送信息，与 CallbackService 搭配使用
 *
 */
public class CallbackListenerImpl implements CallbackListener {

    @Override
    public void changed(String msg) {
        System.out.println("receive msg from server :" + msg);
    }


}
