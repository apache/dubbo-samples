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
 * Response after stream completes.
 */
public class StreamResponse implements Serializable {

    private static final long serialVersionUID = -5423429601473151879L;

    /**
     * Total number of chunks received.
     */
    private int totalChunks;

    /**
     * Total bytes received.
     */
    private long totalBytes;

    /**
     * Duration in milliseconds.
     */
    private long durationMs;

    public StreamResponse() {
    }

    public StreamResponse(int totalChunks, long totalBytes, long durationMs) {
        this.totalChunks = totalChunks;
        this.totalBytes = totalBytes;
        this.durationMs = durationMs;
    }

    public int getTotalChunks() {
        return totalChunks;
    }

    public void setTotalChunks(int totalChunks) {
        this.totalChunks = totalChunks;
    }

    public long getTotalBytes() {
        return totalBytes;
    }

    public void setTotalBytes(long totalBytes) {
        this.totalBytes = totalBytes;
    }

    public long getDurationMs() {
        return durationMs;
    }

    public void setDurationMs(long durationMs) {
        this.durationMs = durationMs;
    }

    @Override
    public String toString() {
        return "StreamResponse{totalChunks=" + totalChunks + ", totalBytes=" + totalBytes + ", durationMs=" + durationMs + "}";
    }
}


