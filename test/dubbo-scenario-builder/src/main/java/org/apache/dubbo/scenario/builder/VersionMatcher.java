/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.dubbo.scenario.builder;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Multi-version matcher
 */
public class VersionMatcher {

    private static final Logger logger = LoggerFactory.getLogger(VersionMatcher.class);

    /**
     * Java Property names
     */
    public static final String CASE_VERSIONS_FILE = "caseVersionsFile";
    public static final String CASE_VERSION_SOURCES_FILE = "caseVersionSourcesFile";
    public static final String CANDIDATE_VERSIONS = "candidateVersions";
    public static final String OUTPUT_FILE = "outputFile";
    public static final String ALL_REMOTE_VERSION = "ALL_REMOTE_VERSION";
    public static final String INCLUDE_CASE_SPECIFIC_VERSION = "includeCaseSpecificVersion";

    public static void main(String[] args) throws Exception {

        String caseVersionsFile = System.getProperty(CASE_VERSIONS_FILE);
        String caseVersionSourcesFile = System.getProperty(CASE_VERSION_SOURCES_FILE);
        String candidateVersionListStr = System.getProperty(CANDIDATE_VERSIONS);
        String outputFile = System.getProperty(OUTPUT_FILE);
        // whether include specific version which defined in case-versions.conf
        // specific version: a real version not contains wildcard '*'
        boolean includeCaseSpecificVersion = Boolean.parseBoolean(System.getProperty(INCLUDE_CASE_SPECIFIC_VERSION, "true"));

        if (StringUtils.isBlank(candidateVersionListStr)) {
            errorAndExit(Constants.EXIT_FAILED, "Missing system prop: '{}'", CANDIDATE_VERSIONS);
        }
        if (StringUtils.isBlank(caseVersionsFile)) {
            errorAndExit(Constants.EXIT_FAILED, "Missing system prop: '{}'", CASE_VERSIONS_FILE);
        }
        if (StringUtils.isBlank(caseVersionSourcesFile)) {
            errorAndExit(Constants.EXIT_FAILED, "Missing system prop: '{}'", CASE_VERSION_SOURCES_FILE);
        }
        File file = new File(caseVersionsFile);
        if (!file.exists() || !file.isFile()) {
            errorAndExit(Constants.EXIT_FAILED, "File not exists or isn't a file: {}", file.getAbsolutePath());
        }
        file = new File(caseVersionSourcesFile);
        if (!file.exists() || !file.isFile()) {
            caseVersionSourcesFile = null;
        }
        if (StringUtils.isBlank(outputFile)) {
            errorAndExit(Constants.EXIT_FAILED, "Missing system prop: '{}'", OUTPUT_FILE);
        }
        new File(outputFile).getParentFile().mkdirs();

        VersionMatcher versionMatcher = new VersionMatcher();
        versionMatcher.doMatch(caseVersionsFile, caseVersionSourcesFile, candidateVersionListStr, outputFile, includeCaseSpecificVersion);
    }

