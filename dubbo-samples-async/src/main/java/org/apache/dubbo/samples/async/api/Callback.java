package org.apache.dubbo.samples.async.api;

public class Callback {

    public void onreturn(String msg, String arg) {
        System.out.println("onNotify:" + msg + "; args:" + arg);
    }

    public void onthrow(Throwable ex, String arg) {
        ex.printStackTrace();
        System.out.println(arg);

    }

    public void oninvoke(String arg) {
        System.out.println(arg);
    }

}
