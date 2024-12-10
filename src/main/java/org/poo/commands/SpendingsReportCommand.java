package org.poo.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.accounts.Account;
import org.poo.fileio.CommandInput;
import org.poo.transactions.Commerciant;
import org.poo.transactions.Transaction;
import org.poo.users.BankSingleton;
import org.poo.users.User;

import java.util.Collections;
import java.util.Comparator;

public class SpendingsReportCommand extends AbstractCommand {
    private int startTimestamp;
    private int endTimestamp;
    private String account;
    private int timestamp;

    public SpendingsReportCommand(ArrayNode output, CommandInput input) {
        super(output);
        this.startTimestamp = input.getStartTimestamp();
        this.endTimestamp = input.getEndTimestamp();
        this.account = input.getAccount();
        this.timestamp = input.getTimestamp();
    }

    @Override
    public void execute() {
        BankSingleton bank = BankSingleton.getInstance();

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode commandOutput = mapper.createObjectNode();
        commandOutput.put("command", "spendingsReport");

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
        Account acc = bank.findAccountUserByIban(account);
        if (acc.getType().equals("savings")) {
            ObjectNode node = mapper.createObjectNode();
            node.put("error", "This kind of report is not supported for a saving account");
            commandOutput.put("output", node);
            commandOutput.put("timestamp", timestamp);
            output.add(commandOutput);
            return;
        }

        ObjectNode node = mapper.createObjectNode();
        node.put("IBAN", account);
        node.put("balance", acc.getBalance());
        node.put("currency", acc.getCurrency());

        ArrayNode transactions = mapper.createArrayNode();

        for (Transaction transaction : acc.getTransactions()) {
            if (transaction.getTimestamp() >= startTimestamp && transaction.getTimestamp() <= endTimestamp) {
                if (transaction.getDescription().equals("Card payment")) {
                    ObjectNode report = mapper.valueToTree(transaction);
                    transactions.add(report);
                }
            }
        }

        node.put("transactions", transactions);

        // sort alphabetically by commerciant name
        Collections.sort(acc.getCommerciants());

        ArrayNode spendings = mapper.createArrayNode();
        for (Commerciant commerciant : acc.getCommerciants()) {
            if (commerciant.getTimestamp() >= startTimestamp && commerciant.getTimestamp() <= endTimestamp) {
                ObjectNode report = mapper.valueToTree(commerciant);
                spendings.add(report);
            }
        }

        node.put("commerciants", spendings);
        commandOutput.put("output", node);

        commandOutput.put("timestamp", timestamp);
        output.add(commandOutput);
    }
}
