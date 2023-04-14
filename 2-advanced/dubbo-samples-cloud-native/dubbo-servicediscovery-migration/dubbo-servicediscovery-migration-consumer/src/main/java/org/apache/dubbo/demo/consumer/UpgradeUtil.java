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

import org.apache.dubbo.common.config.configcenter.DynamicConfiguration;
import org.apache.dubbo.rpc.model.ApplicationModel;

public class UpgradeUtil {
    private static final String DUBBO_SERVICEDISCOVERY_MIGRATION = "DUBBO_SERVICEDISCOVERY_MIGRATION";

    public static void clearRule(ApplicationModel applicationModel) {
        String RULE_KEY = applicationModel.getApplicationName() + ".migration";
        DynamicConfiguration configuration = applicationModel.getModelEnvironment().getDynamicConfiguration().get();
        configuration.publishConfig(RULE_KEY, DUBBO_SERVICEDISCOVERY_MIGRATION, "");
    }

    public static void writeApplicationFirstRule(int proportion) {
        String content = "step: APPLICATION_FIRST\r\n" +
                "proportion: " + proportion;

        String RULE_KEY = ApplicationModel.defaultModel().getApplicationName() + ".migration";
        DynamicConfiguration configuration = ApplicationModel.defaultModel().getModelEnvironment().getDynamicConfiguration().get();
        configuration.publishConfig(RULE_KEY, DUBBO_SERVICEDISCOVERY_MIGRATION, content);
    }

    public static void writeForceInterfaceRule() {
        String content = "step: FORCE_INTERFACE\r\n" +
                "force: true";

        String RULE_KEY = ApplicationModel.defaultModel().getApplicationName() + ".migration";
        DynamicConfiguration configuration = ApplicationModel.defaultModel().getModelEnvironment().getDynamicConfiguration().get();
        configuration.publishConfig(RULE_KEY, DUBBO_SERVICEDISCOVERY_MIGRATION, content);
    }

    public static void writeForceApplicationRule() {
        String content = "step: FORCE_APPLICATION\r\n" +
                "force: true";

        String RULE_KEY = ApplicationModel.defaultModel().getApplicationName() + ".migration";
        DynamicConfiguration configuration = ApplicationModel.defaultModel().getModelEnvironment().getDynamicConfiguration().get();
        configuration.publishConfig(RULE_KEY, DUBBO_SERVICEDISCOVERY_MIGRATION, content);
    }

}
