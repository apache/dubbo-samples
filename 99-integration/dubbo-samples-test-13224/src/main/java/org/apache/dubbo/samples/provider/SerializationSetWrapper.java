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

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.serialize.ObjectInput;
import org.apache.dubbo.common.serialize.ObjectOutput;
import org.apache.dubbo.common.serialize.Serialization;
import org.apache.dubbo.samples.api.SerializationTypeWrap;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.Map;

public class SerializationSetWrapper implements Serialization  {
    private final Serialization serialization;

    public SerializationSetWrapper(Serialization serialization) {
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
        return serialization.serialize(url, output);
    }

    @Override
    public ObjectInput deserialize(URL url, InputStream input) throws IOException {
        ObjectInput origin = serialization.deserialize(url, input);
        return new ObjectInput() {
            @Override
            public Object readObject() throws IOException, ClassNotFoundException {
                Object obj = origin.readObject();
                if (obj instanceof SerializationTypeWrap) {
                    ((SerializationTypeWrap) obj).setContentType(serialization.getContentType());
                }
                if (obj.getClass().isArray()) {
                    Object map = ((Object[])obj)[0];
                    if (map instanceof Map) {
                        ((Map<String, String>) map).put("contentType", serialization.getContentType());
                    }
                }
                if (obj instanceof Map) {
                    ((Map<String, String>) obj).put("contentType", serialization.getContentType());
                }
                return obj;
            }

            @Override
            public <T> T readObject(Class<T> cls) throws IOException, ClassNotFoundException {
                T obj = origin.readObject(cls);
                if (obj instanceof SerializationTypeWrap) {
                    ((SerializationTypeWrap) obj).setContentType(serialization.getContentType());
                }
                if (obj.getClass().isArray()) {
                    Object map = ((Object[])obj)[0];
                    if (map instanceof Map) {
                        ((Map<String, String>) map).put("contentType", serialization.getContentType());
                    }
                }
                if (obj instanceof Map) {
                    ((Map<String, String>) obj).put("contentType", serialization.getContentType());
                }
                return obj;
            }

            @Override
            public <T> T readObject(Class<T> cls, Type type) throws IOException, ClassNotFoundException {
                T obj = origin.readObject(cls, type);
                if (obj instanceof SerializationTypeWrap) {
                    ((SerializationTypeWrap) obj).setContentType(serialization.getContentType());
                }
                if (obj.getClass().isArray()) {
                    Object map = ((Object[])obj)[0];
                    if (map instanceof Map) {
                        ((Map<String, String>) map).put("contentType", serialization.getContentType());
                    }
                }
                if (obj instanceof Map) {
                    ((Map<String, String>) obj).put("contentType", serialization.getContentType());
                }
                return obj;
            }

            @Override
            public boolean readBool() throws IOException {
                return origin.readBool();
            }

            @Override
            public byte readByte() throws IOException {
                return origin.readByte();
            }

            @Override
            public short readShort() throws IOException {
                return origin.readShort();
            }

            @Override
            public int readInt() throws IOException {
                return origin.readInt();
            }

            @Override
            public long readLong() throws IOException {
                return origin.readLong();
            }

            @Override
            public float readFloat() throws IOException {
                return origin.readFloat();
            }

            @Override
            public double readDouble() throws IOException {
                return origin.readDouble();
            }

            @Override
            public String readUTF() throws IOException {
                return origin.readUTF();
            }

            @Override
            public byte[] readBytes() throws IOException {
                return origin.readBytes();
            }

            @Override
            public Throwable readThrowable() throws IOException, ClassNotFoundException {
                return origin.readThrowable();
            }

            @Override
            public String readEvent() throws IOException, ClassNotFoundException {
                return origin.readEvent();
            }

            @Override
            public Map<String, Object> readAttachments() throws IOException, ClassNotFoundException {
                return origin.readAttachments();
            }
        };
    }
}
