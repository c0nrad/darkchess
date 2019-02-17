package com.c0nrad.darkchess.models;

import org.bson.types.ObjectId;

import xyz.morphia.annotations.Entity;
import xyz.morphia.annotations.Id;

@Entity("stories")
public class Story { 
    @Id
    public int level;
   
    public String title;
    public String description;


    public Board startingboard;
    public PlayerType whitePlayer;
    public PlayerType blackPlayer;
}
