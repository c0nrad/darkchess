package com.c0nrad.darkchess.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.c0nrad.darkchess.datastore.GameDatastore;
import com.c0nrad.darkchess.engine.ChessEngine;
import com.c0nrad.darkchess.engine.FogEngine;
import com.c0nrad.darkchess.engine.GameEngine;
import com.c0nrad.darkchess.exceptions.FormValidationException;
import com.c0nrad.darkchess.exceptions.InvalidBotException;
import com.c0nrad.darkchess.exceptions.InvalidMoveException;
import com.c0nrad.darkchess.exceptions.InvalidPositionException;
import com.c0nrad.darkchess.models.Color;
import com.c0nrad.darkchess.models.Game;
import com.c0nrad.darkchess.models.Move;
import com.c0nrad.darkchess.models.PlayerType;
import com.c0nrad.darkchess.models.Position;
import com.c0nrad.darkchess.server.forms.FogMap;
import com.c0nrad.darkchess.server.forms.MoveForm;
import com.c0nrad.darkchess.server.forms.NewGameForm;

import org.bson.types.ObjectId;

@Path("/games")
public class GameResource {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Game> getAllGames() {
    return GameDatastore.FindAll();
  }

  @GET
  @Path("{gameId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Game getGame(@PathParam("gameId") String gameId) {
    Game g = GameDatastore.Find(gameId);
    return FogEngine.GenerateFogGame(g, g.turn);
  }

  @GET
  @Path("{gameId}/fogmap")
  @Produces(MediaType.APPLICATION_JSON)
  public FogMap getGameFogmap(@PathParam("gameId") String gameId) {
    Game g = GameDatastore.Find(gameId);
    FogMap out = new FogMap();
    out.fogmap = FogEngine.GenerateFogMap(g.board, g.turn);
    return out;
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Game newGame(NewGameForm mForm) throws FormValidationException, InvalidMoveException, InvalidBotException, InvalidPositionException { 
    mForm.Validate();

    Game g = new Game();
    g.whitePlayer = mForm.white;
    g.blackPlayer = mForm.black;
    g.startingBoard = g.board;
    g.id = new ObjectId();
    if (g.whitePlayer != PlayerType.HUMAN) {
      if (g.whitePlayer != PlayerType.HUMAN) {
        GameEngine.ApplyBotMoveToGame(g);
      }
    }

    GameDatastore.Save(g);
    return g;
  }

  @POST
  @Path("/{gameId}/moves")
  @Produces(MediaType.APPLICATION_JSON)
  public Response makeMove(@PathParam("gameId") String gameId, MoveForm mForm) throws FormValidationException {
    mForm.Validate();

    Game g = GameDatastore.Find(gameId);

    try {
      ChessEngine.AssertIsValidMove(g.board, mForm.toMove(), g.turn);
      GameEngine.ApplyMoveToGame(g, mForm.toMove());
    } catch (InvalidPositionException | InvalidMoveException ex) {
      return Response.status(400).entity(ex.toString()).type("text/plain").build();
    }

    // Is Bot Move Next?
    if (!g.isGameOver) {
      try {
        GameEngine.ApplyBotMoveToGame(g);
      } catch (InvalidPositionException | InvalidMoveException | InvalidBotException ex) {
        return Response.status(400).entity(ex.toString()).type("text/plain").build();
      }
    }

    GameDatastore.Update(g);
    return Response.ok(FogEngine.GenerateFogGame(g, g.turn)).build();
  }

  @GET
  @Path("/{gameId}/moves")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getPossibleMoves(@PathParam("gameId") String gameId, @QueryParam("x") int x, @QueryParam("y") int y) {
    Game g = GameDatastore.Find(gameId);
    ArrayList<Position> moves = new ArrayList<Position>();


    try {
      // Can player see the moves?
      if (g.board.Get(new Position(x, y)) != null && g.board.Get(new Position(x, y)).color == Color.WHITE) {
        moves = ChessEngine.GetPossibleMoves(g.board, new Position(x, y));
      }
    } catch (InvalidPositionException ex) {}

    return Response.status(200).entity(moves.toArray(new Position[0])).build();
  }
}