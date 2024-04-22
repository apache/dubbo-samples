package oeg.apache.dubbo.shop.web.goodsDetails;

import org.apache.dubbo.shop.common.ReturnResult;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
public class GoodsDetailsController {
    @GetMapping("/goods")
    public ReturnResult GoodsDetailsList(@RequestParam Integer id){

    }
}
