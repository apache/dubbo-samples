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

public class JacocoMerge {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            throw new IllegalArgumentException("Please specify the base path of the jacoco.exec file");
        }
        String basePath = args[0];
        List<File> files = loadExecFiles(new File(basePath));
        if (files.isEmpty()) {
            return;
        }
        String[] sources = files.stream()
                .map(File::getAbsolutePath)
                .toArray(String[]::new);
        System.out.println("Found " + sources.length + " jacoco.exec files");
        String[] mergeArgs = new String[sources.length + 3];
        mergeArgs[0] = "merge";
        System.arraycopy(sources, 0, mergeArgs, 1, sources.length);
        mergeArgs[mergeArgs.length - 2] = "--destfile";
        mergeArgs[mergeArgs.length - 1] = basePath + "/target/jacoco" + System.currentTimeMillis() + ".exec";

        Main.main(mergeArgs);
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
        } else if (baseFile.isFile() && baseFile.getAbsolutePath().contains("target-jacoco") && baseFile.getName().endsWith(".exec")) {
            result.add(baseFile);
        }
        return result;
    }
}
