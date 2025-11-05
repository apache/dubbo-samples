package org.apache.dubbo.test.runner;
/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.maven.plugin.surefire.CommonReflector;
import org.apache.maven.plugin.surefire.StartupReportConfiguration;
import org.apache.maven.plugin.surefire.log.api.ConsoleLogger;
import org.apache.maven.surefire.booter.ClasspathConfiguration;
import org.apache.maven.surefire.booter.ProviderConfiguration;
import org.apache.maven.surefire.booter.StartupConfiguration;
import org.apache.maven.surefire.booter.SurefireExecutionException;
import org.apache.maven.surefire.suite.RunResult;
import org.apache.maven.surefire.testset.TestSetFailedException;
import org.apache.maven.surefire.util.DefaultScanResult;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import static org.apache.maven.surefire.booter.ProviderFactory.invokeProvider;

/**
 * Starts the provider in specify classloader.
 */
public class ClassloaderSurefireStarter
{
    private final StartupConfiguration startupConfig;
    private final StartupReportConfiguration startupReportConfig;
    private final ProviderConfiguration providerConfig;
    private final ConsoleLogger consoleLogger;
    private ClassLoader testClassLoader;

    public ClassloaderSurefireStarter(StartupConfiguration startupConfig,
                                      ProviderConfiguration providerConfig,
                                      StartupReportConfiguration startupReportConfig,
                                      ConsoleLogger consoleLogger,
                                      ClassLoader testClassLoader)
    {
        this.startupConfig = startupConfig;
        this.startupReportConfig = startupReportConfig;
        this.providerConfig = providerConfig;
        this.consoleLogger = consoleLogger;
        this.testClassLoader = testClassLoader;
    }

    public RunResult runSuitesInProcess( DefaultScanResult scanResult )
        throws SurefireExecutionException, TestSetFailedException
    {
        // The test classloader must be constructed first to avoid issues with commons-logging until we properly
        // separate the TestNG classloader

        Map<String, String> providerProperties = providerConfig.getProviderProperties();
        scanResult.writeTo( providerProperties );

        startupConfig.writeSurefireTestClasspathProperty();

        CommonReflector surefireReflector = new CommonReflector(testClassLoader);

        Object factory = surefireReflector.createReportingReporterFactory( startupReportConfig, consoleLogger );

        try
        {
            return invokeProvider( null, testClassLoader, factory, providerConfig, false, startupConfig, true );
        }
        catch ( InvocationTargetException e )
        {
            throw new SurefireExecutionException( "Exception in provider", e.getTargetException() );
        }
    }

}
