package com.c0nrad.darkchess.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Slf4jRequestLog;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class API
{
    private static final Logger logger = LoggerFactory.getLogger(API.class);

    public static void StartServer() throws Exception
    {
        String portStr = Config.properties.getProperty(Config.PORT);
        logger.info("[+] Starting server on port: {}", portStr);

        Server server = new Server(Integer.parseInt(portStr));
        
        ServletContextHandler ctx = 
        new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        
        ctx.setContextPath("/");
        server.setHandler(ctx);

        ServletHolder serHolder = ctx.addServlet(ServletContainer.class, "/api/v1/*");
        serHolder.setInitOrder(1);
        serHolder.setInitParameter("jersey.config.server.provider.packages", 
                "com.c0nrad.darkchess.server");

        // Static handler, reads from production client/build
        // For local dev we use create-react-app's livereload service
        ServletHolder staticHolder = new ServletHolder(new DefaultServlet());
        staticHolder.setInitParameter("resourceBase", "./client/build");
        ctx.addServlet(staticHolder, "/*");

        // This is cool. This enables request logs. 
        Slf4jRequestLog requestLog = new Slf4jRequestLog();
        requestLog.setLogLatency(true);
        server.setRequestLog(requestLog);
            
        server.start();
        server.join();
    }
}