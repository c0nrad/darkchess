package com.c0nrad.darkchess;

import com.c0nrad.darkchess.datastore.MorphiaSingleton;
import com.c0nrad.darkchess.datastore.StoryDatastore;
import com.c0nrad.darkchess.models.Board;
import com.c0nrad.darkchess.models.PlayerType;
import com.c0nrad.darkchess.models.Story;
import com.c0nrad.darkchess.server.API;
import com.c0nrad.darkchess.server.Config;

import org.bson.types.ObjectId;

import xyz.morphia.Datastore;

public class App {
    public static void main(String[] args) throws Exception{
        // Datastore d = MorphiaSingleton.GetDatastore();
        Config.LoadProperties();
        StoryDatastore.SeedLevels();

        // Game g = GameEngine.PlayGame(new RandBot(), new RandBot());
        // GameDatastore.Save(g);

        API.StartServer();
        // GameEngine.PlayGame(new RandBot(), new RandBot());
        
    }
}

