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
package org.apache.dubbo.samples.provider;

import org.apache.dubbo.samples.api.QosService;
import org.apache.dubbo.samples.filter.ConsumerAlibabaFilter;
import org.apache.dubbo.samples.filter.ConsumerClusterFilter;
import org.apache.dubbo.samples.filter.ConsumerFilter;
import org.apache.dubbo.samples.filter.ProviderAlibabaFilter;
import org.apache.dubbo.samples.filter.ProviderFilter;
import org.apache.dubbo.samples.loadbalance.AlibabaLoadBalance;
import org.apache.dubbo.samples.loadbalance.ApacheLoadBalance;
import org.apache.dubbo.samples.router.AlibabaRouter;
import org.apache.dubbo.samples.router.ApacheRouter;
import org.apache.dubbo.samples.router.ApacheStateRouter;

public class QosServiceImpl implements QosService {
    @Override
    public boolean expected() {
        System.out.println("ProviderAlibabaFilter.expected(): " + ProviderAlibabaFilter.expected());
        System.out.println("ProviderFilter.expected(): " + ProviderFilter.expected());
        System.out.println("ConsumerAlibabaFilter.expected(): " + ConsumerAlibabaFilter.expected());
        System.out.println("ConsumerFilter.expected(): " + ConsumerFilter.expected());
        System.out.println("ConsumerClusterFilter.expected(): " + ConsumerClusterFilter.expected());
        System.out.println("AlibabaRouter.isInvoked(): " + AlibabaRouter.isInvoked());
        System.out.println("ApacheRouter.isInvoked(): " + ApacheRouter.isInvoked());
        System.out.println("ApacheStateRouter.isInvoked(): " + ApacheStateRouter.isInvoked());
        System.out.println("AlibabaLoadBalance.isInvoked(): " + AlibabaLoadBalance.isInvoked());
        System.out.println("ApacheLoadBalance.isInvoked(): " + ApacheLoadBalance.isInvoked());

        return ProviderAlibabaFilter.expected() && ProviderFilter.expected()
                && ConsumerAlibabaFilter.expected() && ConsumerFilter.expected() && ConsumerClusterFilter.expected()
                && AlibabaRouter.isInvoked() && ApacheRouter.isInvoked() && ApacheStateRouter.isInvoked()
                && AlibabaLoadBalance.isInvoked() && ApacheLoadBalance.isInvoked();
    }
}
