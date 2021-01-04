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

package org.apache.dubbo.samples.metadatareport.local.xml;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.metadata.definition.model.FullServiceDefinition;
import org.apache.dubbo.metadata.definition.model.MethodDefinition;
import org.apache.dubbo.samples.metadatareport.local.xml.api.DemoService;

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
@ContextConfiguration(locations = {"classpath:/spring/metadata-consumer.xml"})
public class MetadataIT {
    @Autowired
    private DemoService demoService;


    /**
     * <pre>
     * {
     *   "parameters": {
     *     "side": "provider",
     *     "release": "2.7.2",
     *     "methods": "sayHello",
     *     "deprecated": "false",
     *     "dubbo": "2.0.2",
     *     "interface": "org.apache.dubbo.samples.metadatareport.local.xml.api.DemoService",
     *     "generic": "false",
     *     "application": "metadatareport-local-xml-provider2",
     *     "dynamic": "true",
     *     "register": null,
     *     "bean.name": "org.apache.dubbo.samples.metadatareport.local.xml.api.DemoService",
     *     "anyhost": "true"
     *   },
     *   "canonicalName": "org.apache.dubbo.samples.metadatareport.local.xml.api.DemoService",
     *   "codeSource": "file:/dubbo/incubator-dubbo-samples/dubbo-samples-metadata-report/dubbo-samples-metadata-report-local-xml/target/classes/",
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
     *       "type": "char"
     *     },
     *     {
     *       "type": "int"
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
        String result = ZkUtil.getMetadata("/dubbo3", DemoService.class.getName(), CommonConstants.PROVIDER_SIDE,
                "metadatareport-local-xml-provider2");
        Gson gson = new Gson();
        FullServiceDefinition definition = gson.fromJson(result, FullServiceDefinition.class);
        Map<String, String> parameters = definition.getParameters();
        Assert.assertEquals("provider", parameters.get("side"));
        Assert.assertEquals("sayHello", parameters.get("methods"));
        Assert.assertEquals(DemoService.class.getCanonicalName(), parameters.get("interface"));
        Assert.assertEquals("false", parameters.get("generic"));
        Assert.assertEquals("metadatareport-local-xml-provider2", parameters.get("application"));
        Assert.assertEquals("true", parameters.get("dynamic"));
        Assert.assertEquals("true", parameters.get("anyhost"));
        Assert.assertEquals(DemoService.class.getCanonicalName(), definition.getCanonicalName());
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
     *   "application": "metadatareport-local-xml-consumer2",
     *   "release": "2.7.2",
     *   "methods": "sayHello",
     *   "lazy": "false",
     *   "sticky": "false",
     *   "dubbo": "2.0.2",
     *   "interface": "org.apache.dubbo.samples.metadatareport.local.xml.api.DemoService"
     * }
     * </pre>
     */
    @Test
    public void testConsumerMetadata() throws Exception {
        //wait for metadata async report finish
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }

        String result = ZkUtil.getMetadata("/dubbo3", DemoService.class.getName(), CommonConstants.CONSUMER_SIDE,
                "metadatareport-local-xml-consumer2");
        Gson gson = new Gson();
        Map<String, String> map = gson.fromJson(result, Map.class);
        Assert.assertEquals("consumer", map.get("side"));
        Assert.assertEquals("metadatareport-local-xml-consumer2", map.get("application"));
        Assert.assertEquals("sayHello", map.get("methods"));
        Assert.assertNull(map.get("lazy"));
        Assert.assertEquals("false", map.get("sticky"));
        Assert.assertEquals(DemoService.class.getName(), map.get("interface"));
    }
}
