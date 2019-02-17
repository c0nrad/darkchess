package com.c0nrad.darkchess.engine;

import java.util.ArrayList;

import com.c0nrad.darkchess.exceptions.InvalidPositionException;
import com.c0nrad.darkchess.exceptions.InvalidMoveException;

import com.c0nrad.darkchess.models.Board;
import com.c0nrad.darkchess.models.Color;
import com.c0nrad.darkchess.models.Move;
import com.c0nrad.darkchess.models.Position;
import com.c0nrad.darkchess.models.PieceType;
import com.c0nrad.darkchess.models.Piece;

public class ChessEngine {

    public static void AssertIsValidMove(Board board, Move m, Color color)  throws InvalidPositionException, InvalidMoveException{
        m.from.AssertIsValid();
        m.to.AssertIsValid();

        if (board.IsEmpty(m.from)) {
            throw new InvalidMoveException("no piece at from");
        }

        if (board.Get(m.from).color != color) {
            throw new InvalidMoveException("can't move opposite color");
        }

        if (!board.IsEmpty(m.to) && board.Get(m.to).color == color) {
            throw new InvalidMoveException("can't remove own piece");
        }

        ArrayList<Position> possibleMoves = GetPossibleMoves(board, m.from);
        for (Position possibleMove : possibleMoves) {
            if (possibleMove.equals(m.to)) {
                return;
            }    
        }

        throw new InvalidMoveException("piece can not move there");
    }

    public static ArrayList<Position> GetPositionsInDirection(Board board, Position start, int dx, int dy) throws InvalidPositionException {
        start.AssertIsValid();

        ArrayList<Position> out = new ArrayList<Position>();
        int x = start.x;
        int y = start.y;

        while(true) {
            x += dx;
            y += dy;

            Position p = null; 
            try {
                p = new Position(x, y);
                p.AssertIsValid();
            } catch (InvalidPositionException ex) {
                return out;
            }
            
            if (board.Get(p) == null) {
                out.add(p);
            } else if (board.Get(p).color != board.Get(start).color) {
                out.add(p);
                return out;
            } else if (board.Get(p).color == board.Get(start).color) {
                return out;
            }
        }
    }

