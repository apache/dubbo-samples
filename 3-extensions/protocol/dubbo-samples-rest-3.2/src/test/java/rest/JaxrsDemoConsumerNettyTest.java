/*
 *
 *   Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements.  See the NOTICE file distributed with
 *   this work for additional information regarding copyright ownership.
 *   The ASF licenses this file to You under the Apache License, Version 2.0
 *   (the "License"); you may not use this file except in compliance with
 *   the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */
package rest;

import org.apache.dubbo.samples.rest.api.JaxRsRestDemoService;
import org.apache.dubbo.samples.rest.api.User;
import org.jboss.resteasy.specimpl.MultivaluedMapImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/rest-consumer.xml"})
public class JaxrsDemoConsumerNettyTest {
    @Autowired
    JaxRsRestDemoService jaxRsRestDemoService;

    @Test
    public void sayHello() {
        String hello = jaxRsRestDemoService.sayHello("hello");
        Assert.assertEquals("Hello, hello", hello);
    }

    @Test
    public void testPrimitive() {
        Integer result = jaxRsRestDemoService.primitiveInt(1, 2);
        Long resultLong = jaxRsRestDemoService.primitiveLong(1, 2l);
        long resultByte = jaxRsRestDemoService.primitiveByte((byte) 1, 2l);
        long resultShort = jaxRsRestDemoService.primitiveShort((short) 1, 2l, 1);

        assertThat(result, is(3));
        assertThat(resultShort, is(3l));
        assertThat(resultLong, is(3l));
        assertThat(resultByte, is(3l));
    }

    @Test
    public void testBody() {

        Assert.assertEquals(Long.valueOf(1l), jaxRsRestDemoService.testFormBody(1l));

        MultivaluedMapImpl<String, String> forms = new MultivaluedMapImpl<>();
        forms.put("form", Arrays.asList("F1"));

        Assert.assertEquals(Arrays.asList("F1"), jaxRsRestDemoService.testMapForm(forms));
        Assert.assertEquals(User.getInstance(), jaxRsRestDemoService.testJavaBeanBody(User.getInstance()));
    }


}
