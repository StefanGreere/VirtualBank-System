package org.poo.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.accounts.Account;
import org.poo.fileio.CommandInput;
import org.poo.transactions.Transaction;
import org.poo.users.BankSingleton;
import org.poo.users.User;

public class ReportCommand extends AbstractCommand {
    private int startTimestamp;
    private int endTimestamp;
    private String account;
    private int timestamp;

    public ReportCommand(final ArrayNode output, final CommandInput input) {
        super(output);
        this.startTimestamp = input.getStartTimestamp();
        this.endTimestamp = input.getEndTimestamp();
        this.account = input.getAccount();
        this.timestamp = input.getTimestamp();
    }

    /**
     * Executes the command to generate a report for a specific account from an interval
     */
    @Override
    public void execute() {
        // get the instance of the bank with all the users
        BankSingleton bank = BankSingleton.getInstance();

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode commandOutput = mapper.createObjectNode();
        commandOutput.put("command", "report");

        // get the user and if there is not an owner of the account, will ba an error
        User user = bank.findUserByIban(account);
        if (user == null) {
            ObjectNode node = mapper.createObjectNode();
            node.put("description", "Account not found");
            node.put("timestamp", timestamp);
            commandOutput.put("output", node);
            commandOutput.put("timestamp", timestamp);
            output.add(commandOutput);
            return;
        }
        Account acc = user.findAccountByIban(account);

        // create the output
        ObjectNode node = mapper.createObjectNode();
        node.put("IBAN", account);
        node.put("balance", acc.getBalance());
        node.put("currency", acc.getCurrency());

        ArrayNode transactions = mapper.createArrayNode();

        for (Transaction transaction : acc.getTransactions()) {
            if (transaction.getTimestamp() >= startTimestamp
                    && transaction.getTimestamp() <= endTimestamp) {
                ObjectNode report = mapper.valueToTree(transaction);
                transactions.add(report);
            }
        }

        node.put("transactions", transactions);
        commandOutput.put("output", node);
        commandOutput.put("timestamp", timestamp);
        output.add(commandOutput);
    }
}
