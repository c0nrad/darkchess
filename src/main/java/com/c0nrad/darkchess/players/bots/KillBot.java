package com.c0nrad.darkchess.players.bots;

import com.c0nrad.darkchess.models.Board;
import com.c0nrad.darkchess.models.Move;
import com.c0nrad.darkchess.models.PlayerType;
import com.c0nrad.darkchess.players.Player;

public class KillBot extends Player {

    public KillBot() {
        this.playerType= PlayerType.KILLBOT;
    }

    public void SendError(String error) {
        System.err.println(error);
    }
    public Move GetMove(Board board) {
        return BotUtil.getBestMovebyKillPoints(board, this.color);
    }
}