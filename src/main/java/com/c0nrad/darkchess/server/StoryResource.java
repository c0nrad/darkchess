package com.c0nrad.darkchess.server;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.c0nrad.darkchess.datastore.GameDatastore;
import com.c0nrad.darkchess.datastore.StoryDatastore;
import com.c0nrad.darkchess.engine.FogEngine;
import com.c0nrad.darkchess.models.Color;
import com.c0nrad.darkchess.models.Game;
import com.c0nrad.darkchess.models.PlayerType;
import com.c0nrad.darkchess.models.Story;

@Path("/stories")
public class StoryResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON) 
    public List<Story> getAllStories() {
        return StoryDatastore.FindAll();
    }

    @GET 
    @Path("{level}")
    @Produces(MediaType.APPLICATION_JSON)
    public Story getStory(@PathParam("level") int level) {
        return StoryDatastore.FindByLevel(level);
    }

    @POST
    @Path("{level}")
    @Produces(MediaType.APPLICATION_JSON)
    public Game startStory(@PathParam("level") int level) {
        Story s = StoryDatastore.FindByLevel(level);
        Game g = new Game();
        g.board = s.startingboard;
        g.blackPlayer = s.blackPlayer;
        g.whitePlayer = PlayerType.HUMAN;
        g.startingBoard = g.board;
        GameDatastore.Save(g);

        g = FogEngine.GenerateFogGame(g, Color.WHITE);
        return g;
    }
}