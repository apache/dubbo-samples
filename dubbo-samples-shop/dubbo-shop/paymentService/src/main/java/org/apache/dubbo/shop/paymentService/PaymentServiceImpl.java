package org.apache.dubbo.shop.paymentService;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.shop.Api.PaymentService;

import java.util.UUID;
@DubboService
public class PaymentServiceImpl implements PaymentService {
    @Override
    public boolean chargeCreditCardId() {
        return true;
    }

    @Override
    public String getTransactionID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
