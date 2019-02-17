package com.c0nrad.darkchess.engines;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.c0nrad.darkchess.engine.ChessEngine;
import com.c0nrad.darkchess.exceptions.InvalidBoardException;
import com.c0nrad.darkchess.exceptions.InvalidPieceException;
import com.c0nrad.darkchess.exceptions.InvalidPositionException;
import com.c0nrad.darkchess.models.Board;
import com.c0nrad.darkchess.models.Game;
import com.c0nrad.darkchess.models.Position;

import org.junit.Test;

public class ChessEngineTest 
{
    @Test
    public void GetPossibleMovesTest() throws InvalidPositionException, InvalidBoardException, InvalidPieceException
    {
        String[] b1Str ={
            "RNBQKBNR", //8
            "PPP PPPP", //7
            "    P   ", //6
            "   n    ", //5
            "        ", //4
            "  p     ", //3
            "pp ppppp", //2
            "r bqkbnr"  //1
        //   abcdefgh  
        };
        Board b1 = new Board(b1Str);
        
        // Test White Pawn
        ArrayList<Position> possibleMoves1 = ChessEngine.GetPossibleMoves(b1, new Position(0, 1));
        ArrayList<Position> solution1 = new ArrayList<Position>();
        solution1.add(new Position(0, 2));
        solution1.add(new Position(0, 3));
        AssertPositionListsEqual(possibleMoves1, solution1);

        // Test Knight
        ArrayList<Position> possibleMoves2 = ChessEngine.GetPossibleMoves(b1, new Position("d5"));
        ArrayList<Position> solution2 = new ArrayList<Position>();
        solution2.add(new Position("f4"));
        solution2.add(new Position("f6"));
        solution2.add(new Position("c7"));
        solution2.add(new Position("e7"));
        solution2.add(new Position("b4"));
        solution2.add(new Position("b6"));
        solution2.add(new Position("e3"));
        AssertPositionListsEqual(possibleMoves2, solution2);

        // Test Queen
        ArrayList<Position> possibleMoves3 = ChessEngine.GetPossibleMoves(b1, new Position("d1"));
        ArrayList<Position> solution3 = new ArrayList<Position>();
        solution3.add(new Position("c2"));
        solution3.add(new Position("b3"));
        solution3.add(new Position("a4"));
        AssertPositionListsEqual(possibleMoves3, solution3);

        // Test Black Pawn
        ArrayList<Position> possibleMoves4 = ChessEngine.GetPossibleMoves(b1, new Position("e6"));
        ArrayList<Position> solution4 = new ArrayList<Position>();
        solution4.add(new Position("d5"));
        solution4.add(new Position("e5"));
        AssertPositionListsEqual(possibleMoves4, solution4);

        // Test Blocked King
        ArrayList<Position> possibleMoves5 = ChessEngine.GetPossibleMoves(b1, new Position("e1"));
        ArrayList<Position> solution5 = new ArrayList<Position>();
        AssertPositionListsEqual(possibleMoves5, solution5);
    }

    @Test
    public void GetPossibleMovesKnightTest() throws InvalidPositionException, InvalidBoardException, InvalidPieceException
    {
        String[] b1Str ={
            "RNBQKBNR", //8
            "PPPPPPPP", //7
            "        ", //6
            "        ", //5
            "        ", //4
            "        ", //3
            "pppppppp", //2
            "rnbqkbnr"  //1
        //   abcdefgh  
        };
        Board b1 = new Board(b1Str);

        // Test Blocked Knight
        ArrayList<Position> possibleMoves1 = ChessEngine.GetPossibleMoves(b1, new Position("g1"));
        ArrayList<Position> solution1 = new ArrayList<Position>();
        solution1.add(new Position("f3"));
        solution1.add(new Position("h3"));
        AssertPositionListsEqual(possibleMoves1, solution1);

        // Test Blocked Knight
        ArrayList<Position> possibleMoves2 = ChessEngine.GetPossibleMoves(b1, new Position("b1"));
        ArrayList<Position> solution2 = new ArrayList<Position>();
        solution2.add(new Position("a3"));
        solution2.add(new Position("c3"));
        AssertPositionListsEqual(possibleMoves2, solution2);
        
    }

    public void AssertPositionListsEqual(ArrayList<Position> p1, ArrayList<Position> p2) {
        Set<Position> set1 = new HashSet<Position>(p1);
        Set<Position> set2 = new HashSet<Position>(p2);

        assertTrue("first equals second " + set1 + set2, set1.equals(set2));
    }

    @Test
    public void IsGameOverTest() throws InvalidPositionException, InvalidBoardException, InvalidPieceException
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
        assertTrue("game not over", !ChessEngine.IsGameOver(b));

        String[] layout2 ={
            "RNBQKBNR", 
            "PPPPPPPP",
            "        ",
            "        ",
            "        ",
            "        ",
            "pppppppp",
            "rnbq bnr"}; 

        Board b2 = new Board(layout2);

        assertTrue("game is over", ChessEngine.IsGameOver(b2));
        assertTrue("black is winner", !ChessEngine.IsWhiteWinner(b2));
    }
}

