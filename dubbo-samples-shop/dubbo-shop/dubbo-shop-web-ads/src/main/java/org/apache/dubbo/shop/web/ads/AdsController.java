package org.apache.dubbo.shop.web.ads;

import org.apache.dubbo.shop.common.ReturnResult;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
@RequestMapping("/goods")
public class AdsController {
    @GetMapping("/hot")
    public ReturnResult
}
