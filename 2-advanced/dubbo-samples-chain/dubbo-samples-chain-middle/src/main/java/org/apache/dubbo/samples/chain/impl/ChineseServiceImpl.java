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

import org.apache.dubbo.samples.chain.api.ChineseService;
import org.apache.dubbo.samples.chain.api.DogService;
import org.apache.dubbo.samples.chain.api.TigerService;

import org.springframework.beans.factory.annotation.Autowired;

public class ChineseServiceImpl implements ChineseService {

    @Autowired
    private DogService dogService;

    @Autowired
    private TigerService tigerService;

    @Override
    public String eat() {
        System.out.println(dogService.getName());
        return "I can eat Dog!";
    }

    @Override
    public String watch() {
        System.out.println(tigerService.getName());
        return "I want to see Tiger!";
    }

    public DogService getDogService() {
        return dogService;
    }

    public void setDogService(DogService dogService) {
        this.dogService = dogService;
    }

    public TigerService getTigerService() {
        return tigerService;
    }

    public void setTigerService(TigerService tigerService) {
        this.tigerService = tigerService;
    }
}
