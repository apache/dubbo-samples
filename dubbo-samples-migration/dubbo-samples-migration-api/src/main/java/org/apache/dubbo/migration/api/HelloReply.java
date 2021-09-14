package org.apache.dubbo.migration.api;

import java.io.Serializable;

/**
 * @author plusman
 * @since 2021/9/12 10:22 PM
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
