package com.c0nrad.darkchess;

import com.c0nrad.darkchess.datastore.StoryDatastore;
import com.c0nrad.darkchess.server.API;
import com.c0nrad.darkchess.server.Config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;

public class App {
    public static void main(String[] args) throws Exception{
        Config.LoadProperties();
        SetupLogLevels();
        
        StoryDatastore.SeedLevels();

        API.StartServer();
    }

    public static void SetupLogLevels() {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        
        //mangodb is a little noisy...
        loggerContext.getLogger("org.mongodb.driver").setLevel(Level.WARN);
        
        // so is jetty...
        loggerContext.getLogger("org.eclipse.jetty").setLevel(Level.WARN);
        
        // we setup the RequstLogger in server/API.java
        loggerContext.getLogger("org.eclipse.jetty.server.RequestLog").setLevel(Level.INFO);
    }
}

