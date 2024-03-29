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
 *
 */

package org.apache.dubbo.benchmark.agent;

import org.apache.skywalking.apm.agent.core.plugin.interceptor.ConstructorInterceptPoint;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.InstanceMethodsInterceptPoint;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.ClassInstanceMethodsEnhancePluginDefine;
import org.apache.skywalking.apm.agent.core.plugin.match.ClassMatch;
import org.apache.skywalking.apm.agent.core.plugin.match.logical.LogicalMatchOperation;
import org.apache.skywalking.apm.dependencies.net.bytebuddy.description.method.MethodDescription;
import org.apache.skywalking.apm.dependencies.net.bytebuddy.matcher.ElementMatcher;

import static org.apache.skywalking.apm.agent.core.plugin.match.HierarchyMatch.byHierarchyMatch;
import static org.apache.skywalking.apm.agent.core.plugin.match.PrefixMatch.nameStartsWith;
import static org.apache.skywalking.apm.dependencies.net.bytebuddy.matcher.ElementMatchers.namedOneOf;

public class CustomizeDubboInstrumentation extends ClassInstanceMethodsEnhancePluginDefine {

    public static final String INTERCEPT_POINT_METHOD = "doInvoke";
    public static final String INTERCEPT_INVOKE_METHOD = "invoke";

    private static final String RPC_INVOKER = "org.apache.dubbo.rpc.Invoker";
    public static final String INTERCEPT_CLASS = "org.apache.dubbo.benchmark.agent.DubboInvokeInterceptor";

    @Override
    protected ClassMatch enhanceClass() {
        return LogicalMatchOperation.and(nameStartsWith("org.apache.dubbo"), byHierarchyMatch(RPC_INVOKER));
    }

    @Override
    public ConstructorInterceptPoint[] getConstructorsInterceptPoints() {
        return null;
    }

    @Override
    public InstanceMethodsInterceptPoint[] getInstanceMethodsInterceptPoints() {
        return new InstanceMethodsInterceptPoint[]{
                new InstanceMethodsInterceptPoint() {
                    @Override
                    public ElementMatcher<MethodDescription> getMethodsMatcher() {
                        return namedOneOf(INTERCEPT_POINT_METHOD, INTERCEPT_INVOKE_METHOD);
                    }

                    @Override
                    public String getMethodsInterceptor() {
                        return INTERCEPT_CLASS;
                    }

                    @Override
                    public boolean isOverrideArgs() {
                        return false;
                    }
                }
        };
    }

}
