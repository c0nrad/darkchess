package com.c0nrad.darkchess.players.bots;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.c0nrad.darkchess.engine.ChessEngine;
import com.c0nrad.darkchess.models.Board;
import com.c0nrad.darkchess.models.Color;
import com.c0nrad.darkchess.models.Move;
import com.c0nrad.darkchess.models.Piece;
import com.c0nrad.darkchess.models.PieceType;
import com.c0nrad.darkchess.models.Position;


public class BotUtil {

    public static Map<PieceType, Integer> GetPiecePoints() {
        Map<PieceType, Integer> out = new HashMap<PieceType, Integer>();
        out.put(PieceType.PAWN, 1);
        out.put(PieceType.KNIGHT, 3);
        out.put(PieceType.BISHOP, 3);
        out.put(PieceType.ROOK, 5);
        out.put(PieceType.QUEEN, 8);
        out.put(PieceType.KING, 100);
        return out;
    }

    public static Position randomPosition() {
        Random rand = new SecureRandom();
        Position out = new Position();
        out.x = rand.nextInt(8);
        out.y = rand.nextInt(8);
        return out;
    }

    public static Move getRandomMove(Board board, Color color) {
        Random rand = new SecureRandom();

        ArrayList<Move> moves = ChessEngine.GetAllPossibleMoves(board, color);
        return moves.get(rand.nextInt(moves.size()));
    }

    public static Move getBestMovebyKillPoints(Board board, Color color) {
        ArrayList<Move> moves = ChessEngine.GetAllPossibleMoves(board, color);
        Collections.shuffle(moves);

        Map<PieceType, Integer> pointMapping = GetPiecePoints();
        Move bestMove = null;
        int mostPoints =-1;

        for (Move possibleMove : moves) {
            if (!board.IsEmpty(possibleMove.to)) {
                PieceType capture = board.Get(possibleMove.to).type;
                if (pointMapping.get(capture) > mostPoints) {
                    mostPoints = pointMapping.get(capture);
                    bestMove = possibleMove;
                }
            }
        }

        if (bestMove == null) {
            return moves.get(0);
        }

        return bestMove;
    }
}