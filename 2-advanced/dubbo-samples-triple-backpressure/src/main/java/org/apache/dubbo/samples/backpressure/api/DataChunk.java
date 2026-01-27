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
package org.apache.dubbo.samples.backpressure.api;

import java.io.Serializable;

/**
 * A chunk of data in the stream.
 */
public class DataChunk implements Serializable {

    private static final long serialVersionUID = -1205830810422530386L;

    /**
     * Sequence number of this chunk.
     */
    private int sequenceNumber;

    /**
     * The data payload.
     */
    private byte[] data;

    /**
     * Timestamp when this chunk was created (for latency measurement).
     */
    private long timestamp;

    public DataChunk() {
    }

    public DataChunk(int sequenceNumber, byte[] data, long timestamp) {
        this.sequenceNumber = sequenceNumber;
        this.data = data;
        this.timestamp = timestamp;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "DataChunk{seq=" + sequenceNumber + ", dataLen=" + (data != null ? data.length : 0) + ", ts=" + timestamp + "}";
    }
}


