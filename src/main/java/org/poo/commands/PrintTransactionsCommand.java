package org.poo.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CommandInput;
import org.poo.transactions.Transaction;
import org.poo.users.BankSingleton;
import org.poo.users.User;

public class PrintTransactionsCommand extends AbstractCommand {
    private String email;
    private int timestamp;

    public PrintTransactionsCommand(final ArrayNode output, final CommandInput input) {
        super(output);
        this.email = input.getEmail();
        this.timestamp = input.getTimestamp();
    }

    /**
     * Executes the command to print all transactions of a specific user
     */
    @Override
    public void execute() {
        // get the instance of the bank with all the users
        BankSingleton bank = BankSingleton.getInstance();

        User user = bank.getUsers().get(email);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode commandOutput = mapper.createObjectNode();
        commandOutput.put("command", "printTransactions");

        ArrayNode transactions = mapper.createArrayNode();
        for (Transaction transaction : user.getTransactions()) {
            ObjectNode node = mapper.valueToTree(transaction);
            transactions.add(node);
        }

        commandOutput.put("output", transactions);
        commandOutput.put("timestamp", timestamp);

        output.add(commandOutput);
    }
}
