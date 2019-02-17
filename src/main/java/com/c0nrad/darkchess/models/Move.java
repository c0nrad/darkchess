package com.c0nrad.darkchess.models;

public class Move {
    public Position from;
    public Position to;

    public boolean isHidden;

    @Override
    public boolean equals(Object o) { 
        if (o == this) { 
            return true; 
        } 
  
        if (!(o instanceof Move)) { 
            return false; 
        } 
          
        Move m = (Move) o;
        return this.from.equals(m.from) && this.to.equals(m.to);
    }

    public String toString() {
        return from.toString() + " -> " + to.toString();
    }

    public String getFrom() {
        return from.toString();
    }

    public String getTo() {
        return to.toString();
    }

}