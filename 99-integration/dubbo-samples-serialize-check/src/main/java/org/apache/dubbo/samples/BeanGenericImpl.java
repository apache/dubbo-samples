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
package org.apache.dubbo.samples;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.dubbo.common.beanutil.JavaBeanDescriptor;
import org.apache.dubbo.common.beanutil.JavaBeanSerializeUtil;
import org.apache.dubbo.rpc.service.GenericException;
import org.apache.dubbo.rpc.service.GenericService;

public class BeanGenericImpl implements GenericService {
    private Object target;

    public BeanGenericImpl(Object target) {
        this.target = target;
    }

    @Override
    public Object $invoke(String method, String[] parameterTypes, Object[] args) throws GenericException {
        AtomicReference<Object> result = new AtomicReference<>(null);
        Arrays.stream(target.getClass().getDeclaredMethods()).forEach(m -> {
            if (m.getName().equals(method)) {
                try {
                    m.setAccessible(true);
                    Object[] objects = new Object[args.length];
                    for (int i = 0; i < args.length; i++) {
                        objects[i] = JavaBeanSerializeUtil.deserialize((JavaBeanDescriptor) args[i]);
                    }
                    result.set(m.invoke(target, objects));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return JavaBeanSerializeUtil.serialize(result.get());
    }
}
