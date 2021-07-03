package org.apache.dubbo.samples.boundary.mybatis;

import org.apache.dubbo.samples.boundary.mybatis.api.MybatisService;
import org.apache.dubbo.samples.boundary.mybatis.api.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.nullValue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/mybatis-consumer.xml")
public class MybatisServiceIT {
    @Autowired
    private MybatisService service;

    @Test
    public void findUser() throws Exception {
        User user = service.findByUserId(1);

        assertThat(user, not(nullValue()));
    }
}
