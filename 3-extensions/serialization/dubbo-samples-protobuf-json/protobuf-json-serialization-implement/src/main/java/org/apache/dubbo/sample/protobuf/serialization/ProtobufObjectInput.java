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

import org.apache.dubbo.common.serialize.ObjectInput;
import org.apache.dubbo.common.serialize.hessian2.Hessian2ObjectInput;
import org.apache.dubbo.sample.protobuf.serialization.exception.ProtobufSerializationException;
import org.apache.dubbo.sample.protobuf.serialization.utils.ProtobufUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

public class ProtobufObjectInput implements ObjectInput {
    private ProtobufUtils protobufUtil = ProtobufUtils.getInstance();
    private final Hessian2ObjectInput delegate;

    ProtobufObjectInput(InputStream is) {
        this.delegate = new Hessian2ObjectInput(is);
    }

    @Override
    public Object readObject() {
        String className = null;
        try {
            className = readUTF();
            if (protobufUtil.canSerializeWithProtobuf(protobufUtil.loadClass(className))) {
                byte[] data = readBytes();
                return deserializeWithProtobuf(data, protobufUtil.loadClass(className));
            } else {
                return delegate.readObject();
            }
        } catch (Throwable ex) {
            String message =
                    className == null ? "Unable to deserializeWithProtobuf object" : "Unable to deserializeWithProtobuf object for " + className;
            throw new ProtobufSerializationException(message, ex);
        }
    }

    private <T> T deserializeWithProtobuf(byte[] data, Class<T> clazz) throws IOException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {
        try (ByteArrayInputStream newIs = new ByteArrayInputStream(data)) {
            if (protobufUtil.canSerializeWithProtobuf(clazz)) {
                return protobufUtil.deserializeWithProtobuf(newIs, clazz);
            } else {
                return (T) delegate.readObject();
            }
        }
    }

    @Override
    public <T> T readObject(Class<T> cls) {
        if (cls == null || cls == Object.class) {
            return (T) readObject();
        }

        try {
            String className = readUTF();
            if (protobufUtil.canSerializeWithProtobuf(protobufUtil.loadClass(className))) {
                byte[] data = readBytes();
                return deserializeWithProtobuf(data, cls);
            }
            return delegate.readObject(cls);
        } catch (Throwable ex) {
            throw new ProtobufSerializationException("Unable to deserializeWithProtobuf object for " + cls.getName(), ex);
        }
    }

    @Override
    public <T> T readObject(Class<T> cls, Type type) {
        return readObject(cls);
    }

    @Override
    public boolean readBool() throws IOException {
        return delegate.readBool();
    }

    @Override
    public byte readByte() throws IOException {
        return delegate.readByte();
    }

    @Override
    public short readShort() throws IOException {
        return delegate.readShort();
    }

    @Override
    public int readInt() throws IOException {
        return delegate.readInt();
    }

    @Override
    public long readLong() throws IOException {
        return delegate.readLong();
    }

    @Override
    public float readFloat() throws IOException {
        return delegate.readFloat();
    }

    @Override
    public double readDouble() throws IOException {
        return delegate.readDouble();
    }

    @Override
    public byte[] readBytes() throws IOException {
        return delegate.readBytes();
    }

    @Override
    public String readUTF() throws IOException {
        return delegate.readUTF();
    }
}
