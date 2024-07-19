package org.apache.dubbo.api;


/**
 * @author 杨泽翰
 */
public interface DemoService {

    /**
     * 打招呼
     *
     * @param name 名字
     * @return {@link String }
     */
    String sayHello(String name);

    /**
     * 获取用户
     *
     * @param name 名字
     * @return {@link User }
     */
    User getUser(String name);
}
