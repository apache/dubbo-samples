package org.dubbo.samples.protosubff.api;

import org.dubbo.samples.protosubff.domain.UserInfo;

public interface IUserService {

	UserInfo insertUserInfo(String name);
}
