package org.apache.dubbo.samples.extensibility.protocol.common;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;

public class JsonSerializer {

    public static Object[] serializer(String json, Class<?>[] params) {
        if (params == null)
            return null;

        if (params.length == 0)
            return new Object[] {};

        JSONArray array = JSON.parseArray(json);

        if (array.size() < params.length) {
            throw new RuntimeException("传入参数数量小于实际数量");
        }

        Object[] objs = new Object[params.length];

        for (int i = 0; i < params.length; i++) {
            Class<?> clazz = params[i];

            Object o = array.get(i);

            Object res = parseObject(o, clazz);
            // Object res = JSON.parseObject(o.toString(), clazz);

            objs[i] = res;
        }
        return objs;
    }

    private static Object parseObject(Object o, Class<?> clazz) {
        String name = clazz.getName();
        if (name.equals(String.class.getName())) {
            return o.toString();
        }
        if (name.equals(Integer.class.getName())) {
            return Integer.parseInt(o.toString());
        }
        if (name.equals(Short.class.getName())) {
            return Short.parseShort(o.toString());
        }
        if (name.equals(Long.class.getName())) {
            return Long.parseLong(o.toString());
        }
        if (name.equals(Byte.class.getName())) {
            return Byte.parseByte(o.toString());
        }
        if (name.equals(Boolean.class.getName())) {
            return Boolean.parseBoolean(o.toString());
        }
        if (name.equals(Float.class.getName())) {
            return Float.parseFloat(o.toString());
        }
        if (name.equals(Double.class.getName())) {
            return Double.parseDouble(o.toString());
        }

        return JSON.parseObject(o.toString(), clazz);
    }

    public static String toJsonStr(Object object) {
        String name = object.getClass().getName();
        if (name.equals(String.class.getName())) {
            return object.toString();
        }
        if (name.equals(Integer.class.getName())) {
            return object.toString();
        }
        if (name.equals(Short.class.getName())) {
            return object.toString();
        }
        if (name.equals(Long.class.getName())) {
            return object.toString();
        }
        if (name.equals(Byte.class.getName())) {
            return object.toString();
        }
        if (name.equals(Boolean.class.getName())) {
            return object.toString();
        }
        if (name.equals(Float.class.getName())) {
            return object.toString();
        }
        if (name.equals(Double.class.getName())) {
            return object.toString();
        }

        return JSON.toJSONString(object);

    }
}
