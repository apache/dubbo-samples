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

package org.apache.dubbo.samples.metrics.prometheus.util;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Pattern;

public final class PrometheusMetricAssert {

    private static final String[] RESERVED_SUFFIXES = {
            "_total", "_created", "_bucket", "_info",
            ".total", ".created", ".bucket", ".info"
    };

    private PrometheusMetricAssert() {
    }

    public enum MetricKind {
        COUNTER,
        GAUGE,
        INFO,
        HISTOGRAM,
        SUMMARY,
        UNKNOWN
    }

    public static void assertDubboMetricExposed(String scrapeText, String dubboRawName, MetricKind kind) {
        Set<String> compatibleNames = resolveCompatibleNames(dubboRawName, kind);
        boolean matched = compatibleNames.stream().anyMatch(name -> containsMetricLine(scrapeText, name));
        if (!matched) {
            throw new AssertionError("Expected dubbo metric to be exposed. rawName=" + dubboRawName
                    + ", kind=" + kind
                    + ", compatibleNames=" + compatibleNames);
        }
    }

    public static Set<String> resolveCompatibleNames(String dubboRawName, MetricKind kind) {
        Set<String> names = new LinkedHashSet<>();
        names.add(legacyPrometheusName(dubboRawName, kind));
        names.add(newPrometheusName(dubboRawName, kind));
        return names;
    }

    private static boolean containsMetricLine(String scrapeText, String metricName) {
        Pattern pattern = Pattern.compile("(?m)^" + Pattern.quote(metricName) + "(\\{|\\s).*");
        return pattern.matcher(scrapeText).find();
    }

    private static String legacyPrometheusName(String rawName, MetricKind kind) {
        String conventionName = snakeCase(rawName);
        if (kind == MetricKind.COUNTER && !conventionName.endsWith("_total")) {
            conventionName += "_total";
        }

        String sanitized = conventionName.replaceAll("[^a-zA-Z0-9_:]", "_");
        if (sanitized.isEmpty()) {
            return "m_";
        }
        if (!Character.isLetter(sanitized.charAt(0))) {
            return "m_" + sanitized;
        }
        return sanitized;
    }

    private static String snakeCase(String rawName) {
        StringBuilder result = new StringBuilder();
        char[] chars = rawName.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char current = chars[i];
            if (Character.isUpperCase(current)) {
                if (i > 0 && Character.isLowerCase(chars[i - 1])) {
                    result.append('_');
                }
                result.append(Character.toLowerCase(current));
            } else if (Character.isLetterOrDigit(current) || current == '_') {
                result.append(current);
            } else {
                result.append('_');
            }
        }
        return result.toString();
    }

    private static String newPrometheusName(String rawName, MetricKind kind) {
        String baseName = stripReservedSuffixesRepeatedly(rawName);
        String prometheusName = sanitizePrometheusName(baseName.replace('.', '_'));

        switch (kind) {
            case COUNTER:
                return appendSuffixIfAbsent(prometheusName, "_total");
            case INFO:
                return appendSuffixIfAbsent(prometheusName, "_info");
            case GAUGE:
            case UNKNOWN:
            case HISTOGRAM:
            case SUMMARY:
            default:
                return prometheusName;
        }
    }

    private static String appendSuffixIfAbsent(String metricName, String suffix) {
        if (metricName.endsWith(suffix)) {
            return metricName;
        }
        return metricName + suffix;
    }

    private static String stripReservedSuffixesRepeatedly(String rawName) {
        String value = rawName;
        boolean stripped;
        do {
            stripped = false;
            for (String suffix : RESERVED_SUFFIXES) {
                if (value.endsWith(suffix)) {
                    value = value.substring(0, value.length() - suffix.length());
                    stripped = true;
                    break;
                }
            }
        } while (stripped);
        return value;
    }

    private static String sanitizePrometheusName(String value) {
        String sanitized = value.replaceAll("[^a-zA-Z0-9_:]", "_");
        if (sanitized.isEmpty()) {
            return "m_";
        }
        if (!Character.isLetter(sanitized.charAt(0))) {
            return "m_" + sanitized;
        }
        return sanitized;
    }
}
