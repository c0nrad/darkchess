package com.c0nrad.darkchess.models;

import java.util.ArrayList;
import java.util.Arrays;

import com.c0nrad.darkchess.models.Color;
import com.c0nrad.darkchess.models.Move;
import com.c0nrad.darkchess.models.Piece;
import com.c0nrad.darkchess.models.Position;

import com.c0nrad.darkchess.players.Player;
import com.mongodb.client.MongoCollection;

import org.bson.types.ObjectId;

import xyz.morphia.annotations.Entity;
import xyz.morphia.annotations.Id;

@Entity("games")
public class Game {
    @Id
    public ObjectId id;
    
    public Board board;
    public Board startingBoard;

    private ArrayList<Piece> graveyard;
    private ArrayList<Move> moves;
    
    public ArrayList<Boolean> fogmap;
    
    public Color turn;
    public PlayerType whitePlayer;
    public PlayerType blackPlayer;

    public boolean isGameOver;
    public boolean isWhiteWinner;

    public Game() {
        this.board = new Board(false);
        this.graveyard = new ArrayList<Piece>();
        this.moves = new ArrayList<Move>();
        this.turn = Color.WHITE;
    }

    public String getId() {
        return id.toHexString();
    }

    public Move[] getMoves() {
        return this.moves.toArray(new Move[0]);
    } 

    public void setMoves(Move []moves) {
        this.moves = new ArrayList<Move>(Arrays.asList(moves));
    } 

    public void addMove(Move m) {
        this.moves.add(m);
    }

    public Piece[] getGraveyard() {
        return this.graveyard.toArray(new Piece[0]);
    } 

    public void getGraveyard(Piece []graveyard) {
        this.graveyard = new ArrayList<Piece>(Arrays.asList(graveyard));
    }

    public void addGraveyard(Piece p) {
        this.graveyard.add(p);
    } 
}
