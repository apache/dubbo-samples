package org.dubbo.samples.protosubff.consumer.controller;

import org.apache.dubbo.config.annotation.Reference;
import org.dubbo.samples.protosubff.api.IUserService;
import org.dubbo.samples.protosubff.domain.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

	@Reference(check=false)
	private IUserService userService;
	
	@RequestMapping(value = "/test")
	@ResponseBody
	public String insertUserInfo(String name){
		UserInfo userInfo = userService.insertUserInfo(name);
		return userInfo.toString();
	}
}
