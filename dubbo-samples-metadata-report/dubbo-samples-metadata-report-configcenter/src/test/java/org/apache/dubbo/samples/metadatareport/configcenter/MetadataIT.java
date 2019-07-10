/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.dubbo.samples.metadatareport.configcenter;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.metadata.definition.model.FullServiceDefinition;
import org.apache.dubbo.metadata.definition.model.MethodDefinition;
import org.apache.dubbo.samples.metadatareport.configcenter.action.AnnotationAction;
import org.apache.dubbo.samples.metadatareport.configcenter.api.AnnotationService;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MetadataConfigcenterConsumer.ConsumerConfiguration.class})
public class MetadataIT {
    @Autowired
    private AnnotationAction action;

    /**
     * <pre>
     * {
     *   "parameters": {
     *     "side": "provider",
     *     "release": "2.7.2",
     *     "methods": "sayHello",
     *     "deprecated": "false",
     *     "dubbo": "2.0.2",
     *     "threads": "100",
     *     "interface": "org.apache.dubbo.samples.metadatareport.configcenter.api.AnnotationService",
     *     "threadpool": "fixed",
     *     "version": "1.1.1",
     *     "timeout": "5000",
     *     "generic": "false",
     *     "revision": "1.1.1",
     *     "application": "metadatareport-configcenter-provider",
     *     "dynamic": "true",
     *     "register": "true",
     *     "bean.name": "ServiceBean:org.apache.dubbo.samples.metadatareport.configcenter.api.AnnotationService:1.1.1:d-test",
     *     "group": "d-test",
     *     "anyhost": "true"
     *   },
     *   "canonicalName": "org.apache.dubbo.samples.metadatareport.configcenter.api.AnnotationService",
     *   "codeSource": "file:/github/dubbo/incubator-dubbo-samples/dubbo-samples-metadata-report/dubbo-samples-metadata-report-configcenter/target/classes/",
     *   "methods": [
     *     {
     *       "name": "sayHello",
     *       "parameterTypes": [
     *         "java.lang.String"
     *       ],
     *       "returnType": "java.lang.String"
     *     }
     *   ],
     *   "types": [
     *     {
     *       "type": "int"
     *     },
     *     {
     *       "type": "char"
     *     },
     *     {
     *       "type": "java.lang.String",
     *       "properties": {
     *         "value": {
     *           "type": "char[]"
     *         },
     *         "hash": {
     *           "type": "int"
     *         }
     *       }
     *     }
     *   ]
     * }
     * </pre>
     */
    @Test
    public void testProviderMetadata() throws Exception {
        String result = ZKTools.getMetadata("/dubbo", AnnotationService.class.getName(), "1.1.1", "d-test",
                CommonConstants.PROVIDER_SIDE, "metadatareport-configcenter-provider");
        Gson gson = new Gson();
        FullServiceDefinition definition = gson.fromJson(result, FullServiceDefinition.class);
        Map<String, String> parameters = definition.getParameters();
        Assert.assertEquals("provider", parameters.get("side"));
        Assert.assertEquals("sayHello", parameters.get("methods"));
        Assert.assertEquals("5000", parameters.get("timeout"));
        Assert.assertEquals("1.1.1", parameters.get("version"));
        Assert.assertEquals("d-test", parameters.get("group"));
        Assert.assertEquals("100", parameters.get("threads"));
        Assert.assertEquals("fixed", parameters.get("threadpool"));
        Assert.assertEquals(AnnotationService.class.getCanonicalName(), parameters.get("interface"));
        Assert.assertEquals("false", parameters.get("generic"));
        Assert.assertEquals("metadatareport-configcenter-provider", parameters.get("application"));
        Assert.assertEquals("true", parameters.get("dynamic"));
        Assert.assertEquals("true", parameters.get("register"));
        Assert.assertEquals("true", parameters.get("anyhost"));
        Assert.assertEquals(AnnotationService.class.getCanonicalName(), definition.getCanonicalName());
        List<MethodDefinition> methods = definition.getMethods();
        Assert.assertEquals(1, methods.size());
        MethodDefinition method = methods.get(0);
        Assert.assertEquals("sayHello", method.getName());
        String[] parameterTypes = method.getParameterTypes();
        Assert.assertEquals(1, parameterTypes.length);
        Assert.assertEquals("java.lang.String", parameterTypes[0]);
        Assert.assertEquals("java.lang.String", method.getReturnType());
    }

    /**
     * <pre>
     * {
     *   "side": "consumer",
     *   "release": "2.7.2",
     *   "methods": "sayHello",
     *   "lazy": "false",
     *   "dubbo": "2.0.2",
     *   "interface": "org.apache.dubbo.samples.metadatareport.configcenter.api.AnnotationService",
     *   "version": "1.1.1",
     *   "timeout": "6666",
     *   "revision": "1.1.1",
     *   "application": "metadatareport-configcenter-consumer",
     *   "sticky": "false",
     *   "group": "d-test"
     * }
     * </pre>
     */
    @Test
    public void testConsumerMetadata() throws Exception {
        String result = ZKTools.getMetadata("/dubbo", AnnotationService.class.getName(), "1.1.1", "d-test",
                CommonConstants.CONSUMER_SIDE, "metadatareport-configcenter-consumer");
        Gson gson = new Gson();
        Map<String, String> map = gson.fromJson(result, Map.class);
        Assert.assertEquals("consumer", map.get("side"));
        Assert.assertEquals("metadatareport-configcenter-consumer", map.get("application"));
        Assert.assertEquals("sayHello", map.get("methods"));
        Assert.assertEquals("false", map.get("lazy"));
        Assert.assertEquals("false", map.get("sticky"));
        Assert.assertEquals("1.1.1", map.get("version"));
        Assert.assertEquals("d-test", map.get("group"));
        Assert.assertEquals("6666", map.get("timeout"));
        Assert.assertEquals(AnnotationService.class.getName(), map.get("interface"));
    }
}
