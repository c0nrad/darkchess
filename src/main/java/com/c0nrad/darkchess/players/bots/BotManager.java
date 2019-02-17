package com.c0nrad.darkchess.players.bots;

import com.c0nrad.darkchess.exceptions.InvalidBotException;
import com.c0nrad.darkchess.models.Board;
import com.c0nrad.darkchess.models.Color;
import com.c0nrad.darkchess.models.Move;
import com.c0nrad.darkchess.models.PlayerType;
import com.c0nrad.darkchess.players.Player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BotManager {

    private static final Logger logger = LoggerFactory.getLogger(BotManager.class);

    public static PlayerType[] AvailableBots = {
        PlayerType.RANDBOT, 
        PlayerType.PAWNPUSHERBOT, 
        PlayerType.KILLBOT};
    
    public static Move MakeMove(PlayerType bot, Board b, Color c) throws InvalidBotException {

        if (!IsPlayable(bot)) {
            logger.warn("requesting a non-existant bot {}", bot);
            throw new InvalidBotException("bot type " + bot + " is not supported");
        }

        Player p = null;
        if (bot == PlayerType.RANDBOT) {
            p = new RandBot();
            p.color = c;
        }

        if (bot == PlayerType.PAWNPUSHERBOT) {
            p = new PawnPusherBot();
            p.color = c;
        }

        if (bot == PlayerType.KILLBOT) {
            p = new KillBot();
            p.color = c;
        }

        return p.GetMove(b);
    }

    public static boolean IsPlayable(PlayerType bot) {
        for (PlayerType availableBot : AvailableBots) {
            if (availableBot == bot) {
                return true;
            }
        }
        return false;
    }

}