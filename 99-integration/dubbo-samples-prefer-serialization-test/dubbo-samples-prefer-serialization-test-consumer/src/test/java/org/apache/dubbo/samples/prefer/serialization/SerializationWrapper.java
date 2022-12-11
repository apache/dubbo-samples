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
package org.apache.dubbo.samples.prefer.serialization;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.serialize.ObjectInput;
import org.apache.dubbo.common.serialize.ObjectOutput;
import org.apache.dubbo.common.serialize.Serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SerializationWrapper implements Serialization {

    private static final List<Byte> usedSerialization = Collections.synchronizedList(new ArrayList<Byte>());

    private Serialization serialization;

    public SerializationWrapper(Serialization serialization) {
        this.serialization = serialization;
    }

    @Override
    public byte getContentTypeId() {
        return serialization.getContentTypeId();
    }

    @Override
    public String getContentType() {
        return serialization.getContentType();
    }

    @Override
    public ObjectOutput serialize(URL url, OutputStream output) throws IOException {
        usedSerialization.add(serialization.getContentTypeId());
        return serialization.serialize(url, output);
    }

    @Override
    public ObjectInput deserialize(URL url, InputStream input) throws IOException {
        usedSerialization.add(serialization.getContentTypeId());
        return serialization.deserialize(url, input);
    }

    @Override
    public String toString() {
        return serialization.toString();
    }

    public static List<Byte> getUsedSerialization() {
        return usedSerialization;
    }
}
