package com.c0nrad.darkchess.engine;

import com.c0nrad.darkchess.exceptions.InvalidMoveException;
import com.c0nrad.darkchess.exceptions.InvalidPositionException;
import com.c0nrad.darkchess.models.Color;
import com.c0nrad.darkchess.models.Game;
import com.c0nrad.darkchess.models.HistoryView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HistoryEngine {

    private static final Logger logger = LoggerFactory.getLogger(HistoryEngine.class);

    public static HistoryView GenerateHistoryView(Game game, int turnCount) {
        HistoryView out = new HistoryView();
        
        Game outGame = new Game();
        outGame.whitePlayer = game.whitePlayer;
        outGame.blackPlayer = game.blackPlayer;
        outGame.board = game.startingBoard;

        if (turnCount > game.getMoves().length) {
            turnCount = game.getMoves().length;
        }

        for (int i = 0; i < turnCount; i++) {
            try {
                GameEngine.ApplyMoveToGame(outGame, game.getMoves()[i]);
            } catch (InvalidMoveException | InvalidPositionException ex) {
                logger.warn("his should be not possible... we're reapplying an old move {}", ex);
            }
        }

        out.board = outGame.board;
        out.turnCount = turnCount;
        out.maxTurnCount = game.getMoves().length;
        out.fogBoardBlack = FogEngine.GenerateFogBoard(out.board, Color.BLACK);
        out.fogBoardWhite = FogEngine.GenerateFogBoard(out.board, Color.WHITE);
        out.graveyard = outGame.getGraveyard();
        out.moves = outGame.getMoves();
        out.gameId = game.id;
        return out;
    }
}