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
package org.apache.dubbo.test.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DelegateIT {

    @ParameterizedTest
    @ValueSource(classes = {
            ApplicationConfigDelegate.class,
            ArgumentConfigDelegate.class,
            ConsumerConfigDelegate.class,
            MethodConfigDelegate.class,
            ModuleConfigDelegate.class,
            MonitorConfigDelegate.class,
            ProtocolConfigDelegate.class,
            ProviderConfigDelegate.class,
            ReferenceConfigDelegate.class,
            ReferenceConfigDelegate.class,
            ServiceConfigDelegate.class
    })
    void test(Class<?> targetClass) throws Exception {
        Object object = targetClass.getDeclaredConstructor().newInstance();

        for (Method method : targetClass.getMethods()) {
            if (method.getName().startsWith("set")) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                Object[] parameters = new Object[parameterTypes.length];
                for (int i = 0; i < parameterTypes.length; i++) {
                    parameters[i] = null;
                }
                try {
                    method.invoke(object, parameters);
                } catch (InvocationTargetException e) {
                    Assertions.fail(e);
                }
            }
        }
    }

    @Test
    void constructorTest() {
        try {
            new ApplicationConfigDelegate();
            new ApplicationConfigDelegate("test");

            new ArgumentConfigDelegate();

            new ConsumerConfigDelegate();

            new MethodConfigDelegate();

            new ModuleConfigDelegate();
            new ModuleConfigDelegate("test");

            new MonitorConfigDelegate();
            new MonitorConfigDelegate("test");

            new ProtocolConfigDelegate();
            new ProtocolConfigDelegate("dubbo");
            new ProtocolConfigDelegate("dubbo", 1234);

            new ProviderConfigDelegate();

            new ReferenceConfigDelegate();

            new RegistryConfigDelegate();
            new RegistryConfigDelegate("nacso://test");
            new RegistryConfigDelegate("test", "nacos");

            new ServiceConfigDelegate();
        } catch (Throwable t) {
            Assertions.fail(t);
        }
    }
}
