/*
 *
 *   Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements.  See the NOTICE file distributed with
 *   this work for additional information regarding copyright ownership.
 *   The ASF licenses this file to You under the Apache License, Version 2.0
 *   (the "License"); you may not use this file except in compliance with
 *   the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */
package org.apache.dubbo.samples.simplified.registry.nosimple;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.samples.simplified.registry.nosimple.api.DemoService;

/**
 * 2018/11/8
 */
public class ZkUtil {

    private static String toRootDir() {
        return "/dubbo";
    }

    private static String toServicePath() {
        String name = DemoService.class.getName();
        return toRootDir() + CommonConstants.PATH_SEPARATOR + URL.encode(name);
    }

    private static String toCategoryPath(String side) {
        return toServicePath() + CommonConstants.PATH_SEPARATOR + side;
    }

    public static String toUrlPath(String side) {
        return toCategoryPath(side);
    }

}
