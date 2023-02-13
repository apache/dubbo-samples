package org.apache.dubbo.samples.frontend.pages;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.samples.Item;
import org.apache.dubbo.samples.OrderDetail;
import org.apache.dubbo.samples.ShopService;
import org.apache.dubbo.samples.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.Random;


/**
 * This is the controller used by Spring framework.
 * <p>
 * The basic function of this controller is to map
 * each HTTP API Path to the correspondent method.
 */

@RestController
public class WebController {

    @Autowired
    private ShopService shopService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    ModelAndView login(@RequestParam String username, @RequestParam String password) {
        shopService.login(username, password);
        // get user info

        // get item detail
        Item item = shopService.checkItem(1, username);

        ModelAndView modelAndView = new ModelAndView("detail");
        modelAndView.addObject("username", username);
        modelAndView.addObject("item", item);
        return modelAndView;
    }

    @RequestMapping(value = "/timeoutLogin", method = RequestMethod.GET)
    ModelAndView timeoutLogin(@RequestParam String username, @RequestParam String password) {
        ModelAndView modelAndView;

        try {
            shopService.timeoutLogin(username, password);
        } catch (Exception e) {
            modelAndView = new ModelAndView("index");
            modelAndView.addObject("result", "Failed to login, request timeout, please add timeout policy and retry!");
            return modelAndView;
        }

        modelAndView = new ModelAndView("detail");
        modelAndView.addObject("username", username);
        return modelAndView;
    }

    @RequestMapping(value = "/grayLogin", method = RequestMethod.GET)
    ModelAndView grayLogin(@RequestParam String username, @RequestParam String password) {
        ModelAndView modelAndView;
        shopService.login(username, password);
        // get item detail from gray environment
        RpcContext.getClientAttachment().setAttachment("dubbo.tag", "gray");
        Item item = shopService.checkItemGray(1, username);

        modelAndView = new ModelAndView("detail");
        modelAndView.addObject("username", username);
        modelAndView.addObject("item", item);

        return modelAndView;
    }

    @RequestMapping(value = "/userinfo", method = RequestMethod.GET)
    @ResponseBody
    User grayLogin(@RequestParam String username) {
        // retry
        try {
            return shopService.getUserInfo(username);
        } catch (Exception e) {
            return null;
        }
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    @ResponseBody
    OrderDetail createOrder(@RequestParam String username, @RequestParam long sku) {
        return shopService.submitOrder(sku, 1, "Hangzhou China", "18069807880", username);
    }

}