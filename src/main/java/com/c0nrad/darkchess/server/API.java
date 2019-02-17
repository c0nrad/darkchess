package com.c0nrad.darkchess.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

public class API
{
    public static void StartServer() throws Exception
    {
        String portStr = Config.properties.getProperty(Config.PORT);
        System.out.println("[+] Starting server on port: " + portStr);
        Server server = new Server(Integer.parseInt(portStr));
        
        ServletContextHandler ctx = 
        new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        
        ctx.setContextPath("/");
        server.setHandler(ctx);

        ServletHolder serHolder = ctx.addServlet(ServletContainer.class, "/api/v1/*");
        serHolder.setInitOrder(1);
        serHolder.setInitParameter("jersey.config.server.provider.packages", 
                "com.c0nrad.darkchess.server");

        ServletHolder staticHolder = new ServletHolder(new DefaultServlet());
        staticHolder.setInitParameter("resourceBase", "./client/build");
        ctx.addServlet(staticHolder, "/*");
            
        server.start();
        server.join();
    }
}