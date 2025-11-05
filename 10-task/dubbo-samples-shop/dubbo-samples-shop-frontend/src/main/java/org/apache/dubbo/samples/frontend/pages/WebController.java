/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.samples.frontend.pages;

import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.samples.Item;
import org.apache.dubbo.samples.OrderDetail;
import org.apache.dubbo.samples.ShopService;
import org.apache.dubbo.samples.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class WebController {

    @Autowired
    private ShopService shopService;

    // user GET to avoid resubmit warning on browser side.
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

    // user GET to avoid resubmit warning on browser side.
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

    // user GET to avoid resubmit warning on browser side.
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
    User userInfo(@RequestParam String username) {
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