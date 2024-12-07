package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;

public abstract class AbstractCommand implements Command {
    protected ArrayNode output;

    public AbstractCommand (ArrayNode output) {
        this.output = output;
    }

    public AbstractCommand() {

    }
}
