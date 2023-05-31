package org.apache.dubbo.nativeimage;


import java.io.Serializable;

public class HelloResponse implements Serializable {
    private String response;

    public HelloResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
