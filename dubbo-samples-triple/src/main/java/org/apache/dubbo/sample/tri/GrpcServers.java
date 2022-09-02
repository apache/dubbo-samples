package org.apache.dubbo.sample.tri;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.apache.dubbo.sample.tri.interop.client.GrpcGreeterImpl;

import java.io.IOException;

/**
 * @author owen.cai
 * @create_date 2022/9/2
 * @alter_author
 * @alter_date
 */
public class GrpcServers {
    public static void main(String[] args) throws InterruptedException, IOException {
        int port = 9091;
        GrpcGreeterImpl greeterImpl = new GrpcGreeterImpl();

        Server server = ServerBuilder
                .forPort(port)
                .addService(greeterImpl)
                .build()
                .start();
        System.out.println("server started, port : " + port);
        server.awaitTermination();
    }
}
