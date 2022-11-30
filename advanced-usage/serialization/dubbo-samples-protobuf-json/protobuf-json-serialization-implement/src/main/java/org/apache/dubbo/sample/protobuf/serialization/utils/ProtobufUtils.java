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

package org.apache.dubbo.sample.protobuf.serialization.utils;

import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;
import com.google.protobuf.Parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProtobufUtils {
    private final Map<String, Class> className2ClassMap = new ConcurrentHashMap();
    private static final ProtobufUtils protobufUtils = new ProtobufUtils();
    private final Map<Class, Parser<?>> class2ParserMap = new ConcurrentHashMap<>();
    private final Object className2ClassMapLock = new Object();
    private final Object class2ParserMapLock = new Object();
    private ClassLoader classLoader;

    private ProtobufUtils() {
        this.classLoader = Thread.currentThread().getContextClassLoader();
    }

    public static ProtobufUtils getInstance() {
        return protobufUtils;
    }

    public boolean canSerializeWithProtobuf(Class<?> clazz) {
        if (clazz == null) {
            return false;
        }

        if (GeneratedMessageV3.class.isAssignableFrom(MessageLite.class)) {
            return true;
        }

        if (Map.class.isAssignableFrom(clazz) || Array.class.isAssignableFrom(clazz) || List.class.isAssignableFrom(clazz)
                || String.class.isAssignableFrom(clazz) || Number.class.isAssignableFrom(clazz)
                || Boolean.class.isAssignableFrom(clazz) || Throwable.class.isAssignableFrom(clazz) || clazz.isArray()
                || clazz.isEnum()) {
            return false;
        }
        return true;
    }


    public void serializeWithProtobuf(Object o, OutputStream os) {
        if (!(o instanceof MessageLite)) {
            return;
        }
        try {
            ((MessageLite) o).writeTo(os);
        } catch (IOException e) {
            throw new RuntimeException("Google PB序列化失败，序列化对象的类型为" + o.getClass().getName(), e);
        }
    }

    public Class loadClass(String className) throws ClassNotFoundException {
        Class clazz = className2ClassMap.get(className);
        if (clazz == null) {
            synchronized (className2ClassMapLock) {
                clazz = className2ClassMap.get(className);
                if (clazz == null) {
                    clazz = Class.forName(className, false, classLoader);
                    className2ClassMap.put(className, clazz);
                }
            }
        }
        return clazz;
    }

    public <T> T deserializeWithProtobuf(InputStream is, Class<T> clazz) throws InvalidProtocolBufferException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (!(MessageLite.class.isAssignableFrom(clazz))) {
            return null;
        }
        Parser<?> parser = getParser(clazz);
        return (T) parser.parseFrom(is);
    }

    /**
     * According to <a href="https://developers.google.com/protocol-buffers/docs/reference/java-generated#message">Java
     * Generated Code</a>, every pb entity class has a parser method.
     */
    @SuppressWarnings("unchecked")
    private Parser<?> getParser(Class clazz)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Parser<?> parser = class2ParserMap.get(clazz);
        if (parser == null) {
            synchronized (class2ParserMapLock) {
                parser = class2ParserMap.get(clazz);
                if (parser == null) {
                    try {
                        Method parserMethod = clazz.getMethod("parser");
                        parser = (Parser<?>) parserMethod.invoke(clazz);
                    } catch (NoSuchMethodException ex) {
                        // try to find from PARSER field
                        try {
                            Field parserField = clazz.getField("PARSER");
                            parser = (Parser<?>) parserField.get(clazz);
                        } catch (NoSuchFieldException ex2) {
                            // throw NoSuchMethodException instead of NoSuchFieldException
                            throw ex;
                        }
                    }
                    class2ParserMap.put(clazz, parser);
                }
            }
        }
        return parser;
    }
}
