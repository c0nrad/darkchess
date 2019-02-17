package com.c0nrad.darkchess.models;

import com.c0nrad.darkchess.exceptions.InvalidPieceException;
import com.c0nrad.darkchess.models.PieceType;
import com.mongodb.client.MongoCollection;

import org.bson.types.ObjectId;

import xyz.morphia.annotations.Entity;
import xyz.morphia.annotations.Id;

@Entity("pieces")
public class Piece {

    @Id
    ObjectId id;

    public Color color;
    public PieceType type;

    public Piece() {}

    public Piece(PieceType type, Color color) {
        this.color = color;
        this.type = type;
    }

    @Override
    public int hashCode() {
        int out = 0;
        if (this.color == Color.WHITE) {
            out += 10;
        }

        switch (this.type) {
            case PAWN:
                out += 1;
            case KNIGHT:
                out += 1;
            case BISHOP:
                out += 1;
            case ROOK:
                out += 1;
            case QUEEN:
                out += 1;
            case KING:
                out += 1;
                break;
        }       
        return out;
    }

    @Override
    public boolean equals(Object o) { 
        if (o == this) { 
            return true; 
        } 
  
        if (!(o instanceof Piece)) { 
            return false; 
        } 
          
        Piece p = (Piece) o;
        
        return this.color == p.color && this.type == p.type;


    }

    Piece(String p) throws InvalidPieceException {
        p = p.trim();
        if (p.length() != 1) {
            throw new InvalidPieceException("invalid length: "+p);
        }

        if (Character.isLowerCase(p.charAt(0))) {
            this.color = Color.WHITE;
        } else {
           this.color = Color.BLACK;
        }
        p = p.toLowerCase();

        switch (p) {
            case "p":
                this.type = PieceType.PAWN;
                break;
            case "n":
                this.type = PieceType.KNIGHT;
                break;
            case "b":
                this.type = PieceType.BISHOP;
                break;
            case "r":
                this.type = PieceType.ROOK;
                break;
            case "q":
                this.type = PieceType.QUEEN;
                break;
            case "k":
                this.type = PieceType.KING;
                break;
            default:
                throw new InvalidPieceException("not a valid piece type, should be p,n,b,r,q,k: " +p);
            }   
    }

    public String toString() {
        String out = "";

        if (this.type == PieceType.PAWN) {
            out = "P";
        }

        if (this.type == PieceType.KNIGHT) {
            out =  "N";
        }

        if (this.type == PieceType.BISHOP) {
            out =  "B";
        }

        if (this.type == PieceType.ROOK) {
            out =  "R";
        }

        if (this.type == PieceType.QUEEN) {
            out =  "Q";
        }

        if (this.type == PieceType.KING) {
            out =  "K";
        }

        if (out == ""){
            System.err.println("[-] Unvalid piece type" + this.type);
            return "?";
        }

        if (this.color == Color.WHITE) {
            return out.toLowerCase();
        }

        return out;
    }

    // public void Save() {
    //     MongoCollection<Piece> c = MongoConnectionManager.GetPieceCollection();
    //     c.insertOne(this);
    // }
}

