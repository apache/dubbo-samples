package org.apache.dubbo.shop.checkoutController;

import org.apache.dubbo.shop.attributeClass.checkout.CheckoutData;
import org.apache.dubbo.shop.common.ReturnResult;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Component
@RestController
@RequestMapping("/member")
@CrossOrigin(origins = "*")
public class CheckoutController {
    @GetMapping("/order")
    public ReturnResult checkout(){
        CheckoutData checkoutData = new CheckoutData();
        checkoutData.setGoods(SetGood.setgood());
        checkoutData.setSummary(SetGood.setSummary());
        System.out.println("已返回结果");
        System.out.println(checkoutData);
        return ReturnResult.success(checkoutData);
    }
}
