package org.apache.dubbo.demo.consumer;

import org.apache.dubbo.common.config.configcenter.DynamicConfiguration;
import org.apache.dubbo.rpc.model.ApplicationModel;

public class UpgradeUtil {
    private static final String DUBBO_SERVICEDISCOVERY_MIGRATION = "DUBBO_SERVICEDISCOVERY_MIGRATION";

    public static void writeApplicationFirstRule(int proportion) {
        String content = "step: APPLICATION_FIRST\r\n" +
                "proportion: " + proportion;

        String RULE_KEY = ApplicationModel.getName() + ".migration";
        DynamicConfiguration configuration = ApplicationModel.getEnvironment().getDynamicConfiguration().get();
        configuration.publishConfig(RULE_KEY, DUBBO_SERVICEDISCOVERY_MIGRATION, content);
    }

    public static void writeForceInterfaceRule() {
        String content = "step: FORCE_INTERFACE\r\n" +
                "force: true";

        String RULE_KEY = ApplicationModel.getName() + ".migration";
        DynamicConfiguration configuration = ApplicationModel.getEnvironment().getDynamicConfiguration().get();
        configuration.publishConfig(RULE_KEY, DUBBO_SERVICEDISCOVERY_MIGRATION, content);
    }

    public static void writeForceApplicationRule() {
        String content = "step: FORCE_APPLICATION\r\n" +
                "force: true";

        String RULE_KEY = ApplicationModel.getName() + ".migration";
        DynamicConfiguration configuration = ApplicationModel.getEnvironment().getDynamicConfiguration().get();
        configuration.publishConfig(RULE_KEY, DUBBO_SERVICEDISCOVERY_MIGRATION, content);
    }

}
