package com.c0nrad.darkchess.datastore;

import java.util.List;

import com.c0nrad.darkchess.exceptions.InvalidBoardException;
import com.c0nrad.darkchess.exceptions.InvalidPieceException;
import com.c0nrad.darkchess.exceptions.InvalidPositionException;
import com.c0nrad.darkchess.models.Board;
import com.c0nrad.darkchess.models.PlayerType;
import com.c0nrad.darkchess.models.Story;

import org.bson.types.ObjectId;

import xyz.morphia.Datastore;
import xyz.morphia.Morphia;
import xyz.morphia.query.Query;
import xyz.morphia.query.UpdateOperations;

public class StoryDatastore {
    public static Story Find(String storyId) {
        Datastore d = MorphiaSingleton.GetDatastore();
        ObjectId objectId = new ObjectId(storyId);
        return d.get(Story.class, objectId);
    }

    public static List<Story> FindAll() {
        Datastore d = MorphiaSingleton.GetDatastore();
        return d.createQuery(Story.class).asList();
    }

    public static Story FindByLevel(int level) {
        Datastore d = MorphiaSingleton.GetDatastore();
        return d.createQuery(Story.class).field("level").equal(level).get();
    }

    public static void SeedLevels() throws InvalidBoardException, InvalidPieceException{
        Datastore d = MorphiaSingleton.GetDatastore();
        
        Story s1 = new Story();
        s1.title = "Frontal Siege";
        s1.level = 1;
        s1.description = "Commander! A wall of pawns is approaching!\n" +
        "\n"+
        "Can you break through the wall of pawns and kill the opposing commander?\n" +
        "\n"+
        "The fate of our people depends on you.";

        String[] layout ={
            "    K   ", 
            "PPPPPPPP",
            "        ",
            "        ",
            "        ",
            "        ",
            "pppppppp",
            "    k   "}; 

        Board b = new Board(layout);

        s1.startingboard = b;
        s1.blackPlayer = PlayerType.RANDBOT;
        d.save(s1);


        //---------- LEVEL 2 ---------
        Story s2 = new Story();
        s2.title = "Assassins Dance";
        s2.level = 2;
        s2.description = "Commander we believe there will be an assassination attempt on your life.\n" +
        "\n"+
        "Can you find and kill the enemy king before being assassinated??\n";

        String[] layout2 ={
            "   NKN  ", 
            "        ",
            "        ",
            "        ",
            "        ",
            "        ",
            "        ",
            "   nkn  "}; 

        Board b2 = new Board(layout2);

        s2.startingboard = b2;
        s2.blackPlayer = PlayerType.KILLBOT;
        d.save(s2);

        //---------- LEVEL 3 ---------
        Story s3 = new Story();
        s3.title = "The kidnapped queen";
        s3.level = 3;
        s3.description = "Commander! The queen has been kidnapped! Launch a full frontal siege and kill the enemey king to resecure the queen.!\n" +
        "\n"+
        "Hurry before it's too late!?\n";

        String[] layout3 ={
            "RNBQKBNR", 
            "PPPPPPPP",
            "        ",
            "        ",
            "        ",
            "        ",
            "pppppppp",
            "rnb kbnr"}; 

        Board b3 = new Board(layout3);

        s3.startingboard = b3;
        s3.blackPlayer = PlayerType.RANDBOT;
        d.save(s3);

        //---------- LEVEL 4 ---------
        Story s4 = new Story();
        s4.title = "Peasant Uprising";
        s4.level = 4;
        s4.description = "Commander! The villagers are angry about the recent raise in taxes and are coming to kill the nobles!\n" +
        "\n"+
        "Kill the peasants and their leader before they get to us!?\n";

        String[] layout4 ={
            "PPPPKPPP", 
            "PPPPPPPP",
            "PPPPPPPP",
            "        ",
            "        ",
            "        ",
            "        ",
            "rnbqkbnr"}; 

        Board b4 = new Board(layout4);

        s4.startingboard = b4;
        s4.blackPlayer = PlayerType.PAWNPUSHERBOT;
        d.save(s4);

        //---------- LEVEL 5 ---------
        Story s5 = new Story();
        s5.title = "Final Fight";
        s5.level = 5;
        s5.description = "Commander. This is it. We've recieved word from our scouts that the strong nation in the lands is trying to take over our village. Can you defend the city?\n";

        String[] layout5 ={
            "RNBQKBNR", 
            "PPPPPPPP",
            "        ",
            "        ",
            "        ",
            "        ",
            "pppppppp",
            "rnbqkbnr"}; 

        Board b5 = new Board(layout5);

        s5.startingboard = b5;
        s5.blackPlayer = PlayerType.RANDBOT;
        d.save(s5);

    }
}