package com.c0nrad.darkchess.engines;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.c0nrad.darkchess.engine.ChessEngine;
import com.c0nrad.darkchess.engine.FogEngine;
import com.c0nrad.darkchess.exceptions.InvalidBoardException;
import com.c0nrad.darkchess.exceptions.InvalidPieceException;
import com.c0nrad.darkchess.exceptions.InvalidPositionException;
import com.c0nrad.darkchess.models.Board;
import com.c0nrad.darkchess.models.Position;

import org.junit.Test;

public class FogEngineTest 
{
    @Test
    public void GetVisibilePiecesTest() throws InvalidPositionException, InvalidBoardException, InvalidPieceException
    {
        String[] b1Str ={
            "RNBQKBNR", //8
            "PPP PPPP", //7
            "    P  p", //6
            "   n   p", //5
            "        ", //4
            "  p     ", //3
            "pp pppp ", //2
            "r bqkbnr"  //1
        //   abcdefgh  
        };
        Board b1 = new Board(b1Str);
        
        // Test White Pawn
        ArrayList<Position> possibleMoves1 = FogEngine.GetVisibilePieces(b1, new Position("b2"));
        ArrayList<Position> solution1 = new ArrayList<Position>();
        AssertPositionListsEqual(possibleMoves1, solution1);

        // Test Knight
        ArrayList<Position> possibleMoves2 = FogEngine.GetVisibilePieces(b1, new Position("d5"));
        ArrayList<Position> solution2 = new ArrayList<Position>();
        solution2.add(new Position("c7"));
        solution2.add(new Position("e7"));
        AssertPositionListsEqual(possibleMoves2, solution2);

        // Test White Pawn
        ArrayList<Position> possibleMoves3 = FogEngine.GetVisibilePieces(b1, new Position("h6"));
        ArrayList<Position> solution3 = new ArrayList<Position>();
        solution3.add(new Position("g7"));
        solution3.add(new Position("h7"));
        AssertPositionListsEqual(possibleMoves3, solution3);

        // Test Black Pawn through
        ArrayList<Position> possibleMoves4 = FogEngine.GetVisibilePieces(b1, new Position("h7"));
        ArrayList<Position> solution4 = new ArrayList<Position>();
        solution4.add(new Position("h6"));
        AssertPositionListsEqual(possibleMoves4, solution4);

        // Test Blocked King
        ArrayList<Position> possibleMoves5 = FogEngine.GetVisibilePieces(b1, new Position("e1"));
        ArrayList<Position> solution5 = new ArrayList<Position>();
        AssertPositionListsEqual(possibleMoves5, solution5);
    }

    public void AssertPositionListsEqual(ArrayList<Position> p1, ArrayList<Position> p2) {
        Set<Position> set1 = new HashSet<Position>(p1);
        Set<Position> set2 = new HashSet<Position>(p2);

        assertTrue("first equals second " + set1 + set2, set1.equals(set2));
    }
}

