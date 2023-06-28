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
package org.apache.dubbo.samples.provider;

import org.apache.dubbo.samples.api.QosService;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.nio.charset.StandardCharsets;

public class QosServiceImpl implements QosService {
    @Override
    public long usedMemory() {
        String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        try {
            Process process = Runtime.getRuntime().exec(
                    String.format("jmap -histo:live %s", pid)
            );
            StringBuilder stringBuilder = new StringBuilder();
            InputStreamReader streamReader = new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8);
            do {
                char[] chars = new char[1024];
                int len;
                while ((len = streamReader.read(chars)) != -1) {
                    stringBuilder.append(chars, 0, len);
                }
            } while (process.isAlive());

            String[] strings = stringBuilder.toString().split("\n");
            for (String string : strings) {
                if (string.contains("Total")) {
                    String a = string.trim();
                    String trim = a.substring(a.lastIndexOf(" ")).trim();
                    return Long.parseLong(trim);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Runtime.getRuntime().maxMemory() - Runtime.getRuntime().freeMemory();
    }
}