    private void doMatch(String caseVersionsFile, String caseVersionSourcesFile, String candidateVersionListStr, String outputFile, boolean includeCaseSpecificVersion) throws Exception {
        logger.info("{}: {}", CANDIDATE_VERSIONS, candidateVersionListStr);
        logger.info("{}: {}", CASE_VERSIONS_FILE, caseVersionsFile);
        logger.info("{}: {}", CASE_VERSION_SOURCES_FILE, caseVersionSourcesFile);
        logger.info("{}: {}", OUTPUT_FILE, outputFile);

        // parse and expand to versions list
        Map<String, List<String>> candidateVersionMap = parseVersionList(candidateVersionListStr);

        Map<String, List<String>> candidateVersionFromRemoteMap = parseVersionSources(caseVersionSourcesFile);

        // remote version first
        candidateVersionMap.forEach((k, v) ->
                candidateVersionFromRemoteMap.computeIfAbsent(k, i -> new ArrayList<>())
                        .addAll(v));

        // parse case version match rules
        Map<String, List<MatchRule>> caseVersionMatchRules = parseCaseVersionMatchRules(caseVersionsFile);

        // Filter caseVersionMatchRules
        chooseActiveDubboVersionRule(caseVersionMatchRules);

        Map<String, List<String>> matchedVersionMap = new LinkedHashMap<>();

        candidateVersionFromRemoteMap.forEach((component, candidateVersionList) -> {
            Map<String, List<MatchRule>> matchRulesMap;
            if (Constants.DUBBO_VERSION_KEY.equals(component) &&
                    !caseVersionMatchRules.containsKey(Constants.DUBBO_VERSION_KEY)
            ) {
                matchRulesMap = caseVersionMatchRules.keySet().stream()
                        .filter(key -> !extractDubboServiceName(key).isEmpty())
                        .collect(Collectors.toMap(
                                key -> key,
                                key -> caseVersionMatchRules.get(key)
                        ));
            } else {
                matchRulesMap = new HashMap<>();
                if (caseVersionMatchRules.get(component) != null) {
                    matchRulesMap.put(component, caseVersionMatchRules.get(component));
                }
            }

            if (matchRulesMap.isEmpty()) {
                return;
            }

            for (String matchRulesKey : matchRulesMap.keySet()) {
                List<MatchRule> matchRules = matchRulesMap.get(matchRulesKey);

                // matching rules
                List<String> matchedVersionList = new ArrayList<>();
                for (String version : candidateVersionList) {
                    if (hasIncludeVersion(matchRules, version)) {
                        matchedVersionList.add(version);
                    }
                }

                //add case specific version
                if (matchedVersionList.isEmpty() && includeCaseSpecificVersion) {
                    for (MatchRule matchRule : matchRules) {
                        if (!matchRule.isExcluded() && matchRule instanceof PlainMatchRule) {
                            matchedVersionList.add(((PlainMatchRule) matchRule).getVersion());
                        }
                    }
                }
                if (matchedVersionList.size() > 0) {
                    matchedVersionMap.put(matchRulesKey, matchedVersionList);
                }
            }
        });

        // Check whether all components have matched version
        if (caseVersionMatchRules.size() != matchedVersionMap.size()) {
            List<String> components = new ArrayList<>(caseVersionMatchRules.keySet());
            components.removeAll(matchedVersionMap.keySet());
            for (String component : components) {
                errorAndExit(Constants.EXIT_UNMATCHED, "Component not match: {}, rules: {}", component, caseVersionMatchRules.get(component));
            }
        }

        List<List<String>> versionProfiles = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : matchedVersionMap.entrySet()) {
            String component = entry.getKey();
            List<String> versions = entry.getValue();
            versionProfiles = appendComponent(versionProfiles, component, versions);
        }

        if (versionProfiles.isEmpty()) {
            errorAndExit(Constants.EXIT_UNMATCHED, "Version matrix is empty");
        }
        boolean onlyLatest = !Boolean.parseBoolean(System.getenv(ALL_REMOTE_VERSION));

