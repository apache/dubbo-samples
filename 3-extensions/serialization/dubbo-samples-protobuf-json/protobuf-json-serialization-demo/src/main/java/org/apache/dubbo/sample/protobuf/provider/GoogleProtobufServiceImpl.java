package org.apache.dubbo.sample.protobuf.provider;/*
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


import org.apache.dubbo.sample.protobuf.GoogleProtobufBasic;
import org.apache.dubbo.sample.protobuf.GoogleProtobufService;

public class GoogleProtobufServiceImpl implements GoogleProtobufService {
    @Override
    public GoogleProtobufBasic.GooglePBResponseType callGoogleProtobuf(GoogleProtobufBasic.GooglePBRequestType requestType) {
        return GoogleProtobufBasic.GooglePBResponseType.newBuilder()
                .setDouble(requestType.getDouble())
                .setFloat(requestType.getFloat())
                .setInt32(requestType.getInt32())
                .setInt64(requestType.getInt64())
                .setUint32(requestType.getUint32())
                .setUint64(requestType.getUint64())
                .setSint32(requestType.getSint32())
                .setSint64(requestType.getSint64())
                .setFixed32(requestType.getFixed32())
                .setFixed64(requestType.getFixed64())
                .setBool(requestType.getBool())
                .setString(requestType.getString())
                .setBytesType(requestType.getBytesType())
                .setCard(requestType.getCard())
                .addAllCards(requestType.getCardsList())
                .putAllCardsmap(requestType.getCardsmapMap())
                .build();
    }
}
