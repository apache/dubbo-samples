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

package org.apache.dubbo.sample.protobuf.serialization;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.serialize.ObjectInput;
import org.apache.dubbo.common.serialize.ObjectOutput;
import org.apache.dubbo.common.serialize.Serialization;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * ProtobufSerialization
 * Date: 2019-10-01
 */
public class ProtobufSerialization implements Serialization {
    //Serialization ID must less than SERIALIZATION_MASK and cannot be duplicated with other serializations
    private static final int CONTENT_TYPE_ID = 30;

    /**
     * The content type id should not be changed in any circumstance!
     */
    @Override
    public byte getContentTypeId() {
        return CONTENT_TYPE_ID;
    }

    @Override
    public String getContentType() {
        return "x-application/protobuf";
    }

    @Override
    public ObjectOutput serialize(URL url, OutputStream output) {
        return new ProtobufObjectOutput(output);
    }

    @Override
    public ObjectInput deserialize(URL url, InputStream input) {
        return new ProtobufObjectInput(input);
    }
}
