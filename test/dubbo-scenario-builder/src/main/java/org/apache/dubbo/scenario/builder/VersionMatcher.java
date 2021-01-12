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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Multi-version matcher
 */
public class VersionMatcher {

    private static final Logger logger = LoggerFactory.getLogger(VersionMatcher.class);
    public static final String CASE_VERSIONS_FILE = "caseVersionsFile";
    public static final String TEST_VERSIONS_LIST = "testVersionsList";
    public static final String OUTPUT_FILE = "outputFile";
    public static final String LIMIT = "limit";
    public static final String INCLUDE_CASE_SPECIFIC_VERSION = "includeCaseSpecificVersion";

    public static void main(String[] args) throws Exception {

        String caseVersionsFile = System.getProperty(CASE_VERSIONS_FILE);
        String testVersionListStr = System.getProperty(TEST_VERSIONS_LIST);
        String outputFile = System.getProperty(OUTPUT_FILE);
        int limit = Integer.parseInt(System.getProperty(LIMIT, "4"));
        // whether include specific version which defined in case-versions.conf
        // specific version: a real version not contains wildcard '*'
        boolean includeCaseSpecificVersion = Boolean.parseBoolean(System.getProperty(INCLUDE_CASE_SPECIFIC_VERSION, "true"));

        if (StringUtils.isBlank(testVersionListStr)) {
            logger.error("Missing system prop: '{}'", TEST_VERSIONS_LIST);
            System.exit(1);
        }
        if (StringUtils.isBlank(caseVersionsFile)) {
            logger.error("Missing system prop: '{}'", CASE_VERSIONS_FILE);
            System.exit(1);
        }
        File file = new File(caseVersionsFile);
        if (!file.exists() || !file.isFile()) {
            logger.error("file not exists or isn't a file: {}", file.getAbsolutePath());
            System.exit(1);
        }
        if (StringUtils.isBlank(outputFile)) {
            logger.error("Missing system prop: '{}'", OUTPUT_FILE);
            System.exit(1);
        }
        new File(outputFile).getParentFile().mkdirs();

        logger.info("{}: {}", TEST_VERSIONS_LIST, testVersionListStr);
        logger.info("{}: {}", CASE_VERSIONS_FILE, caseVersionsFile);
        logger.info("{}: {}", OUTPUT_FILE, outputFile);

        // parse and expand to versions list
        List<String> testVersionList = parseVersionList(testVersionListStr);

        // parse case version rules
        Map<String, List<String>> caseVersionRules = parseCaseVersionRules(caseVersionsFile);

        Map<String, List<String>> matchVersions = new LinkedHashMap<>();
        caseVersionRules.forEach((component, versionPatterns) -> {
            for (String versionPattern : versionPatterns) {
                // convert 'component:version_prefix*' to regex
                String regex = "\\Q" + component + ":" + versionPattern.replace("*", "\\E.*?\\Q") + "\\E";
                Pattern pattern = Pattern.compile(regex);
                boolean matched = false;
                for (String version : testVersionList) {
                    if (pattern.matcher(version).matches()) {
                        getMatchVersionList(matchVersions, component).add(version);
                        matched = true;
                    }
                }
                //add case specific version
                if (!matched && includeCaseSpecificVersion && !versionPattern.contains("*")) {
                    getMatchVersionList(matchVersions, component).add(component+":"+versionPattern);
                }
            }
        });

        if (caseVersionRules.size() != matchVersions.size()) {
            List<String> components = new ArrayList<>(caseVersionRules.keySet());
            components.removeAll(matchVersions.keySet());
            for (String component : components) {
                logger.error("Component not match, component: {}, rules: {}, testVersionList: {}", component, caseVersionRules.get(component), testVersionListStr);
            }
            System.exit(2);
        }

        List<List<String>> versionProfiles = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : matchVersions.entrySet()) {
            String component = entry.getKey();
            List<String> versions = entry.getValue();
            versionProfiles = appendComponent(versionProfiles, component, versions);
        }

        if (versionProfiles.isEmpty()) {
            logger.error("");
            System.exit(2);
        }
        if (versionProfiles.size() > limit) {
            logger.warn("Version matrix size exceeds limit and will be truncated, total: {}, limit: {}", versionProfiles.size(), limit);
        }
        try (FileOutputStream fos = new FileOutputStream(outputFile);
             PrintWriter pw = new PrintWriter(fos)) {
            StringBuilder sb = new StringBuilder();
            int size = Math.min(versionProfiles.size(), limit);
            for (int i = 0; i < size; i++) {
                List<String> profile = versionProfiles.get(i);
                for (String s : profile) {
                    sb.append("-D").append(s).append(" ");
                }
                sb.append("\n");
            }
            pw.print(sb);
            logger.info("Version matrix: \n{}", sb);
        } catch (IOException e) {
            logger.error("Write version matrix failed: " + e.toString(), e);
        }

    }

    private static List<String> getMatchVersionList(Map<String, List<String>> matchVersions, String component) {
        return matchVersions.computeIfAbsent(component, (key) -> new ArrayList<>());
    }

    private static List<List<String>> appendComponent(List<List<String>> versionProfiles, String component, List<String> versions) {
        List<List<String>> newProfiles = new ArrayList<>();
        for (String version : versions) {
            if (versionProfiles.isEmpty()) {
                List<String> newProfile = new ArrayList<>();
                newProfile.add(version);
                newProfiles.add(newProfile);
            } else {
                //extends version matrix
                for (int i = 0; i < versionProfiles.size(); i++) {
                    List<String> profile = versionProfiles.get(i);
                    List<String> newProfile = new ArrayList<>(profile);
                    newProfile.add(version);
                    newProfiles.add(newProfile);
                }
            }
        }
        return newProfiles;
    }

    private static Map<String, List<String>> parseCaseVersionRules(String caseVersionsFile) throws Exception {
        //dubbo.version=2.7*, 3.*
        //spring.version=4.*, 5.*
        try {
            Map<String, List<String>> ruleMap = new LinkedHashMap<>();
            String content = FileUtil.readFully(caseVersionsFile);
            BufferedReader br = new BufferedReader(new StringReader(content));
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("#") || StringUtils.isBlank(line)) {
                    continue;
                }
                String[] strs = line.split("=");
                String component = strs[0].trim();
                String patternStr = strs[1];
                String[] patterns = patternStr.split(",");
                List<String> patternList = new ArrayList<>();
                for (String pattern : patterns) {
                    patternList.add(pattern.trim());
                }
                ruleMap.put(component, patternList);
            }
            return ruleMap;
        } catch (Exception e) {
            logger.error("Parse case versions rules failed: {}", caseVersionsFile, e);
            throw e;
        }
    }

    private static List<String> parseVersionList(String versionListStr) {
        // "<component1>:<version1>[,version2];<component2>:<version1>[,version2]"
        // "<component1>:<version1>[;component1:<version2];<component2>:<version1>[;component2:version2];"

        List<String> versionList = new ArrayList<>();
        String[] compvers = versionListStr.split(";");
        for (String compver : compvers) {
            String[] strs = compver.split(":");
            String component = strs[0].trim();
            String[] vers = strs[1].split(",");
            for (String ver : vers) {
                versionList.add(component + ":" + ver.trim());
            }
        }
        return versionList;
    }
}
