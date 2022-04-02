package org.apache.dubbo.sample.tri.interop.server;

import org.apache.dubbo.sample.tri.stub.TriStubServer;
import org.apache.dubbo.sample.tri.util.TriSampleConstants;

import java.io.IOException;

public class TriOpServer {
    public static void main(String[] args) throws IOException {
        TriStubServer triStubServer = new TriStubServer(TriSampleConstants.SERVER_PORT);
        triStubServer.initialize();
        triStubServer.start();
        System.in.read();
    }
}
