package org.apache.dubbo.samples.rpccontext.dto;

import java.io.Serializable;

public class Service2DTO implements Serializable {
    private String consumerReq;
    private String provider1Req;

    public String getConsumerReq() {
        return consumerReq;
    }

    public void setConsumerReq(String consumerReq) {
        this.consumerReq = consumerReq;
    }

    public String getProvider1Req() {
        return provider1Req;
    }

    public void setProvider1Req(String provider1Req) {
        this.provider1Req = provider1Req;
    }
}
