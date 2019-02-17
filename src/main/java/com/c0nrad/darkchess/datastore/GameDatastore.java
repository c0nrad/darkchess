package com.c0nrad.darkchess.datastore;


import java.util.List;

import com.c0nrad.darkchess.models.Game;

import org.bson.types.ObjectId;

import xyz.morphia.Datastore;

public class GameDatastore {
    public static Game Find(String gameId) {
        Datastore d = MorphiaSingleton.GetDatastore();
        ObjectId objectId = new ObjectId(gameId);
        return d.get(Game.class, objectId);
    }

    public static void Update(Game g) {
        Datastore d = MorphiaSingleton.GetDatastore();
        d.merge(g);
    }

    public static void Save(Game g) {
        Datastore d = MorphiaSingleton.GetDatastore();
        d.save(g);    
    }

    public static List<Game> FindAll() {
        Datastore d = MorphiaSingleton.GetDatastore();
        return d.createQuery(Game.class).asList();     
    }
}