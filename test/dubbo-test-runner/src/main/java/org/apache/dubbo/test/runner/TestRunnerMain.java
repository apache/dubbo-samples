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

package org.apache.dubbo.test.runner;


import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.apache.maven.plugin.surefire.StartupReportConfiguration;
import org.apache.maven.plugin.surefire.log.api.ConsoleLogger;
import org.apache.maven.plugin.surefire.log.api.PrintStreamLogger;
import org.apache.maven.plugin.surefire.report.ConsoleReporter;
import org.apache.maven.plugin.surefire.util.DirectoryScanner;
import org.apache.maven.surefire.booter.AbstractPathConfiguration;
import org.apache.maven.surefire.booter.ClassLoaderConfiguration;
import org.apache.maven.surefire.booter.Classpath;
import org.apache.maven.surefire.booter.ClasspathConfiguration;
import org.apache.maven.surefire.booter.ProviderConfiguration;
import org.apache.maven.surefire.booter.Shutdown;
import org.apache.maven.surefire.booter.StartupConfiguration;
import org.apache.maven.surefire.booter.SurefireExecutionException;
import org.apache.maven.surefire.cli.CommandLineOption;
import org.apache.maven.surefire.junit4.JUnit4Provider;
import org.apache.maven.surefire.junitplatform.JUnitPlatformProvider;
import org.apache.maven.surefire.report.ReporterConfiguration;
import org.apache.maven.surefire.suite.RunResult;
import org.apache.maven.surefire.testset.DirectoryScannerParameters;
import org.apache.maven.surefire.testset.RunOrderParameters;
import org.apache.maven.surefire.testset.TestListResolver;
import org.apache.maven.surefire.testset.TestRequest;
import org.apache.maven.surefire.testset.TestSetFailedException;
import org.apache.maven.surefire.util.DefaultScanResult;

import static java.util.Collections.emptyList;

public class TestRunnerMain {

