package org.apache.dubbo.shoop.testConsumer;

import org.apache.dubbo.shop.attributeClass.checkout.CheckoutData;
import org.apache.dubbo.shop.common.ReturnResult;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RestController
@RequestMapping("/member")
public class Task implements CommandLineRunner {
//    @DubboReference
//    PaymentService paymentService;

    @GetMapping("/order")
    public ReturnResult checkout(){
        CheckoutData checkoutData = new CheckoutData();
        checkoutData.setGoods(SetGood.setgood());
        checkoutData.setSummary(SetGood.setSummary());
        return ReturnResult.success(checkoutData);
    }
//    @DubboReference
//    ShippingService shippingService;
    @Override
    public void run(String... args) throws Exception {

    }

}
