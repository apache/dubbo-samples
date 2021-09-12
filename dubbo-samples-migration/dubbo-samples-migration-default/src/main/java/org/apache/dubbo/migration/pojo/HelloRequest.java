package org.apache.dubbo.migration.pojo;

import java.io.Serializable;

/**
 * @author plusman
 * @since 2021/9/12 10:25 PM
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