    public static void main(String[] args) throws Exception {

        if (args.length < 4) {
            throw new IllegalArgumentException("Invalid arguments, usage: TestRunnerMain <testClassesDir> <targetClassesDir> <dependencyJarsDir> <reportDir> [test-pattern1;test-pattern2]");
        }

        File testClassesDir = new File(args[0]);
        if (!testClassesDir.exists() || !testClassesDir.isDirectory()) {
            throw new IllegalArgumentException("testClassesDir is not exists or is not a directory: " + testClassesDir.getAbsolutePath());
        }
        File targetClassesDir = new File(args[1]);
        if (!targetClassesDir.exists() || !targetClassesDir.isDirectory()) {
            throw new IllegalArgumentException("targetClassesDir is not exists or is not a directory: " + targetClassesDir.getAbsolutePath());
        }
        File dependencyJarsDir = new File(args[2]);
        if (!dependencyJarsDir.exists() || !dependencyJarsDir.isDirectory()) {
            throw new IllegalArgumentException("dependencyJarsDir is not exists or is not a directory: " + dependencyJarsDir.getAbsolutePath());
        }

        File reportsDirectory = new File(args[3]);
        reportsDirectory.mkdirs();
        if (!reportsDirectory.exists() || !reportsDirectory.isDirectory()) {
            throw new IllegalArgumentException("reportDir is not exists or is not a directory: " + reportsDirectory.getAbsolutePath());
        }

        // tests pattern:  **/*IT.class;**/*Test.class
        List<String> tests = new ArrayList<>();
        if (args.length > 4) {
            String[] testPatterns = args[4].split(";");
            for (String pattern : testPatterns) {
                String s = pattern.trim();
                if (s.endsWith(".java")) {
                    s = s.replace(".java", ".class");
                }
                tests.add(s);
            }
        }

        System.out.println("testClassesDir: " + testClassesDir.getAbsolutePath());
        System.out.println("targetClassesDir: " + targetClassesDir.getAbsolutePath());
        System.out.println("dependencyJarsDir: " + dependencyJarsDir.getAbsolutePath());
        System.out.println("reportsDirectory: " + reportsDirectory.getAbsolutePath());
        System.out.println("test patterns: " + tests);


        File statisticsFile = new File(reportsDirectory, "test-statistics.txt");
        StartupReportConfiguration startupReportConfiguration = new StartupReportConfiguration(true,
                true,
                ConsoleReporter.PLAIN,
                false,
                true,
                reportsDirectory,
                false,
                "report",
                statisticsFile,
                true,
                10,
                null,
                "UTF-8",
                false
        );

        DirectoryScannerParameters directoryScannerParameters = new DirectoryScannerParameters(testClassesDir,
                emptyList(), emptyList(), emptyList(), false, "");

        File runStatisticsFile = new File(reportsDirectory, "run-statistics.txt");
        RunOrderParameters runOrderParameters = new RunOrderParameters((String) null, runStatisticsFile);

        TestListResolver requestTests = new TestListResolver(Collections.emptyList());
        TestRequest testRequest = new TestRequest(Collections.emptyList(), testClassesDir, requestTests);

        HashMap<String, String> providerProperties = new HashMap<>();

        ReporterConfiguration reporterConfiguration = new ReporterConfiguration(reportsDirectory, false);

        List<CommandLineOption> cliOptions = new ArrayList<>();
        cliOptions.add(CommandLineOption.LOGGING_LEVEL_ERROR);
        cliOptions.add(CommandLineOption.LOGGING_LEVEL_WARN);
        cliOptions.add(CommandLineOption.LOGGING_LEVEL_INFO);
        cliOptions.add(CommandLineOption.REACTOR_FAIL_FAST);

        ProviderConfiguration providerConfiguration = new ProviderConfiguration(
                directoryScannerParameters,
                runOrderParameters,
                false,
                reporterConfiguration,
                null,
                testRequest,
                providerProperties,
                null,
                false,
                cliOptions,
                0,
                Shutdown.DEFAULT,
                30
        );

        int runCodeJunit4 = runInJunit4(testClassesDir, targetClassesDir, dependencyJarsDir, tests, startupReportConfiguration, providerConfiguration);
        int runCodeJunit5 = runInJunit5(testClassesDir, targetClassesDir, dependencyJarsDir, tests, startupReportConfiguration, providerConfiguration);

        ConsoleLogger consoleLogger = new PrintStreamLogger(System.out);

        if (runCodeJunit4 == 255) {
            consoleLogger.info("There are some failure test in junit 4.");
            System.exit(1);
        }
        if (runCodeJunit5 == 255) {
            consoleLogger.info("There are some failure test in junit 5.");
            System.exit(1);
        }

        if (runCodeJunit4 == 254 && runCodeJunit5 == 254) {
            consoleLogger.info("No test case found both in junit 4 and junit 5.");
            System.exit(1);
        }

        boolean runSuccess = runCodeJunit4 == 0 || runCodeJunit5 == 0;

//        File tmpDirectory = new File(reportsDirectory, "tmp");
//        tmpDirectory.mkdirs();
//        ForkConfiguration forkConfiguration = new ClasspathForkConfiguration(inprocClasspath, tmpDirectory,  null,
//                reportsDirectory, new Properties(), "", System.getenv(), false, 1, true,
//                new Platform(), consoleLogger);
//        ForkStarter forkStarter = new ForkStarter(providerConfiguration,
//                startupConfiguration,
//                forkConfiguration,
//                30,
//                startupReportConfiguration,
//                consoleLogger);
//        RunResult runResult = forkStarter.run(new SurefireProperties(), scanResult);
//        System.out.println(String.format("RunResult: %d, Failures: %d, Errors: %d, failure: %s",
//                runResult.getCompletedCount(), runResult.getFailures(), runResult.getErrors(), runResult.getFailure()));

        if (runSuccess) {
            System.exit(0);
        } else {
            System.exit(1);
        }
    }

    private static int runInJunit4(File testClassesDir, File targetClassesDir, File dependencyJarsDir, List<String> tests, StartupReportConfiguration startupReportConfiguration, ProviderConfiguration providerConfiguration) throws SurefireExecutionException, TestSetFailedException {
        String providerClassName = JUnit4Provider.class.getName();//"org.apache.maven.surefire.junit4.JUnit4Provider";
        Classpath testClasspath = generateTestClasspath(testClassesDir, targetClassesDir, dependencyJarsDir);
        Classpath inprocClasspath = getInprocClasspath();
        Classpath surefireClasspath = inprocClasspath;
        AbstractPathConfiguration classpathConfiguration = new ClasspathConfiguration(
                testClasspath,
                surefireClasspath,
                inprocClasspath,
                false,
                false
        );

        ClassLoaderConfiguration classloaderConfiguration = new ClassLoaderConfiguration(false, false);
        StartupConfiguration startupConfiguration = new StartupConfiguration(
                providerClassName,
                classpathConfiguration,
                classloaderConfiguration,
                false,
                false);


        DefaultScanResult scanResult = getScanResult(testClassesDir, tests);
        ConsoleLogger consoleLogger = new PrintStreamLogger(System.out);
        consoleLogger.info("Run JUnit4 tests...");

        // Fix Class loading problem in java9+:
        // CompletableFuture.supplyAsync() is executed in the ForkJoinWorkerThread
        // and it only uses system classloader to load classes instead of the IsolatedClassLoader
        ClassloaderSurefireStarter testStarter = new ClassloaderSurefireStarter(startupConfiguration, providerConfiguration,
                startupReportConfiguration, consoleLogger, ClassLoader.getSystemClassLoader());
//        InPluginVMSurefireStarter testStarter = new InPluginVMSurefireStarter(startupConfiguration, providerConfiguration,
//                startupReportConfiguration, consoleLogger);

        RunResult runResult = testStarter.runSuitesInProcess(scanResult);
        boolean runSuccess = runResult.getCompletedCount() > 0 && runResult.isErrorFree() && !runResult.isTimeout();

        String line = "------------------------------------------------------------------------\n";
        consoleLogger.info(String.format(line + "Junit4: TEST %s, Total: %d, Failures: %d, Errors: %d, Skipped: %d\n" + line,
                runSuccess ? "SUCCESS" : "FAILURE", runResult.getCompletedCount(), runResult.getFailures(), runResult.getErrors(),
                runResult.getSkipped()));
        return Optional.ofNullable(runResult.getFailsafeCode()).orElse(0);
    }

