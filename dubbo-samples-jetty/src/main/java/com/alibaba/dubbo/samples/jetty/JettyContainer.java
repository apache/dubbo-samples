package com.alibaba.dubbo.samples.jetty;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.container.Container;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;

/**
 * Dubbo Container Extension, used to customize the load content.
 */
public class JettyContainer implements Container {

    private static final Logger logger = LoggerFactory.getLogger(JettyContainer.class);

    private static Server server = new Server(8080);

    public void setServerHandler(Handler handler){
        server.setHandler(handler);
    }

    @Override
    public void start() {
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to start jetty" + e.getMessage(), e);
        }
    }

    @Override
    public void stop() {
        if (server != null) {
            try {
                server.stop();
            } catch (Exception e) {
                logger.warn(e.getMessage(), e);
            }
        }
    }
}
