package com.c0nrad.darkchess.datastore;

import com.c0nrad.darkchess.server.Config;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import xyz.morphia.Datastore;
import xyz.morphia.Morphia;

public class MorphiaSingleton {
    
    private MorphiaSingleton() {}

    public static final String DB = "darkchess";
    private static Datastore datastore;

    public static Datastore GetDatastore() {
        if (datastore == null) {
            final Morphia morphia = new Morphia();
            morphia.mapPackage("com.c0nrad.darkchess.models");

            String mongouri = Config.properties.getProperty(Config.MONGOURI);
            MongoClientURI uri = new MongoClientURI(mongouri);
            MongoClient mongoClient = new MongoClient(uri);

            datastore = morphia.createDatastore(mongoClient, DB);
        } 

        return datastore;
    }
}
