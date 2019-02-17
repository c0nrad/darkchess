package com.c0nrad.darkchess.engine;

import java.util.ArrayList;

import com.c0nrad.darkchess.exceptions.InvalidPositionException;
import com.c0nrad.darkchess.models.Board;
import com.c0nrad.darkchess.models.Color;
import com.c0nrad.darkchess.models.Game;
import com.c0nrad.darkchess.models.Move;
import com.c0nrad.darkchess.models.Piece;
import com.c0nrad.darkchess.models.PieceType;
import com.c0nrad.darkchess.models.Position;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FogEngine {

    private static final Logger logger = LoggerFactory.getLogger(FogEngine.class);

    public static Board GenerateFogBoard(Board board, Color color) {
        Board fb = new Board(true);

        // Copy Over Color
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++){
                Position p = null;
                try {
                    p = new Position(x, y);
                } catch(InvalidPositionException e) {
                    logger.error("this should be impossible {} {}",  x, y);
                    return fb;
                }


                if (!board.IsEmpty(p)) {
                    Piece piece;
    
                    piece = board.Get(p);

                    if (piece.color == color) {
                        fb.Set(p, piece);

                        ArrayList<Position> visiblePieces = GetVisibilePieces(board, p);
                        for (Position visiblePiece : visiblePieces) {
                            fb.Set(visiblePiece, board.Get(visiblePiece));
                        }
                    }                   
                }
            }
        }
        return fb;
    }

    public static Game GenerateFogGame(Game game, Color color) {
        game.board = GenerateFogBoard(game.board, color);
        game.fogmap = GenerateFogMap(game.board, color);

        Move[] moves = game.getMoves();
        for (int i = 0; i < moves.length; i++) {
            if (color == Color.WHITE && i % 2 == 1) {
                moves[i].isHidden = true;
                try {
                    moves[i].from = new Position(0, 0);
                    moves[i].to = new Position(0, 0);
                } catch (InvalidPositionException ex) {}
            }
        }
        game.setMoves(moves);

        return game;
    }

    // Which squares are invisible?
    public static ArrayList<Boolean> GenerateFogMap(Board board, Color color) {
        ArrayList<Boolean> out = new ArrayList<Boolean>();

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Position p = null;
                try {
                    p = new Position(x, y);
                } catch (InvalidPositionException ex) {}

                if (!board.IsEmpty(p) && board.Get(p).color == color) {
                    out.add(false);
                } else {
                    out.add(true);
                }
            }
        }

        ArrayList<Move> moves = ChessEngine.GetAllPossibleMoves(board, color);
        for (Move m : moves) {
            out.set(m.to.hashCode(), false);
        }

        ArrayList<Position> pawnVisibilePositions = FogEngine.GetPawnVisibileSquares(board, color);
        for (Position p : pawnVisibilePositions) {
            out.set(p.hashCode(), false);
        }

        return out;
    }

    public static ArrayList<Position> GetPawnVisibileSquares(Board board, Color color) {
        ArrayList<Position> out = new ArrayList<Position>();

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Position p = null;
                try {
                    p = new Position(x, y);
                } catch (InvalidPositionException ex) {}

                if (!board.IsEmpty(p) && board.Get(p).color == color && board.Get(p).type == PieceType.PAWN) {
                    Piece piece = board.Get(p);
                    if (piece.color == Color.BLACK) {
                        try {
                            Position downOne = new Position(p.x, p.y - 1);
                            out.add(downOne);
                        } catch (InvalidPositionException ex) {}
        
                        try {
                            if (p.y == 6) {
                                Position downTwo = new Position(p.x, p.y -2);
                                out.add(downTwo);
                            }
                        } catch (InvalidPositionException ex) {}
        
                        try {
                            Position left = new Position(p.x - 1, p.y - 1);
                            out.add(left);
                        } catch (InvalidPositionException ex) {}
        
                        try {
                            Position right = new Position(p.x + 1, p.y - 1 );
                            out.add(right);
                        } catch (InvalidPositionException ex) {}
                    } else {

                        try {
                            Position upOne = new Position(p.x, p.y + 1);
                            out.add(upOne);
                        } catch (InvalidPositionException ex) {}
        
                        try {
                            if (p.y == 1) {
                                Position upTwo = new Position(p.x, p.y + 2);
                                out.add(upTwo);
                            }
                        } catch (InvalidPositionException ex) {}
        
                        try {
                            Position left = new Position(p.x - 1, p.y + 1);
                            out.add(left);
                        } catch (InvalidPositionException ex) {}
        
                        try {
                            Position right = new Position(p.x + 1, p.y + 1 );
                            out.add(right);
                        } catch (InvalidPositionException ex) {}
                    }
                }
            }
        }

        return out;
    }

    public static ArrayList<Position> GetVisibilePieces(Board board, Position p) {
        ArrayList<Position> out = new ArrayList<Position>();

        Piece piece = board.Get(p);

        ArrayList<Position> possibleMoves = null;
        try {
            possibleMoves = ChessEngine.GetPossibleMoves(board, p);
        } catch (InvalidPositionException ex) {
            board.Print();
            logger.error("something went very wrong {}", ex);
            return null;    
        }

        // We have to special case Pawns because they can see ahead of them, but not move there.
        // We also allow them to see diag, even if they can't necessarily move there (empty)
        if (piece.type == PieceType.PAWN) {

            if (piece.color == Color.BLACK) {

                try {
                    Position downOne = new Position(p.x, p.y - 1);
                    if (!board.IsEmpty(downOne) && board.Get(downOne).color == Color.WHITE) {
                        possibleMoves.add(downOne);
                    }
                } catch (InvalidPositionException ex) {}

                try {
                    Position downOne = new Position(p.x, p.y - 1);
                    Position downTwo = new Position(p.x, p.y -2);
                    if (p.y == 6 && board.IsEmpty(downOne) && !board.IsEmpty(downTwo) && board.Get(downTwo).color == Color.WHITE) {
                        possibleMoves.add(downTwo);
                    }
                } catch (InvalidPositionException ex) {}

                
            } else if (piece.color == Color.WHITE) {

                try {
                    Position upOne = new Position(p.x, p.y + 1);
                    if (!board.IsEmpty(upOne) && board.Get(upOne).color == Color.BLACK) {
                        possibleMoves.add(upOne);
                    }
                } catch (InvalidPositionException ex) {}

                try {
                    Position upOne = new Position(p.x, p.y + 1);
                    Position upTwo = new Position(p.x, p.y + 2);
                    if (p.y == 1 && board.IsEmpty(upOne) && !board.IsEmpty(upTwo) && board.Get(upTwo).color == Color.BLACK) {
                        possibleMoves.add(upTwo);
                    }
                } catch (InvalidPositionException ex) {}

                // try {
                //     Position left = new Position(p.x - 1, p.y + 1);
                //     possibleMoves.add(left);
                // } catch (InvalidPositionException ex) {}

                // try {
                //     Position right = new Position(p.x + 1, p.y + 1 );
                //     possibleMoves.add(right);
                // } catch (InvalidPositionException ex) {}
            }  
        }

        for (Position possibleMove : possibleMoves) {
            if (!board.IsEmpty(possibleMove) && board.Get(possibleMove).color != piece.color) {
                out.add(possibleMove);
            }
        }

        return out;
    }
}