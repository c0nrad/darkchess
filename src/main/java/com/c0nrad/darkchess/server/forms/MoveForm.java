package com.c0nrad.darkchess.server.forms;

import com.c0nrad.darkchess.exceptions.FormValidationException;
import com.c0nrad.darkchess.models.Move;
import com.c0nrad.darkchess.models.Position;

public class MoveForm {
    public Position from;
    public Position to;

    public void Validate() throws FormValidationException {
        // validation in psoition constructor
    }

    public Move toMove() throws FormValidationException {
        Move m = new Move();
        m.from = from;
        m.to = to;
        return m;
    }
}

