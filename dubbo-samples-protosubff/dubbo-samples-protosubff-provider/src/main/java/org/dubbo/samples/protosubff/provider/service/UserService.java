package org.dubbo.samples.protosubff.provider.service;

import java.util.UUID;

import org.apache.dubbo.config.annotation.Service;
import org.dubbo.samples.protosubff.api.IUserService;
import org.dubbo.samples.protosubff.domain.UserInfo;

/**
 * 这里 dynamic 需要手动设置为true
 * 具体见  AbstractServiceConfig dynamic=false
 *
 */
@Service(protocol="dubbo", dynamic=true)
public class UserService implements IUserService{

	@Override
	public UserInfo insertUserInfo(String name) {
		UserInfo userInfo = new UserInfo();
		userInfo.setName(name);
		userInfo.setUuid(UUID.randomUUID().toString());
		return userInfo;
	}

}
