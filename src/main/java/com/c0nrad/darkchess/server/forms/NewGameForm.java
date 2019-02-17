package com.c0nrad.darkchess.server.forms;

import com.c0nrad.darkchess.exceptions.FormValidationException;
import com.c0nrad.darkchess.models.PlayerType;

public class NewGameForm {
    public PlayerType white;
    public PlayerType black;

    public void Validate() throws FormValidationException {
        if (white == null ) {
            throw new FormValidationException("white player can't be null");
        }

        if (black == null ) {
            throw new FormValidationException("black player can't be null");
        }
        
        if (white.equals(black)) {
            throw new FormValidationException("both players can't be the same type" + white);
        }

        if (white != PlayerType.HUMAN && black != PlayerType.HUMAN) {
            throw new FormValidationException("there must be a human player");
        }
    }

}

