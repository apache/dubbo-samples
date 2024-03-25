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

package org.apache.dubbo.samples.validation;




import javax.validation.ValidationException;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.samples.validation.api.ValidationParameter;
import org.apache.dubbo.samples.validation.api.ValidationService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import java.util.Date;
@SpringBootApplication()
@EnableDubbo
public class ValidationConsumer {


    @DubboReference
    private ValidationService validationService;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ValidationConsumer.class,args);
    }

    public void validation(){
        // Save OK
        ValidationParameter parameter = new ValidationParameter();
        parameter.setName("yang siming");
        parameter.setEmail("1608839567@qq.com");
        parameter.setAge(50);
        parameter.setLoginDate(new Date(System.currentTimeMillis() - 1000000));
        parameter.setExpiryDate(new Date(System.currentTimeMillis() + 1000000));
        validationService.save(parameter);
        System.out.println("Validation Save OK");

        // Save Error
        try {
            parameter = new ValidationParameter();
            validationService.save(parameter);
        } catch (ValidationException e) {
            System.err.println("Validation Save ERROR");
            e.printStackTrace();
        }

        // Delete OK
        validationService.delete(2, "abc");
        System.out.println("Validation Delete OK");

        // Delete Error
        try {
            validationService.delete(0, "abc");
        } catch (ValidationException e) {
            System.err.println("Validation Delete ERROR");
            e.printStackTrace();
        }
    }

}
