package org.apache.dubbo.sample.tri.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class IpUtils {

    private static final Logger logger = LogManager.getLogger(IpUtils.class);

    /**
     * 检测Ip和端口是否可用
     *
     * @param ip
     * @param port
     * @return
     */
    public static boolean checkIpPort(String ip, int port) {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(ip,port),3000);
            logger.info("地址和端口号可用");
            return true;
        } catch (Exception e) {
            logger.info("地址和端口号不可用");
            return false;
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {

                }
            }
        }

    }
    /**
     * 检测Ip地址
     *
     * @param ip
     * @return
     */
    public static boolean checkIp(String ip){
        try {
            InetAddress.getByName(ip).isReachable(3000);
            logger.info("Ip可以使用");
            return true;
        } catch (IOException e) {
            logger.info("Ip不可用");
            return false;
        }
    }

}

