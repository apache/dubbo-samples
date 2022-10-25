package org.apache.dubbo.samples.rpccontext.dto;

import java.io.Serializable;

public class Service1DTO implements Serializable {
    private String consumerReq;
    private String provider2Res;

    private Service2DTO service2DTO;

    public Service2DTO getService2DTO() {
        return service2DTO;
    }

    public void setService2DTO(Service2DTO service2DTO) {
        this.service2DTO = service2DTO;
    }

    public String getConsumerReq() {
        return consumerReq;
    }

    public void setConsumerReq(String consumerReq) {
        this.consumerReq = consumerReq;
    }

    public String getProvider2Res() {
        return provider2Res;
    }

    public void setProvider2Res(String provider2Res) {
        this.provider2Res = provider2Res;
    }
}
