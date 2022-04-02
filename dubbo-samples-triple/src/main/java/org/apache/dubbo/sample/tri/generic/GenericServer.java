package org.apache.dubbo.sample.tri.generic;

import org.apache.dubbo.sample.tri.pojo.TriPojoServer;
import org.apache.dubbo.sample.tri.util.TriSampleConstants;

import java.io.IOException;

public class GenericServer {
    public static void main(String[] args) throws IOException {
        TriPojoServer server = new TriPojoServer(TriSampleConstants.SERVER_PORT);
        server.initialize();
        server.start();
        System.in.read();
    }
}
