package org.apache.dubbo.migration.api;

import java.io.Serializable;

/**
 * HelloRequest
 */
public class HelloRequest implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
