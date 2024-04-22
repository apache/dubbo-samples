package org.apache.dubbo.shop.web.checkout;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.shop.common.pojo.checkout.CheckoutData;
import org.apache.dubbo.shop.service.CheckoutService;
import org.apache.dubbo.shop.common.ReturnResult;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;


@Component
@RestController
@RequestMapping("/member")
@CrossOrigin(origins = "*")
public class CheckoutController {
    @DubboReference
    CheckoutService checkoutService;
    @GetMapping("/order")
    public ReturnResult checkout(Integer orderId){
        CheckoutData checkoutData = checkoutService.checkoutResult(orderId);
        return ReturnResult.success(checkoutData);
    }
}
