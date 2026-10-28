package org.apache.dubbo.provider.util;

import cn.hutool.core.util.ClassUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author 杨泽翰
 */

public class AotUtil {

    public static List<Set<Class<?>>> getClassBySupport(List<String> basePackagelist, Class app) {
        List<Set<Class<?>>> setList = new ArrayList<>();
        basePackagelist.forEach(basePackage -> {
            Set<Class<?>> classes = ClassUtil.scanPackageBySuper(basePackage, app);
            setList.add(classes);
        });
        return setList;
    }

    public static void main(String[] args) {
        List<String> basePackagelist = new ArrayList<>();
        basePackagelist.add("org.apache.dubbo");
        List<Set<Class<?>>> classBySupport = getClassBySupport(basePackagelist, Serializable.class);
        classBySupport.forEach(set -> set.forEach(item -> {
            Class<?>[] declaredClasses = item.getDeclaredClasses();
            if (declaredClasses.length != 0) {
                Arrays.stream(declaredClasses).forEach(System.out::println);
            }
        }));
        System.out.println(classBySupport);
    }

}
