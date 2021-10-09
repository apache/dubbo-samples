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

import java.util.regex.Pattern;

/**
 * Constants in dubbo-scenario-builder
 */
public interface Constants {

    String ERROR_MSG_FLAG=":ErrorMsg:";

    int EXIT_FAILED = 1;
    int EXIT_UNMATCHED = 100;
    int EXIT_IGNORED = 120;

    /**
     * Used in case-versions.conf 
     */
    String DUBBO_VERSION_KEY = "dubbo.version";

    /**
     * The pattern used for searching target service name 
     */
    Pattern PATTERN_DUBBO_VERSION = Pattern.compile("^dubbo\\.(.+)\\.version$");
}
