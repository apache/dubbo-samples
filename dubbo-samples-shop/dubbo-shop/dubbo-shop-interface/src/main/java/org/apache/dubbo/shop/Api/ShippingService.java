package org.apache.dubbo.shop.Api;

public interface ShippingService {
        public double getShipping(Shipping shipping, String type);
        public double getShipping(String type);
}
