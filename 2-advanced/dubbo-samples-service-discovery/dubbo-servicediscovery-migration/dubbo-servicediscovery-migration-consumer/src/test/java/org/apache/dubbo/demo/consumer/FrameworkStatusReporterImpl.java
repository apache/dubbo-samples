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
package org.apache.dubbo.demo.consumer;

import com.google.gson.Gson;
import org.apache.dubbo.common.status.reporter.FrameworkStatusReportService;
import org.apache.dubbo.common.status.reporter.FrameworkStatusReporter;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class FrameworkStatusReporterImpl implements FrameworkStatusReporter {
    private static String expectedStep = "";
    private static CountDownLatch latch = null;

    @Override
    public void report(String type, Object obj) {
        if (latch != null && FrameworkStatusReportService.MIGRATION_STEP_STATUS.equals(type) && obj instanceof String) {
            Map<?, ?> map = new Gson().fromJson((String) obj, Map.class);
            Object newStep = map.get("newStep");
            Object success = map.get("success");
            if (expectedStep.equals(newStep) && "true".equals(success)) {
                latch.countDown();
            }
        }
    }

    public static boolean waitReport(long ms) throws InterruptedException {
        if (latch != null) {
            return latch.await(ms, TimeUnit.MILLISECONDS);
        }
        return false;
    }

    public static void prepare(String newStep, int expectedMigrationReportCount) {
        expectedStep = newStep;
        latch = new CountDownLatch(expectedMigrationReportCount);
    }
}
