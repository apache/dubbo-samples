package com.alibaba.dubbo.samples.validation;

import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import com.alibaba.dubbo.samples.validation.api.ValidationParameter;
import com.alibaba.dubbo.samples.validation.api.ValidationService;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ValidationConsumer
 */
public class ValidationConsumer {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/validation-consumer.xml"});
        context.start();

        ValidationService validationService = (ValidationService) context.getBean("validationService");

        // Save OK
        ValidationParameter parameter = new ValidationParameter();
        parameter.setName("liangfei");
        parameter.setEmail("liangfei@liang.fei");
        parameter.setAge(50);
        parameter.setLoginDate(new Date(System.currentTimeMillis() - 1000000));
        parameter.setExpiryDate(new Date(System.currentTimeMillis() + 1000000));
        validationService.save(parameter);
        System.out.println("Validation Save OK");

        // Save Error
        try {
            parameter = new ValidationParameter();
            validationService.save(parameter);
            System.err.println("Validation Save ERROR");
        } catch (Exception e) {
            ConstraintViolationException ve = (ConstraintViolationException) e;
            Set<ConstraintViolation<?>> violations = ve.getConstraintViolations();
            System.out.println(violations);
        }

        // Delete OK
        validationService.delete(2, "abc");
        System.out.println("Validation Delete OK");

        // Delete Error
        try {
            validationService.delete(0, "abc");
            System.err.println("Validation Delete ERROR");
        } catch (Exception e) {
            ConstraintViolationException ve = (ConstraintViolationException) e;
            Set<ConstraintViolation<?>> violations = ve.getConstraintViolations();
            System.out.println(violations);
        }
    }

}
