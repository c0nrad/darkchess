package com.c0nrad.darkchess.players;

import com.c0nrad.darkchess.models.Board;
import com.c0nrad.darkchess.models.Color;
import com.c0nrad.darkchess.models.Move;
import com.c0nrad.darkchess.models.PlayerType;

public abstract class Player {
    public Color color;
    public PlayerType playerType;

    abstract public void SendError(String error);
    abstract public Move GetMove(Board b);
}