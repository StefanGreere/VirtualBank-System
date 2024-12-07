package org.poo.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.accounts.Account;
import org.poo.fileio.CommandInput;
import org.poo.users.BankSingleton;
import org.poo.users.User;

public class PrintUserCommand extends AbstractCommand {
    int timestamp;

    public PrintUserCommand(ArrayNode output, CommandInput input) {
        super(output);
        this.timestamp = input.getTimestamp();
    }

    @Override
    public void execute() {
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
