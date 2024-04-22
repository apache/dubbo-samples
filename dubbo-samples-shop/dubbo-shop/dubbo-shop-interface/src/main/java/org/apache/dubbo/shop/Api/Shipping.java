package org.apache.dubbo.shop.Api;

import java.io.Serializable;

public class Shipping implements Serializable {
    public String zipcode;
    public String city;
    public String state;
    public String country;

    @Override
    public String toString() {
        return "ship{" +
                "zipcode='" + zipcode + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
