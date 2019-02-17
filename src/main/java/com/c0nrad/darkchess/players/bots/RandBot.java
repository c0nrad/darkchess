package com.c0nrad.darkchess.players.bots;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

import com.c0nrad.darkchess.engine.ChessEngine;
import com.c0nrad.darkchess.exceptions.InvalidStateException;
import com.c0nrad.darkchess.exceptions.InvalidPositionException;
import com.c0nrad.darkchess.models.Board;
import com.c0nrad.darkchess.models.Move;
import com.c0nrad.darkchess.models.PlayerType;
import com.c0nrad.darkchess.models.Position;
import com.c0nrad.darkchess.players.Player;

public class RandBot extends Player {

    Random rand;
    public RandBot() {
        rand = new SecureRandom();
        this.playerType = PlayerType.RANDBOT;
    }

    public void SendError(String error) {
        System.err.println(error);
    }
    public Move GetMove(Board board) {
        return BotUtil.getRandomMove(board, this.color);
    }
}