package com.c0nrad.darkchess;

import com.c0nrad.darkchess.datastore.MorphiaSingleton;
import com.c0nrad.darkchess.datastore.StoryDatastore;
import com.c0nrad.darkchess.models.Board;
import com.c0nrad.darkchess.models.PlayerType;
import com.c0nrad.darkchess.models.Story;
import com.c0nrad.darkchess.server.API;
import com.c0nrad.darkchess.server.Config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.bson.types.ObjectId;

import xyz.morphia.Datastore;

public class App {
    public static void main(String[] args) throws Exception{
        // Datastore d = MorphiaSingleton.GetDatastore();
        Config.LoadProperties();
        SetupLogLevels();
        
        StoryDatastore.SeedLevels();


        // Game g = GameEngine.PlayGame(new RandBot(), new RandBot());
        // GameDatastore.Save(g);

        API.StartServer();
        // GameEngine.PlayGame(new RandBot(), new RandBot());
    }

    public static void SetupLogLevels() {
        // System.setProperty("org.eclipse.jetty.util.log.class", "org.eclipse.jetty.util.log.StdErrLog");
        // System.setProperty("org.eclipse.jetty.LEVEL", "WARN");

        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.getLogger("org.mongodb.driver").setLevel(Level.WARN);
        loggerContext.getLogger("org.eclipse.jetty").setLevel(Level.WARN);
        loggerContext.getLogger("org.eclipse.jetty.server.RequestLog").setLevel(Level.INFO);
    }
}

