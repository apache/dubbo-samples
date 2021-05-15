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
package org.apache.dubbo.samples.metadatareport.local.xml;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.metadata.report.identifier.KeyTypeEnum;
import org.apache.dubbo.metadata.report.identifier.MetadataIdentifier;

/**
 * 2018/11/8
 */
public class ZkUtil {

    static String toRootDir(String root) {
        if (root.equals(CommonConstants.PATH_SEPARATOR)) {
            return root;
        }
        return root + CommonConstants.PATH_SEPARATOR;
    }

    public static String getNodePath(MetadataIdentifier metadataIdentifier) {
        return toRootDir("/dubbo3") + metadataIdentifier.getUniqueKey(KeyTypeEnum.PATH) + CommonConstants.PATH_SEPARATOR + "service.data";
    }
}
