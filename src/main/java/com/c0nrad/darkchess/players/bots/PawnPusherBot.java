package com.c0nrad.darkchess.players.bots;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Random;

import com.c0nrad.darkchess.engine.ChessEngine;
import com.c0nrad.darkchess.exceptions.InvalidStateException;
import com.c0nrad.darkchess.exceptions.InvalidPositionException;
import com.c0nrad.darkchess.models.Board;
import com.c0nrad.darkchess.models.Move;
import com.c0nrad.darkchess.models.PieceType;
import com.c0nrad.darkchess.models.PlayerType;
import com.c0nrad.darkchess.models.Position;
import com.c0nrad.darkchess.players.Player;

public class PawnPusherBot extends Player {

    Random rand;
    public PawnPusherBot() {
        rand = new SecureRandom();
        this.playerType = PlayerType.PAWNPUSHERBOT;
    }

    public void SendError(String error) {
        System.err.println(error);
    }

    public Move GetMove(Board board) {
        ArrayList<Move> possibleMoves = ChessEngine.GetAllPossibleMoves(board, this.color);
        Collections.shuffle(possibleMoves);

        Map<PieceType, Integer> pointMapping = BotUtil.GetPiecePoints();
        Move bestMove = null;
        int mostPoints =-1;

        for (Move m : possibleMoves) {
            if (!board.IsEmpty(m.to) && board.Get(m.from).type == PieceType.PAWN) {
                PieceType capture = board.Get(m.to).type;
                if (pointMapping.get(capture) > mostPoints) {
                    mostPoints = pointMapping.get(capture);
                    bestMove = m;
                }
            }
        }

        if (bestMove == null) {
            return BotUtil.getBestMovebyKillPoints(board, this.color);
        }

     
        return bestMove;
    }
}