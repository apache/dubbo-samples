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

import org.junit.Assert;
import org.junit.Test;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import static org.apache.dubbo.scenario.builder.VersionMatcher.CASE_VERSIONS_FILE;
import static org.apache.dubbo.scenario.builder.VersionMatcher.OUTPUT_FILE;
import static org.apache.dubbo.scenario.builder.VersionMatcher.CANDIDATE_VERSIONS;

public class VersionMatcherTest {

    @Test
    public void testVersionMatch1() throws Exception {
        String caseVersionRules = "# Spring app\n" +
                "dubbo.version=2.7*, 3.*\n" +
                "spring.version= 4.*, 5.*\n\n\n";

        String candidateVersions = "dubbo.version: 2.7.7,3.0;";
        candidateVersions += "spring.version:4.1.13.RELEASE, 5.3.2;";
        candidateVersions += "spring-boot.version:1.5.13.RELEASE,2.1.1.RELEASE;";

        String versionMatrix = getVersionMatrix(caseVersionRules, candidateVersions);
        Assert.assertTrue(versionMatrix.contains("-Ddubbo.version:2.7.7 -Dspring.version:4.1.13.RELEASE"));
        Assert.assertTrue(versionMatrix.contains("-Ddubbo.version:3.0 -Dspring.version:4.1.13.RELEASE"));
        Assert.assertTrue(versionMatrix.contains("-Ddubbo.version:2.7.7 -Dspring.version:5.3.2"));
        Assert.assertTrue(versionMatrix.contains("-Ddubbo.version:3.0 -Dspring.version:5.3.2"));
    }

    @Test
    public void testVersionMatch2() throws Exception {
        String caseVersionRules = "# SpringBoot app\n" +
                "dubbo.version=2.7*, 3.*\n" +
                "spring-boot.version=2.*\n\n\n";

        String candidateVersions = "dubbo.version:2.7.7,3.0\n";
        candidateVersions += "spring.version:4.1.13.RELEASE,5.3.2\n\n";
        candidateVersions += "spring-boot.version:1.5.13.RELEASE\n";
        candidateVersions += "spring-boot.version:2.1.1.RELEASE";

        String versionMatrix = getVersionMatrix(caseVersionRules, candidateVersions);
        Assert.assertTrue(versionMatrix.contains("-Ddubbo.version:2.7.7 -Dspring-boot.version:2.1.1.RELEASE"));
        Assert.assertTrue(versionMatrix.contains("-Ddubbo.version:3.0 -Dspring-boot.version:2.1.1.RELEASE"));
    }

    @Test
    public void testVersionMatchIncludeSpecificVersion() throws Exception {
        String caseVersionRules = "# SpringBoot app\n" +
                "dubbo.version=2.7*, 3.* \n" +
                "spring-boot.version= 2.0.8.RELEASE \n\n\n";

        String candidateVersions = "dubbo.version:2.7.7,3.0;";
        candidateVersions += "spring.version:4.1.13.RELEASE,5.3.2;";
        candidateVersions += "spring-boot.version:1.5.13.RELEASE,2.1.1.RELEASE;";

        String versionMatrix = getVersionMatrix(caseVersionRules, candidateVersions);
        Assert.assertTrue(versionMatrix.contains("-Ddubbo.version:2.7.7 -Dspring-boot.version:2.0.8.RELEASE"));
        Assert.assertTrue(versionMatrix.contains("-Ddubbo.version:3.0 -Dspring-boot.version:2.0.8.RELEASE"));
    }

    private String getVersionMatrix(String caseVersionRules, String candidateVersions) throws Exception {
        File caseVersionsFile = File.createTempFile("case-versions", ".conf");
        File outputFile = File.createTempFile("case-version-matrix", ".txt");
        caseVersionsFile.deleteOnExit();
        outputFile.deleteOnExit();

        writeToFile(caseVersionRules, caseVersionsFile);

        System.setProperty(CANDIDATE_VERSIONS, candidateVersions);
        System.setProperty(CASE_VERSIONS_FILE, caseVersionsFile.getAbsolutePath());
        System.setProperty(OUTPUT_FILE, outputFile.getAbsolutePath());

        VersionMatcher.main(new String[0]);

        return FileUtil.readFully(outputFile.getAbsolutePath());
    }

    private void writeToFile(String content, File file) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(file);
             DataOutputStream dos = new DataOutputStream(fos)) {
            dos.writeBytes(content);
        }
    }

}
