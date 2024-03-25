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

package org.apache.dubbo.samples.validation;


import javax.validation.ValidationException;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.samples.validation.api.ValidationParameter;
import org.apache.dubbo.samples.validation.api.ValidationService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest(classes = {ValidationConsumer.class})
@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
public class ValidationServiceIT {

    @DubboReference
    private ValidationService validationService;

    @Test
    public void testSavePass() throws Exception {
        ValidationParameter parameter = new ValidationParameter();
        parameter.setName("yang siming");
        parameter.setEmail("1608839567@qq.com");
        parameter.setAge(50);
        parameter.setLoginDate(new Date(System.currentTimeMillis() - 1000000));
        parameter.setExpiryDate(new Date(System.currentTimeMillis() + 1000000));
        validationService.save(parameter);
    }

    @Test(expected = ValidationException.class)
    public void testSaveFail() {
        ValidationParameter parameter = new ValidationParameter();
        validationService.save(parameter);
    }

    @Test
    public void testDeletePass() throws Exception {
        validationService.delete(2, "abc");
    }

    @Test(expected = ValidationException.class)
    public void testDeleteFail() throws Exception {
        validationService.delete(0, "abc");
    }
}
