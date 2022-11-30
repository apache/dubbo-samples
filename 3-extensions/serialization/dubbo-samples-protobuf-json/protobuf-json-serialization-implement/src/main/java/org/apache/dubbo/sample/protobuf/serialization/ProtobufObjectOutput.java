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

import org.apache.dubbo.common.serialize.ObjectOutput;
import org.apache.dubbo.common.serialize.hessian2.Hessian2ObjectOutput;
import org.apache.dubbo.sample.protobuf.serialization.exception.ProtobufSerializationException;
import org.apache.dubbo.sample.protobuf.serialization.utils.ProtobufUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ProtobufObjectOutput implements ObjectOutput {
    private static final ProtobufUtils protobufUtil = ProtobufUtils.getInstance();
    private final Hessian2ObjectOutput delegate;

    public ProtobufObjectOutput(OutputStream outputStream) {
        this.delegate = new Hessian2ObjectOutput(outputStream);
    }

    /**
     * when object insatanceOf protobuf object serialize with ProtobufBuilder
     *
     * @param obj object
     */
    @Override
    public void writeObject(Object obj) {
        try {
            Class clazz = obj.getClass();
            writeUTF(clazz.getName());
            if (protobufUtil.canSerializeWithProtobuf(clazz)) {
                writeBytes(serializeToBytes(obj));
            } else {
                delegate.writeObject(obj);
            }
        } catch (Throwable ex) {
            String message =
                    obj == null ? "Unable to serialize object" : "Unable to serialize object for " + obj.getClass().getName();
            throw new ProtobufSerializationException(message, ex);
        }
    }

    private byte[] serializeToBytes(Object obj) throws IOException {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            protobufUtil.serializeWithProtobuf(obj, os);
            return os.toByteArray();
        }
    }

    @Override
    public void writeBool(boolean v) throws IOException {
        delegate.writeBool(v);
    }

    @Override
    public void writeByte(byte v) throws IOException {
        delegate.writeByte(v);
    }

    @Override
    public void writeShort(short v) throws IOException {
        delegate.writeShort(v);
    }

    @Override
    public void writeInt(int v) throws IOException {
        delegate.writeInt(v);
    }

    @Override
    public void writeLong(long v) throws IOException {
        delegate.writeLong(v);
    }

    @Override
    public void writeFloat(float v) throws IOException {
        delegate.writeFloat(v);
    }

    @Override
    public void writeDouble(double v) throws IOException {
        delegate.writeDouble(v);
    }

    @Override
    public void writeBytes(byte[] b) throws IOException {
        delegate.writeBytes(b);
    }

    @Override
    public void writeBytes(byte[] b, int off, int len) throws IOException {
        delegate.writeBytes(b, off, len);
    }

    @Override
    public void writeUTF(String v) throws IOException {
        delegate.writeUTF(v);
    }

    @Override
    public void flushBuffer() throws IOException {
        delegate.flushBuffer();
    }
}
