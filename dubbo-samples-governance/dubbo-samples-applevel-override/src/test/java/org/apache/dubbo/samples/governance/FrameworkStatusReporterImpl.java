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
package org.apache.dubbo.samples.governance;

import org.apache.dubbo.common.status.reporter.FrameworkStatusReporter;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class FrameworkStatusReporterImpl implements FrameworkStatusReporter {
    private static final Map<String, String> report = new HashMap<>();

    @Override
    public void report(String type, Object obj) {
        if (obj instanceof String) {
            Object group = new Gson().fromJson((String) obj, Map.class).get("group");
            report.put((String) group, (String) obj);
        }
    }

    public static Map<String, String> getReport() {
        return report;
    }

    public static void clearReport() {
        report.clear();
    }
}
