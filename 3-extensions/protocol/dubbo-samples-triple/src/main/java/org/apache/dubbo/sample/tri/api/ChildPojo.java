package org.apache.dubbo.sample.tri.api;

public class ChildPojo extends ParentPojo {

    private String child;

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return "ChildPojo{" +
                "child='" + child + '\'' +
                "} " + super.toString();
    }
}
