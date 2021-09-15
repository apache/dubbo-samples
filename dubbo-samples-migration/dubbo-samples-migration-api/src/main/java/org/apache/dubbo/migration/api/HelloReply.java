package org.apache.dubbo.migration.api;

import java.io.Serializable;

/**
 * HelloReply
 */
public class HelloReply implements Serializable {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
