package com.c0nrad.darkchess.players;

import java.util.Scanner;

import com.c0nrad.darkchess.exceptions.InvalidPositionException;
import com.c0nrad.darkchess.models.Board;
import com.c0nrad.darkchess.models.Board;
import com.c0nrad.darkchess.models.Move;
import com.c0nrad.darkchess.models.Piece;
import com.c0nrad.darkchess.models.Position;
import com.c0nrad.darkchess.players.Player;

public class LocalPlayer extends Player {

    Scanner reader;

    public LocalPlayer() {
        reader = new Scanner(System.in); 
    }
    

    @Override
    public Move GetMove(Board b) { 
        PrintBoard(b);
 
        Move m = new Move();
        
        while (true) {
            System.out.println("Enter From: ");
            try {
                m.from = new Position(reader.nextLine()); 
            } catch (InvalidPositionException e) {
                System.err.println(e);
                continue;
            }

            break;
        }

        while (true) {
            System.out.println("Enter To: ");
            try {
                m.to = new Position(reader.nextLine()); 
            } catch (InvalidPositionException e) {
                System.err.println(e);
                continue;
            }

            break;
        }

        return m; 
    }


    @Override
    public void SendError(String error) {
        System.err.println(error);
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