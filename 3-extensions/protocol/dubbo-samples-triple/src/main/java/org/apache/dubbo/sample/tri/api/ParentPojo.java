package org.apache.dubbo.sample.tri.api;

import java.io.Serializable;

public class ParentPojo implements Serializable {

    private String parent;

    private Byte byte1;

    public Byte getByte1() {
        return byte1;
    }

    public void setByte1(Byte byte1) {
        this.byte1 = byte1;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "ParentPojo{" +
                "parent='" + parent + '\'' +
                ", byte1=" + byte1 +
                '}';
    }
}