    private static int runInJunit5(File testClassesDir, File targetClassesDir, File dependencyJarsDir, List<String> tests, StartupReportConfiguration startupReportConfiguration, ProviderConfiguration providerConfiguration) throws SurefireExecutionException, TestSetFailedException {
        String providerClassName = JUnitPlatformProvider.class.getName();//"org.apache.maven.surefire.junitplatform.JUnitPlatformProvider";
        Classpath testClasspath = generateTestClasspath(testClassesDir, targetClassesDir, dependencyJarsDir);
        Classpath inprocClasspath = getInprocClasspath();
        Classpath surefireClasspath = inprocClasspath;
        AbstractPathConfiguration classpathConfiguration = new ClasspathConfiguration(
                testClasspath,
                surefireClasspath,
                inprocClasspath,
                false,
                false
        );

        ClassLoaderConfiguration classloaderConfiguration = new ClassLoaderConfiguration(false, false);
        StartupConfiguration startupConfiguration = new StartupConfiguration(
                providerClassName,
                classpathConfiguration,
                classloaderConfiguration,
                false,
                false);


        DefaultScanResult scanResult = getScanResult(testClassesDir, tests);
        ConsoleLogger consoleLogger = new PrintStreamLogger(System.out);
        consoleLogger.info("Run JUnit5 tests...");

        // Fix Class loading problem in java9+:
        // CompletableFuture.supplyAsync() is executed in the ForkJoinWorkerThread
        // and it only uses system classloader to load classes instead of the IsolatedClassLoader
        ClassloaderSurefireStarter testStarter = new ClassloaderSurefireStarter(startupConfiguration, providerConfiguration,
                startupReportConfiguration, consoleLogger, ClassLoader.getSystemClassLoader());
//        InPluginVMSurefireStarter testStarter = new InPluginVMSurefireStarter(startupConfiguration, providerConfiguration,
//                startupReportConfiguration, consoleLogger);

        RunResult runResult = testStarter.runSuitesInProcess(scanResult);
        boolean runSuccess = runResult.getCompletedCount() > 0 && runResult.isErrorFree() && !runResult.isTimeout();

        String line = "------------------------------------------------------------------------\n";
        consoleLogger.info(String.format(line + "Junit5: TEST %s, Total: %d, Failures: %d, Errors: %d, Skipped: %d\n" + line,
                runSuccess ? "SUCCESS" : "FAILURE", runResult.getCompletedCount(), runResult.getFailures(), runResult.getErrors(),
                runResult.getSkipped()));
        return Optional.ofNullable(runResult.getFailsafeCode()).orElse(0);
    }

    public static DefaultScanResult getScanResult(File testClassesDir, Collection<String> tests) {
        DirectoryScanner directoryScanner = new DirectoryScanner(testClassesDir, new TestListResolver(tests));
        return directoryScanner.scan();
    }

    private static Classpath getInprocClasspath() {
        List<String> classpath = new ArrayList<>();
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        URL[] urls = ClassLoaderUtils.getUrls(cl);
        if (urls != null) {
            for (URL url : urls) {
                classpath.add(url.getFile());
            }
        }
        return new Classpath(classpath);
    }

    private static Classpath generateTestClasspath(File testClassesDir, File targetClassDir, File dependenciesDir) {
        List<String> classpath = new ArrayList<>();
        classpath.add(testClassesDir.getAbsolutePath());
        classpath.add(targetClassDir.getAbsolutePath());

        File[] jarFiles = dependenciesDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".jar"));
        if (jarFiles != null) {
            for (File jarFile : jarFiles) {
                classpath.add(jarFile.getAbsolutePath());
            }
        }
        return new Classpath(classpath);
    }

}