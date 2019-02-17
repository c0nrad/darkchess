package com.c0nrad.darkchess.models;

import static org.junit.Assert.assertTrue;

import com.c0nrad.darkchess.exceptions.InvalidBoardException;
import com.c0nrad.darkchess.exceptions.InvalidPieceException;
import com.c0nrad.darkchess.exceptions.InvalidPositionException;

import org.junit.Test;

public class BoardTest 
{

    @Test
    public void isEmptyTest() throws InvalidPositionException {
        Board b = new Board(false);
        assertTrue("null piece", b.IsEmpty(new Position(3, 3)));
        assertTrue("not null piece", !b.IsEmpty(new Position(7, 7)));
    }

    @Test
    public void loadBoardTest() throws InvalidPositionException, InvalidBoardException, InvalidPieceException
    {
        String[] layout ={
            "RNBQKBNR", 
            "PPPPPPPP",
            "        ",
            "        ",
            "        ",
            "        ",
            "pppppppp",
            "rnbqkbnr"}; 

        Board b = new Board(layout);

        assertTrue("not null piece", !b.IsEmpty(new Position(2, 0)));
        assertTrue("Check for white bishop: " + b.Get(new Position(2, 0)), b.Get(new Position(2, 0)).equals(new Piece(PieceType.BISHOP, Color.WHITE)));
    }

    public static void PrintBoard(Board b) {
        try {
    
            for (int y = 7; y >= 0; y--) {
                System.out.print(y+1);
                System.out.print(" ");

                for (int x = 0; x < Board.BOARD_SIZE; x++) {
                    if (b.IsEmpty(new Position(x, y))) {
                        System.out.print(" ");
                    } else {
                        Piece p = b.Get(new Position(x, y));
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
