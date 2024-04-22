package org.apache.dubbo.shop.Api;

public interface PaymentService {
    public String getTransactionID();

    public boolean chargeCreditCardId();
}
