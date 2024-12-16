package org.poo.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CommandInput;
import org.poo.users.BankSingleton;
import org.poo.users.User;

public class PrintUserCommand extends AbstractCommand {
    private int timestamp;

    public PrintUserCommand(final ArrayNode output, final CommandInput input) {
        super(output);
        this.timestamp = input.getTimestamp();
    }

    /**
     * Executes the command to print all users from the bank
     */
    @Override
    public void execute() {
        // get the instance of the bank with all the users
        BankSingleton bank = BankSingleton.getInstance();

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode commandOutput = mapper.createObjectNode();
        commandOutput.put("command", "printUsers");

        ArrayNode users = mapper.createArrayNode();
        for (User user : bank.getUsers().values()) {
            ObjectNode node = mapper.valueToTree(user);
            users.add(node);
        }
        commandOutput.put("output", users);
        commandOutput.put("timestamp", timestamp);
        output.add(commandOutput);
    }
}
