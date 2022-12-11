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
