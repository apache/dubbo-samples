package org.apache.dubbo.shop.Api;

import java.io.Serializable;

public class Order implements Serializable {
    public String confirmation;
    public String transactionID;
    public double orderId;


    @Override
    public String toString() {
        return "order{" +
                "confirmation='" + confirmation + '\'' +
                ", transactionID='" + transactionID + '\'' +
                ", orderId=" + orderId +
                '}';
    }
}
