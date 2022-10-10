package com.c0nrad.darkchess.models;

import org.bson.types.ObjectId;

public class HistoryView {
    public ObjectId gameId;
    
    public int turnCount;
    public int maxTurnCount;



    public Board board;
    public Board fogBoardWhite;
    public Board fogBoardBlack;

    public PlayerType playerWhite;
    public PlayerType playerBlack;

    public Piece[] graveyard;
    public Move[] moves;

    public String getGameId() {
        return gameId.toHexString();
    }
}
