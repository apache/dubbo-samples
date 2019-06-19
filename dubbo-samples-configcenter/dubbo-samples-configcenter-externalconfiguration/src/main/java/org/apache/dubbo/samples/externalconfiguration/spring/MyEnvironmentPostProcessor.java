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
package org.apache.dubbo.samples.externalconfiguration.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * The users are responsible for gathering all Dubbo related configurations and put them into the standard
 * Spring environment mapping to a specified key, in this demo is 'dubbo.properties' and 'application.dubbo.properties'.
 */
public class MyEnvironmentPostProcessor implements EnvironmentPostProcessor {

    public MyEnvironmentPostProcessor() {
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        try {
            Map<String, Object> dubboProperties = new HashMap<>();
            BufferedReader reader = new BufferedReader(new InputStreamReader(MyEnvironmentPostProcessor.class.getResourceAsStream("/yourconfigcenter/dubbo-properties-in-configcenter.properties")));
            dubboProperties.put("dubbo.properties", reader.lines().collect(Collectors.joining("\n")));
            MapPropertySource dubboPropertySource = new MapPropertySource("dubbo.properties", dubboProperties);
            environment.getPropertySources().addLast(dubboPropertySource);

            Map<String, Object> appDubboProperties = new HashMap<>();
            BufferedReader appReader;
            String appName = application.getMainApplicationClass().getSimpleName();
            if (appName.contains("consumer")) {
                appReader = new BufferedReader(new InputStreamReader(MyEnvironmentPostProcessor.class.getResourceAsStream("/yourconfigcenter/dubbo-properties-in-configcenter-consumer.properties")));
                appDubboProperties.put("configcenter-annotation-consumer.dubbo.properties", appReader.lines().collect(Collectors.joining("\n")));
                MapPropertySource appDubboPropertySource = new MapPropertySource("configcenter-annotation-consumer.dubbo.properties", appDubboProperties);
                environment.getPropertySources().addLast(appDubboPropertySource);
            } else {
                appReader = new BufferedReader(new InputStreamReader(MyEnvironmentPostProcessor.class.getResourceAsStream("/yourconfigcenter/dubbo-properties-in-configcenter-provider.properties")));
                appDubboProperties.put("configcenter-annotation-provider.dubbo.properties", appReader.lines().collect(Collectors.joining("\n")));
                MapPropertySource appDubboPropertySource = new MapPropertySource("configcenter-annotation-provider.dubbo.properties", appDubboProperties);
                environment.getPropertySources().addLast(appDubboPropertySource);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
