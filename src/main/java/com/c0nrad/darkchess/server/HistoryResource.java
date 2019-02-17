package com.c0nrad.darkchess.server;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.c0nrad.darkchess.datastore.GameDatastore;
import com.c0nrad.darkchess.engine.ChessEngine;
import com.c0nrad.darkchess.engine.HistoryEngine;
import com.c0nrad.darkchess.exceptions.InvalidPositionException;
import com.c0nrad.darkchess.models.Game;
import com.c0nrad.darkchess.models.HistoryView;
import com.c0nrad.darkchess.models.Position;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/history")
public class HistoryResource {

    private static final Logger logger = LoggerFactory.getLogger(HistoryResource.class);

    @GET
    @Path("{gameId}/{turnCount}")
    @Produces(MediaType.APPLICATION_JSON)
    public HistoryView getGameHistoryView(
        @PathParam("gameId") String gameId,
        @PathParam("turnCount") int turnCount) {
    
        Game game = GameDatastore.Find(gameId);
        return HistoryEngine.GenerateHistoryView(game, turnCount);
    }

    @GET
    @Path("/{gameId}/{turnCount}/moves")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPossibleMoves(
        @PathParam("gameId") String gameId, 
        @PathParam("turnCount") int turnCount,
        @QueryParam("x") int x, 
        @QueryParam("y") int y) {
      Game game = GameDatastore.Find(gameId);
      HistoryView history = HistoryEngine.GenerateHistoryView(game, turnCount);

      ArrayList<Position> moves = null;
      try {
        moves = ChessEngine.GetPossibleMoves(history.board, new Position(x, y));
      } catch (InvalidPositionException ex) {
        logger.warn("unable to get all moves {}", ex);
        return Response.status(400).entity(ex.toString()).type("text/plain").build();
      }

      return Response.status(200).entity(moves.toArray(new Position[0])).build();
    }
}