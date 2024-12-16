package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;

public abstract class AbstractCommand implements Command {
    protected ArrayNode output; // the array node where it will be the results of the commands

    public AbstractCommand(final ArrayNode output) {
        this.output = output;
    }

    public AbstractCommand() {
    }
}
