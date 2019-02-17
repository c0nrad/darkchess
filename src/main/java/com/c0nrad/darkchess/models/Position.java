package com.c0nrad.darkchess.models;

import com.c0nrad.darkchess.exceptions.InvalidPositionException;
import com.mongodb.client.MongoCollection;

public class Position {
    public int x, y;

    public Position() {}

    public Position(int x, int y) throws InvalidPositionException {
        this.x = x;
        this.y = y;

        AssertIsValid();
    }

    public void AssertIsValid() throws InvalidPositionException {
        if (x >= 8 || x < 0)  {
            throw new InvalidPositionException("Invalid position for x: " + String.valueOf(x));
        }

        if (y >= 8 || y < 0)  {
            throw new InvalidPositionException("Invalid position for y: " + String.valueOf(y));
        }
    } 

    public Position(String in) throws InvalidPositionException {
        in = in.trim();
        if (in.length() != 2) {
            throw new InvalidPositionException("Invalid position should only be two characters long"+ in);   
        }

        this.y = Integer.parseInt(in.substring(1))- 1;
        this.x = in.charAt(0) - 'a';

        AssertIsValid();
    }

    public String toString() {
        return new String(Character.toChars('a'+x)) + String.valueOf(y+1);
    }

    @Override
    public boolean equals(Object o) { 
        if (o == this) { 
            return true; 
        } 

        if (!(o instanceof Position)) { 
            return false; 
        } 

        Position p2 = (Position) o;
        return p2.x == this.x && p2.y == this.y;
    }

    @Override
    public int hashCode() {
        return this.x + this.y*8;
    }

    // public void Save() {
    //     MongoCollection<Position> c = MongoConnectionManager.GetPositionCollection();
    //     c.insertOne(this);
    // }
}