    public static ArrayList<Move> GetAllPossibleMoves(Board board, Color color) {
        ArrayList<Move> out = new ArrayList<Move>();

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                try {
                    Position from = new Position(x, y);
                    if (!board.IsEmpty(from) && board.Get(from).color == color) {
                        ArrayList<Position> tos = GetPossibleMoves(board, from);
                        for (Position to : tos) {
                            Move m = new Move();
                            m.from = from; 
                            m.to = to;
                            out.add(m);
                        }
                    }
                } catch(InvalidPositionException ex) {}
            }
        }
        return out;
    }

    public static ArrayList<Position> GetPossibleMoves(Board board, Position p) throws InvalidPositionException {
        if (board.IsEmpty(p)) {
            throw new InvalidPositionException("no piece at position " +  p);
        }

        p.AssertIsValid();
        

        Piece piece = board.Get(p);
        ArrayList<Position> possibleMoves = new ArrayList<Position>();
        
        switch(piece.type) {
            case KING:
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        try {
                            possibleMoves.add(new Position(p.x + dx, p.y + dy));
                        } catch (InvalidPositionException ex) {
                            continue;
                        }
                    }
                }
                break;
            case QUEEN:
                possibleMoves.addAll(GetPositionsInDirection(board, p, 0, 1));
                possibleMoves.addAll(GetPositionsInDirection(board, p, 0, -1));
                possibleMoves.addAll(GetPositionsInDirection(board, p, 1, 1));
                possibleMoves.addAll(GetPositionsInDirection(board, p, 1, -1)); 
                possibleMoves.addAll(GetPositionsInDirection(board, p, 1, 0));
                possibleMoves.addAll(GetPositionsInDirection(board, p, -1, 1));
                possibleMoves.addAll(GetPositionsInDirection(board, p, -1, -1));
                possibleMoves.addAll(GetPositionsInDirection(board, p, -1, 0));
                break;
            case BISHOP: 
                possibleMoves.addAll(GetPositionsInDirection(board, p, 1, 1));
                possibleMoves.addAll(GetPositionsInDirection(board, p, 1, -1)); 
                possibleMoves.addAll(GetPositionsInDirection(board, p, -1, 1));
                possibleMoves.addAll(GetPositionsInDirection(board, p, -1, -1));
                break;
            case ROOK: 
                possibleMoves.addAll(GetPositionsInDirection(board, p, 0, 1));
                possibleMoves.addAll(GetPositionsInDirection(board, p, 0, -1));
                possibleMoves.addAll(GetPositionsInDirection(board, p, 1, 0));
                possibleMoves.addAll(GetPositionsInDirection(board, p, -1, 0));
                break;
            case KNIGHT:

                for (int dx = -2; dx <= 2; dx+=4) {
                    for (int dy = -1; dy <= 1; dy+=2) {
                        try {
                            possibleMoves.add(new Position(p.x + dx, p.y + dy));
                        } catch (InvalidPositionException ex) {}

                        try {
                            possibleMoves.add(new Position(p.x + dy, p.y + dx));
                        } catch (InvalidPositionException ex) {}
                    }
                }

                break;
            case PAWN: 
                // So... pawns are intersting. you can't take the piece in front of you. we need to check that manually
                if (piece.color == Color.BLACK) {

                    // Can I take diag?

                    try {
                        Position left = new Position(p.x - 1, p.y - 1);
                        if (!board.IsEmpty(left) && board.Get(left).color == Color.WHITE) {
                            possibleMoves.add(left);
                        }
                    } catch (InvalidPositionException ex) {}

                    try {
                        Position right = new Position(p.x + 1, p.y - 1 );
                        if (!board.IsEmpty(right) && board.Get(right).color == Color.WHITE) {
                            possibleMoves.add(right);
                        }
                    } catch (InvalidPositionException ex) {}

                    try {
                        Position downOne = new Position(p.x, p.y - 1);
                        if (board.IsEmpty(downOne)) {
                            possibleMoves.add(downOne);

                            Position downTwo = new Position(p.x, p.y - 2);
                            if (p.y == 6 && board.IsEmpty(downTwo)) {
                                possibleMoves.add(downTwo);
                            }
                        }
                    } catch (InvalidPositionException ex) {}

                } else if (piece.color == Color.WHITE) {

                    try {
                        Position left = new Position(p.x - 1, p.y + 1);
                        if (!board.IsEmpty(left) && board.Get(left).color == Color.BLACK) {
                            possibleMoves.add(left);
                        }
                    } catch (InvalidPositionException ex) {}

                    try {
                        Position right = new Position(p.x + 1, p.y + 1 );
                        if (!board.IsEmpty(right) && board.Get(right).color == Color.BLACK) {
                            possibleMoves.add(right);
                        }
                    } catch (InvalidPositionException ex) {}


                    try {
                        Position upOne = new Position(p.x, p.y + 1);
                        if (board.IsEmpty(upOne)) {
                            possibleMoves.add(upOne);

                            Position upTwo = new Position(p.x, p.y + 2);
                            if (p.y == 1 && board.IsEmpty(upTwo)) {
                                possibleMoves.add(upTwo);
                            }
                        }
                    }catch (InvalidPositionException ex) {}
                }  
        }

        return filterValidSpots(board, p, possibleMoves);

    }

    private static ArrayList<Position> filterValidSpots(Board board, Position start, ArrayList<Position> attempts) 
        throws InvalidPositionException {
        Piece piece = board.Get(start);
        ArrayList<Position> out = new ArrayList<Position>();

        
        for (Position p : attempts) {
            if (board.Get(p) == null || board.Get(p).color != piece.color) {
                out.add(p);
            }
        }

        return out;
    }

    public static boolean IsGameOver(Board board) {
        boolean hasWhiteKing = false, hasBlackKing = false;
        for (int y = 0; y < Board.BOARD_SIZE; y++) {
            for (int x = 0; x < Board.BOARD_SIZE; x++) {
                Piece piece = null;
                try {
                    piece = board.Get(new Position(x, y));
                } catch (Exception e) {
                }

                if (piece == null) {
                    continue;
                }

                if (piece.color == Color.WHITE && piece.type == PieceType.KING) {
                    hasWhiteKing = true;
                }

                if (piece.color == Color.BLACK && piece.type == PieceType.KING) {
                    hasBlackKing = true;
                }
            }
        }
        return !(hasWhiteKing && hasBlackKing);
    }

    public static boolean IsWhiteWinner(Board board) {
        boolean hasWhiteKing = false, hasBlackKing = false;

        for (int y = 0; y < Board.BOARD_SIZE; y++) {
            for (int x = 0; x < Board.BOARD_SIZE; x++) {
                Piece piece = null;
                try {
                    piece = board.Get(new Position(x, y));
                } catch (Exception e) {
                }

                if (piece == null) {
                    continue;
                }

                if (piece.color == Color.WHITE && piece.type == PieceType.KING) {
                    hasWhiteKing = true;
                }

                if (piece.color == Color.BLACK && piece.type == PieceType.KING) {
                    hasBlackKing = true;
                }

              
            }
        }
        return !hasBlackKing && hasWhiteKing;
    }

}