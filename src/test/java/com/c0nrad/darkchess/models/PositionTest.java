package com.c0nrad.darkchess.models;

import static org.junit.Assert.assertTrue;

import com.c0nrad.darkchess.exceptions.InvalidPositionException;

import org.junit.Test;

public class PositionTest 
{
    @Test
    public void convertChessNotation() throws InvalidPositionException
    {
        Position p1 = new Position("e2");

        assertTrue("p1.x" + p1.x + " p1.y" + p1.y,  p1.x == 4 );
        assertTrue("p1.x" + p1.x + " p1.y" + p1.y, p1.y == 1 );

        assertTrue("to chess notation " + p1.toString(), p1.toString().equals("e2"));
    }


    @Test
    public void equalsTest() throws InvalidPositionException {
        assertTrue("same position should be equal", new Position(3, 4).equals(new Position(3, 4)));

        assertTrue("same position from string should be equal", new Position("a3").equals(new Position("a3")));

    }

    

}
