package com.alibaba.dubbo.samples.basic.api;

import java.io.Serializable;

public class Phone implements Serializable {

    private String mobile;
    private String tel;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
