package org.apache.dubbo.shop.shippingService;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.shop.Api.Shipping;
import org.apache.dubbo.shop.Api.ShippingService;

@DubboService
public class ShippingServiceImpl implements ShippingService {
    @Override
    public double getShipping(Shipping shipping, String type) {
        switch (type){
            case "EUR":
                return 45.69;
            case "JPY":
                return 2000.36;
            case "USD":
                return 9665.37;
            case "GBP":
                return 8556.23;
            case "TRY":
                return 659.21;
            case "CAD":
                return 98.35;
        }
        return -1;
    }

    @Override
    public double getShipping(String type) {
        switch (type){
            case "EUR":
                return 45.69;
            case "JPY":
                return 2000.36;
            case "USD":
                return 9665.37;
            case "GBP":
                return 8556.23;
            case "TRY":
                return 659.21;
            case "CAD":
                return 98.35;
        }
        return -1;
    }
}