        try (FileOutputStream fos = new FileOutputStream(outputFile);
             PrintWriter pw = new PrintWriter(fos)) {
            StringBuilder sb = new StringBuilder();
            int size = versionProfiles.size();
            for (int i = 0; i < (onlyLatest ? 1 : size); i++) {
                List<String> profile = versionProfiles.get(i);
                for (String version : profile) {
                    //-Dxxx.version=1.0.0
                    sb.append("-D").append(version.replace(':', '=')).append(" ");
                }
                sb.append("\n");
            }
            pw.print(sb);
            logger.info("Version matrix total: {}, list: \n{}", versionProfiles.size(), sb);
        } catch (IOException e) {
            errorAndExit(Constants.EXIT_FAILED, "Write version matrix failed: " + e.toString(), e);
        }
    }

    /**
     * Choose only one final active Dubbo version rule from following case:
     * <br>
     * 1. dubbo.version - original usage
     * <br>
     * 2. dubbo.{service}.version - different services can have different dubbo version for supporting compatibility-test
     *
     * @param caseVersionMatchRules
     */
    private static void chooseActiveDubboVersionRule(Map<String, List<MatchRule>> caseVersionMatchRules) {
        for (String key : caseVersionMatchRules.keySet()) {
            if (!extractDubboServiceName(key).isEmpty() && caseVersionMatchRules.get(Constants.DUBBO_VERSION_KEY) != null) {
                errorAndExit(Constants.EXIT_FAILED, "The config item dubbo.version and dubbo.{service}.version can't appear simultaneously");
            }
        }
    }

    /**
     * Extract service name from the given key
     *
     * @return the key or empty string when not matched.
     */
    public static String extractDubboServiceName(String key) {
        Matcher matcher = Constants.PATTERN_DUBBO_VERSION.matcher(key);
        if (matcher.matches()) {
            return matcher.group(1);
        }

        return "";
    }

    private static boolean hasIncludeVersion(List<MatchRule> matchRules, String version) {
        boolean included = false;
        String trimVersion = trimVersion(version);
        for (MatchRule matchRule : matchRules) {
            if (matchRule.match(matchRule instanceof WildcardMatchRule ? version : trimVersion)) {
                // excluded rule has higher priority than included rule
                if (matchRule.isExcluded()) {
                    return false;
                } else {
                    included = true;
                }
            }
        }
        return included;
    }

    private static Pattern getWildcardPattern(String versionPattern) {
        // convert 'version_prefix*' to regex
        String regex = "\\Q" + versionPattern.replace("*", "\\E.*?\\Q") + "\\E";
        return Pattern.compile(regex);
    }

    private static List<List<String>> appendComponent(List<List<String>> versionProfiles, String component, List<String> versions) {
        List<List<String>> newProfiles = new ArrayList<>();
        for (String version : versions) {
            String versionProfile = createVersionProfile(component, version);
            if (versionProfiles.isEmpty()) {
                List<String> newProfile = new ArrayList<>();
                newProfile.add(versionProfile);
                newProfiles.add(newProfile);
            } else {
                //extends version matrix
                for (int i = 0; i < versionProfiles.size(); i++) {
                    List<String> profile = versionProfiles.get(i);
                    List<String> newProfile = new ArrayList<>(profile);
                    newProfile.add(versionProfile);
                    newProfiles.add(newProfile);
                }
            }
        }
        return newProfiles;
    }

    private static String createVersionProfile(String component, String version) {
        return component + ":" + version;
    }

    private Map<String, List<MatchRule>> parseCaseVersionMatchRules(String caseVersionsFile) throws Exception {
        // Possible formats:
        //dubbo.version=2.7*, 3.*, !2.7.8*, !2.7.8.1
        //dubbo.version=<=2.7.7, >2.7.8, >=3.0
        //dubbo.version=[<=2.7.7, >2.7.8, >=3.0]
        //dubbo.version=["<=2.7.7", ">2.7.8", ">=3.0"]
        //dubbo.version=['<=2.7.7', '>2.7.8', '>=3.0']

        try {
            Map<String, List<MatchRule>> ruleMap = new LinkedHashMap<>();
            String content = FileUtil.readFully(caseVersionsFile);
            BufferedReader br = new BufferedReader(new StringReader(content));
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("#") || StringUtils.isBlank(line)) {
                    continue;
                }
                int p = line.indexOf('=');
                String component = line.substring(0, p);
                String serviceName = extractDubboServiceName(component);
                String patternStr = line.substring(p + 1);
                patternStr = trimRule(patternStr, "[", "]");
                String[] patterns = patternStr.split(",");
                List<MatchRule> ruleList = new ArrayList<>();
                for (String pattern : patterns) {
                    pattern = trimRule(pattern, "\"");
                    pattern = trimRule(pattern, "'");
                    if (pattern.startsWith(">") || pattern.startsWith("<")) {
                        ruleList.add(parseRangeMatchRule(pattern));
                    } else {
                        boolean excluded = false;
                        if (pattern.startsWith("!")) {
                            excluded = true;
                            pattern = pattern.substring(1).trim();
                        }
                        if (pattern.contains("*")) {
                            ruleList.add(new WildcardMatchRule(excluded, pattern, serviceName));
                        } else {
                            ruleList.add(new PlainMatchRule(excluded, pattern, serviceName));
                        }
                    }
                }
                ruleMap.put(component, ruleList);
            }
            return ruleMap;
        } catch (Exception e) {
            logger.error("Parse case versions rules failed: {}", caseVersionsFile, e);
            throw e;
        }
    }

    private String trimRule(String rule, String ch) {
        return trimRule(rule, ch, ch);
    }

    private String trimRule(String rule, String begin, String end) {
        rule = rule.trim();
        if (rule.startsWith(begin)) {
            if (rule.endsWith(end)) {
                return rule.substring(1, rule.length() - 1);
            } else {
                throw new IllegalArgumentException("Version match rule is invalid: " + rule);
            }
        }
        return rule;
    }

    private Map<String, List<String>> parseVersionList(String versionListStr) {
        // "<component1>:<version1>[,version2];<component2>:<version1>[,version2]"
        // "<component1>:<version1>[;component1:<version2];<component2>:<version1>[;component2:version2];"

        Map<String, List<String>> versionMap = new LinkedHashMap<>();
        //split components by ';' or '\n'
        String[] compvers = versionListStr.split("[;\n]");
        for (String compver : compvers) {
            if (StringUtils.isBlank(compver)) {
                continue;
            }
            String[] strs = compver.split(":");
            String component = strs[0].trim();
            String[] vers = strs[1].split(",");
            List<String> versionList = versionMap.computeIfAbsent(component, (key) -> new ArrayList<>());
            for (String ver : vers) {
                versionList.add(ver.trim());
            }
        }
        return versionMap;
    }

    private Map<String, List<String>> parseVersionSources(String caseVersionSourcesFile) {
        Map<String, List<String>> versionMap = new LinkedHashMap<>();
        if (caseVersionSourcesFile == null) {
            return versionMap;
        }

        Map<String, String> sources = new HashMap<>();
        try {
            String content = FileUtil.readFully(caseVersionSourcesFile);
            BufferedReader br = new BufferedReader(new StringReader(content));
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("#") || StringUtils.isBlank(line)) {
                    continue;
                }
                int p = line.indexOf('=');
                String component = line.substring(0, p).trim();
                String source = line.substring(p + 1).trim();
                sources.put(component, source);
            }
        } catch (IOException e) {
            errorAndExit(Constants.EXIT_FAILED, "Parse case versions sources failed: {}", caseVersionSourcesFile, e);
        }

        try (AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient()) {
            for (Map.Entry<String, String> entry : sources.entrySet()) {
                String component = entry.getKey();
                String source = entry.getValue();
                if (source.contains("repo1.maven.org")) {
                    // maven
                    Future<Response> f = asyncHttpClient.prepareGet(source).execute();
                    Response r = f.get();
                    if (r.hasResponseBody()) {
                        SAXReader reader = new SAXReader();
                        Document document = reader.read(r.getResponseBodyAsStream());

                        List<String> result = document.getRootElement()
                                .element("versioning")
                                .element("versions")
                                .elements("version")
                                .stream()
                                .map(Element::getText)
                                .collect(Collectors.toList());
                        versionMap.put(component, result);
                    } else {
                        errorAndExit(Constants.EXIT_FAILED, "Request remote versions failed: {}", r);
                    }
                } else if (source.contains("hub")) {
                    // Dockerhub
                    while (source != null) {
                        Future<Response> f = asyncHttpClient.prepareGet(source).execute();
                        Response r = f.get();
                        if (r.hasResponseBody() && r.getStatusCode() == 200) {
                            JSONObject root = JSON.parseObject(r.getResponseBody());
                            source = root.getString("next");
                            JSONArray results = root.getJSONArray("results");
                            for (int i = 0; i < results.size(); i++) {
                                JSONObject jsonObject = results.getJSONObject(i);
                                String tag = jsonObject.getString("name");
                                versionMap.computeIfAbsent(component, k -> new ArrayList<>()).add(tag);
                            }
                        } else {
                            errorAndExit(Constants.EXIT_FAILED, "Request remote versions failed: {}", r);
                        }
                    }
                } else {
                    errorAndExit(Constants.EXIT_FAILED, "Not supported remote type: {}", source);
                }
            }
        } catch (IOException | InterruptedException | ExecutionException | DocumentException e) {
            errorAndExit(Constants.EXIT_FAILED, "Request remote versions failed: {}", caseVersionSourcesFile, e);
        }

        return versionMap;
    }

    private static void errorAndExit(int exitCode, String format, Object... arguments) {
        //insert ERROR_MSG_FLAG before error msg
        Object[] newArgs = new Object[arguments.length + 1];
        newArgs[0] = Constants.ERROR_MSG_FLAG;
        System.arraycopy(arguments, 0, newArgs, 1, arguments.length);
        logger.error("{} " + format, newArgs);
        System.exit(exitCode);
    }

    private interface MatchRule {
        boolean isExcluded();

        boolean match(String version);


        /**
         * Get service the MatchRule bind to
         *
         * @return
         */
        default String getServiceName() {
            return null;
        }
    }

    private static abstract class ExcludableMatchRule implements VersionMatcher.MatchRule {
        boolean excluded;
        protected String serviceName;

        public ExcludableMatchRule(boolean excluded) {
            this.excluded = excluded;
        }

        @Override
        public boolean isExcluded() {
            return excluded;
        }

        @Override
        public String getServiceName() {
            return serviceName;
        }
    }

    private static class PlainMatchRule extends ExcludableMatchRule {
        private String version;

        public PlainMatchRule(boolean excluded, String version, String serviceName) {
            super(excluded);
            this.version = version;
            this.serviceName = serviceName;
        }

        @Override
        public boolean match(String version) {
            return this.version.equals(version);
        }

        public String getVersion() {
            return version;
        }

        @Override
        public String toString() {
            return (excluded ? "!" : "") + version;
        }
    }

    private static class WildcardMatchRule extends ExcludableMatchRule {
        private Pattern versionPattern;
        private String versionWildcard;

        public WildcardMatchRule(boolean excluded, String versionWildcard, String serviceName) {
            super(excluded);
            this.versionPattern = getWildcardPattern(versionWildcard);
            this.versionWildcard = versionWildcard;
            this.serviceName = serviceName;
        }

        @Override
        public boolean match(String version) {
            return this.versionPattern.matcher(version).matches();
        }

        @Override
        public String toString() {
            return (excluded ? "!" : "") + versionWildcard;
        }
    }

    private static class RangeMatchRule implements VersionMatcher.MatchRule {
        private VersionComparator comparator;
        private String operator;
        private String version;
        private int[] versionInts;

        public RangeMatchRule(String operator, String version) {
            this.operator = operator;
            this.version = version;
            this.comparator = getVersionComparator(operator);
            this.versionInts = toVersionInts(version);
        }

        @Override
        public boolean isExcluded() {
            return false;
        }

        @Override
        public boolean match(String matchingVersion) {
            int[] matchingVersionInts = toVersionInts(matchingVersion);
            return comparator.match(matchingVersionInts, versionInts);
        }

        @Override
        public String toString() {
            return operator + version;
        }
    }

    private static class CombineMatchRule implements VersionMatcher.MatchRule {
        List<VersionMatcher.MatchRule> matchRules = new ArrayList<>();

        public CombineMatchRule(List<VersionMatcher.MatchRule> matchRules) {
            this.matchRules.addAll(matchRules);
        }

        @Override
        public boolean isExcluded() {
            return false;
        }

        @Override
        public boolean match(String version) {
            for (VersionMatcher.MatchRule matchRule : matchRules) {
                if (!matchRule.match(version)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (VersionMatcher.MatchRule rule : matchRules) {
                sb.append(rule.toString()).append(' ');
            }
            return sb.toString();
        }
    }

    private static int[] toVersionInts(String version) {
        String[] vers = StringUtils.split(version, '.');
        int[] ints = new int[4];
        for (int i = 0; i < ints.length; i++) {
            if (vers.length > i) {
                try {
                    ints[i] = Integer.parseInt(vers[i]);
                } catch (NumberFormatException e) {
                    //ignore part
                }
            } else {
                break;
            }
        }
        return ints;
    }

    private static String trimVersion(String version) {
        //remove '-' suffix, such as '-SNAPSHOT'
        int p = version.indexOf('-');
        if (p > 0) {
            version = version.substring(0, p);
        }
        return version;
    }

    private static VersionComparator getVersionComparator(String operator) {
        if (operator.startsWith(">=")) {
            return greaterThanOrEqualToComparator;
        } else if (operator.startsWith(">")) {
            return greaterThanComparator;
        } else if (operator.startsWith("<=")) {
            return lessThanOrEqualToComparator;
        } else if (operator.startsWith("<")) {
            return lessThanComparator;
        }
        throw new IllegalArgumentException("Comparison operator is invalid: " + operator);
    }

    private Pattern cmpExprPattern = Pattern.compile("<=|>=|<|>|[\\d\\.]+");

    private MatchRule parseRangeMatchRule(String versionPattern) {
        List<MatchRule> matchRules = new ArrayList<>();
        Matcher matcher = cmpExprPattern.matcher(versionPattern);
        while (matcher.find()) {
            if (matchRules.size() == 2) {
                throw new IllegalArgumentException("Invalid range match rule: " + versionPattern);
            }
            String operator = matcher.group();
            if (!matcher.find()) {
                throw new IllegalArgumentException("Parse range match rule failed, unexpected EOF: " + versionPattern);
            }
            String version = matcher.group();
            matchRules.add(new RangeMatchRule(operator, version));
        }

        if (matchRules.size() == 1) {
            return matchRules.get(0);
        } else if (matchRules.size() == 2) {
            return new CombineMatchRule(matchRules);
        }
        throw new IllegalArgumentException("Parse range match rule failed: " + versionPattern);
    }

    private interface VersionComparator {
        boolean match(int[] matchingVersionInts, int[] versionInts);
    }

    private static VersionComparator greaterThanComparator = new VersionComparator() {
        @Override
        public boolean match(int[] matchingVersionInts, int[] versionInts) {
            for (int i = 0; i < versionInts.length; i++) {
                if (matchingVersionInts[i] > versionInts[i]) {
                    return true;
                } else if (matchingVersionInts[i] < versionInts[i]) {
                    return false;
                }
            }
            return false;
        }
    };

    private static VersionComparator greaterThanOrEqualToComparator = new VersionComparator() {
        @Override
        public boolean match(int[] matchingVersionInts, int[] versionInts) {
            return Arrays.equals(matchingVersionInts, versionInts) ||
                    greaterThanComparator.match(matchingVersionInts, versionInts);
        }
    };

    private static VersionComparator lessThanComparator = new VersionComparator() {
        @Override
        public boolean match(int[] matchingVersionInts, int[] versionInts) {
            for (int i = 0; i < versionInts.length; i++) {
                if (matchingVersionInts[i] < versionInts[i]) {
                    return true;
                } else if (matchingVersionInts[i] > versionInts[i]) {
                    return false;
                }
            }
            return false;
        }
    };

    private static VersionComparator lessThanOrEqualToComparator = new VersionComparator() {
        @Override
        public boolean match(int[] matchingVersionInts, int[] versionInts) {
            return Arrays.equals(matchingVersionInts, versionInts) ||
                    lessThanComparator.match(matchingVersionInts, versionInts);
        }
    };

}
