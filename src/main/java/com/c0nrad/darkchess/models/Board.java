package com.c0nrad.darkchess.models;

import com.c0nrad.darkchess.models.Position;

import org.bson.types.ObjectId;

import xyz.morphia.annotations.Embedded;
import xyz.morphia.annotations.Entity;
import xyz.morphia.annotations.Id;

import com.c0nrad.darkchess.models.Position;
import com.c0nrad.darkchess.exceptions.InvalidBoardException;
import com.c0nrad.darkchess.exceptions.InvalidPieceException;
import com.c0nrad.darkchess.exceptions.InvalidPositionException;

@Entity("boards")
public class Board { 
    public static final int BOARD_SIZE = 8;
    
    @Id
    public ObjectId id;
    
    // Morphia doesn't appear to support 2d arrays...
    // I don't get paid enough for this shit
    @Embedded
    public Piece[] pieces;

    public Board() {}
    public Board(boolean empty) {
        this.pieces = new Piece[BOARD_SIZE*BOARD_SIZE];

        if (!empty) {
            String[] layout ={
                "RNBQKBNR", 
                "PPPPPPPP",
                "        ",
                "        ",
                "        ",
                "        ",
                "pppppppp",
                "rnbqkbnr"}; 
    
            try {
                Board b = new Board(layout);
                this.pieces = b.pieces;
            } catch(InvalidBoardException | InvalidPieceException ex) {

            }
        }
    }

    public Board(String[] layout) throws InvalidBoardException, InvalidPieceException {
        this.pieces = new Piece[BOARD_SIZE*BOARD_SIZE];

        if (layout.length != Board.BOARD_SIZE) {
            throw new InvalidBoardException("Board height is not correct size!" + layout.length);
        }

        for (int y = 0; y < Board.BOARD_SIZE; y++) {
            String line = layout[y];
            if (line.length() != Board.BOARD_SIZE) {
                throw new InvalidBoardException("Board line is not correct size!" + line.length() + " line: " +  line);
            }

            for (int x = 0; x < Board.BOARD_SIZE; x++) {
                String pieceStr = line.substring(x, x+1);
                if (pieceStr.equals(" ")) {
                    continue;
                } else {
                    try {
                        this.Set(new Position(x, 7-y),new Piece(pieceStr));
                    } catch (InvalidPositionException e) {}
                }
            }
        }

    }

    public boolean IsEmpty(Position p) {
        return this.pieces[p.x + (p.y*8)] == null;
    }

    public Piece Get(Position p) {
        return this.pieces[p.x + (p.y*8)];
    }

    public void Set(Position p, Piece piece) {
        this.pieces[p.x + (p.y*8)] = piece;
    }

    public void Print() {
        try {

            for (int y = 7; y >= 0; y--) {
                System.out.print(y+1);
                System.out.print(" ");

                for (int x = 0; x < Board.BOARD_SIZE; x++) {
                    if (this.IsEmpty(new Position(x, y))) {
                        System.out.print(" ");
                    } else {
                        Piece p = this.Get(new Position(x, y));
                        System.out.print(p);
                    }
                }
                System.out.println("");
            }
            System.out.println("  abcdefgh");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


