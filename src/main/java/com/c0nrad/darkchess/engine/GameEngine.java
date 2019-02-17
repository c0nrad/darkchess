package com.c0nrad.darkchess.engine;

import com.c0nrad.darkchess.models.Board;
import com.c0nrad.darkchess.models.Color;
import com.c0nrad.darkchess.models.Game;
import com.c0nrad.darkchess.models.Move;
import com.c0nrad.darkchess.models.PlayerType;
import com.c0nrad.darkchess.players.Player;
import com.c0nrad.darkchess.players.bots.BotManager;


import com.c0nrad.darkchess.engine.ChessEngine;
import com.c0nrad.darkchess.engine.FogEngine;
import com.c0nrad.darkchess.exceptions.InvalidBotException;
import com.c0nrad.darkchess.exceptions.InvalidMoveException;
import com.c0nrad.darkchess.exceptions.InvalidPositionException;

public class GameEngine {

    static boolean DEBUG_MODE = false;

    public static Game PlayGame(Player p1, Player p2) {
        Game g = new Game();
        p1.color = Color.WHITE;
        p2.color = Color.BLACK;

        g.whitePlayer = p1.playerType;
        g.blackPlayer = p2.playerType;

        while (!ChessEngine.IsGameOver(g.board)) {
            Player currentPlayer;
            if (g.turn == Color.WHITE) {
                currentPlayer = p1;
            } else {
                currentPlayer = p2;
            } 

            Board fogBoard = FogEngine.GenerateFogBoard(g.board, currentPlayer.color);

            if (DEBUG_MODE) {
                Board whiteFogBoard = FogEngine.GenerateFogBoard(g.board, Color.WHITE);
                Board blackFogBoard = FogEngine.GenerateFogBoard(g.board, Color.BLACK);

                blackFogBoard.Print();
                whiteFogBoard.Print();

                try {
                Thread.sleep(2000);
                } catch (InterruptedException e) {}

            }

            while (true) {
                Move move = currentPlayer.GetMove(fogBoard);
                
                try {
                    ChessEngine.AssertIsValidMove(g.board, move, currentPlayer.color);
                    ApplyMoveToGame(g, move);
                    break;
                } catch (InvalidMoveException | InvalidPositionException ex) {
                    currentPlayer.SendError(ex.toString());
                    continue;
                }
            }
        }

        if (ChessEngine.IsWhiteWinner(g.board)) {
            g.isGameOver = true;
            g.isWhiteWinner = true;
        } else {
            g.isGameOver = true;
            g.isWhiteWinner = false;
        }

        return g;
    }

    public static void ApplyMoveToGame(Game g, Move m) throws InvalidMoveException, InvalidPositionException {
        ChessEngine.AssertIsValidMove(g.board, m, g.turn);
        
        if (g.board.Get(m.to) != null) {
            g.addGraveyard(g.board.Get(m.to));
        }

        g.board.Set(m.to, g.board.Get(m.from));
        g.board.Set(m.from, null);

        if (g.turn == Color.WHITE) {
            g.turn = Color.BLACK;
        } else {
            g.turn = Color.WHITE;
        }

        g.addMove(m);

        if (ChessEngine.IsGameOver(g.board)) {
            if (ChessEngine.IsWhiteWinner(g.board)) {
                g.isGameOver = true;
                g.isWhiteWinner = true;
            } else {
                g.isGameOver = true;
                g.isWhiteWinner = false;
            }
        }
    }

    public static void ApplyBotMoveToGame(Game g) throws InvalidMoveException, InvalidPositionException, InvalidBotException {
        PlayerType botPlayer = null;
        if (g.turn == Color.WHITE) {
            botPlayer = g.whitePlayer;
        } else {
            botPlayer = g.blackPlayer;
        }

        Move move = BotManager.MakeMove(botPlayer, g.board, g.turn);

        ApplyMoveToGame(g, move);
    }
}