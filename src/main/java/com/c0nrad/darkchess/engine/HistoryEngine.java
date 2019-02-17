package com.c0nrad.darkchess.engine;

import com.c0nrad.darkchess.exceptions.InvalidMoveException;
import com.c0nrad.darkchess.exceptions.InvalidPositionException;
import com.c0nrad.darkchess.models.Color;
import com.c0nrad.darkchess.models.Game;
import com.c0nrad.darkchess.models.HistoryView;

public class HistoryEngine {

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
                System.out.println("This should be not possible..."+ex);
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