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

package org.apache.dubbo.samples.rpccontext.dto;

import java.io.Serializable;

public class Service1DTO implements Serializable {
    private String consumerReq;
    private String provider2Res;

    private Service2DTO service2DTO;

    public Service2DTO getService2DTO() {
        return service2DTO;
    }

    public void setService2DTO(Service2DTO service2DTO) {
        this.service2DTO = service2DTO;
    }

    public String getConsumerReq() {
        return consumerReq;
    }

    public void setConsumerReq(String consumerReq) {
        this.consumerReq = consumerReq;
    }

    public String getProvider2Res() {
        return provider2Res;
    }

    public void setProvider2Res(String provider2Res) {
        this.provider2Res = provider2Res;
    }

}
