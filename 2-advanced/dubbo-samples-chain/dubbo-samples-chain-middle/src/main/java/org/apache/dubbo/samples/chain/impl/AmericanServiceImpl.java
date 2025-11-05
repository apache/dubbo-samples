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
package org.apache.dubbo.samples.chain.impl;

import org.apache.dubbo.samples.chain.api.AmericanService;
import org.apache.dubbo.samples.chain.api.CatService;
import org.apache.dubbo.samples.chain.api.LionService;

import org.springframework.beans.factory.annotation.Autowired;

public class AmericanServiceImpl implements AmericanService {

    @Autowired
    private CatService catService;

    @Autowired
    private LionService lionService;

    @Override
    public String eat() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(catService.getName());
        return "I can eat Cat!";
    }

    @Override
    public String watch() {
        System.out.println(lionService.getName());
        return "I want to see Lion!";
    }

    public CatService getCatService() {
        return catService;
    }

    public void setCatService(CatService catService) {
        this.catService = catService;
    }

    public LionService getLionService() {
        return lionService;
    }

    public void setLionService(LionService lionService) {
        this.lionService = lionService;
    }
}
