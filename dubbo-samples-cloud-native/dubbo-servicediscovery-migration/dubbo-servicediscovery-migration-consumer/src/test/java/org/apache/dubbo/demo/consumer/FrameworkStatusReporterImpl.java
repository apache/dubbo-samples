package org.apache.dubbo.demo.consumer;

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
