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
package org.apache.dubbo.test;

import org.jacoco.cli.internal.Main;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class JacocoReport {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            throw new IllegalArgumentException("Please specify the base path of the jacoco.exec file");
        }
        String basePath = args[0];
        String dubboRepo = args[1];

        List<File> execFiles = loadExecFiles(new File(basePath + File.separator + "target"));
        List<File> classFiles = loadClassFiles(new File(dubboRepo));
        List<File> sourceFiles = loadSourceFiles(new File(dubboRepo));

        if (execFiles.isEmpty()) {
            System.out.println(basePath + File.separator + "target" + File.separator + "jacoco*.exec" + " does not exist");
            return;
        }


        String[] execs = execFiles.stream()
                .map(File::getAbsolutePath)
                .toArray(String[]::new);
        String[] classes = classFiles.stream()
                .map(File::getAbsolutePath)
                .flatMap(s -> Stream.of("--classfiles", s))
                .toArray(String[]::new);
        String[] sources = sourceFiles.stream()
                .map(File::getAbsolutePath)
                .flatMap(s -> Stream.of("--sourcefiles", s))
                .toArray(String[]::new);

        String[] reportArgs = new String[execs.length + classes.length + sources.length + 5];
        reportArgs[0] = "report";
        System.arraycopy(execs, 0, reportArgs, 1, execs.length);
        System.arraycopy(classes, 0, reportArgs, execs.length + 1, classes.length);
        System.arraycopy(sources, 0, reportArgs, execs.length + classes.length + 1, sources.length);
        reportArgs[execs.length + classes.length + sources.length + 1] = "--xml";
        reportArgs[execs.length + classes.length + sources.length + 2] = basePath + File.separator + "target" + File.separator + "report.xml";
        reportArgs[execs.length + classes.length + sources.length + 3] = "--html";
        reportArgs[execs.length + classes.length + sources.length + 4] = basePath + File.separator + "target" + File.separator + "site";

        Main.main(reportArgs);
    }


    private static List<File> loadClassFiles(File baseFile) {
        List<File> result = new LinkedList<>();
        if (baseFile.isDirectory()) {
            if (baseFile.getAbsolutePath().contains("dubbo-demo")) {
                return result;
            }
            if (baseFile.getAbsolutePath().contains("dubbo-native")) {
                return result;
            }
            if (new File(baseFile.getAbsolutePath() + File.separator + "target" + File.separator + "classes" + File.separator + "org" + File.separator + "apache" + File.separator + "dubbo").exists()) {
                result.add(new File(baseFile.getAbsolutePath() + File.separator + "target" + File.separator + "classes" + File.separator + "org" + File.separator + "apache" + File.separator + "dubbo"));
                return result;
            }
            File[] files = baseFile.listFiles();
            if (files != null) {
                for (File file : files) {
                    result.addAll(loadClassFiles(file));
                }
            }
        }
        return result;
    }


    private static List<File> loadExecFiles(File baseFile) {
        List<File> result = new LinkedList<>();
        if (baseFile.isDirectory()) {
            File[] files = baseFile.listFiles();
            if (files != null) {
                for (File file : files) {
                    result.addAll(loadExecFiles(file));
                }
            }
        } else if (baseFile.isFile() && baseFile.getName().endsWith(".exec")) {
            result.add(baseFile);
        }
        return result;
    }
    private static List<File> loadSourceFiles(File baseFile) {
        List<File> result = new LinkedList<>();
        if (baseFile.isDirectory()) {
            if (baseFile.getAbsolutePath().contains("dubbo-demo")) {
                return result;
            }
            if (baseFile.getAbsolutePath().contains("dubbo-native")) {
                return result;
            }
            if (new File(baseFile.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "java").exists()) {
                result.add(new File(baseFile.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "java"));
                return result;
            }
            File[] files = baseFile.listFiles();
            if (files != null) {
                for (File file : files) {
                    result.addAll(loadSourceFiles(file));
                }
            }
        }
        return result;
    }
}
