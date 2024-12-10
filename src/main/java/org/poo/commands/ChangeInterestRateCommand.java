package org.poo.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.accounts.Account;
import org.poo.accounts.SavingsAccount;
import org.poo.fileio.CommandInput;
import org.poo.transactions.InterestRateTransaction;
import org.poo.transactions.Transaction;
import org.poo.users.BankSingleton;
import org.poo.users.User;

public class ChangeInterestRateCommand extends AbstractCommand {
    private int timestamp;
    private String account;
    private double interestRate;
    private String command;

    public ChangeInterestRateCommand(ArrayNode output, CommandInput input) {
        super(output);
        this.timestamp = input.getTimestamp();
        this.account = input.getAccount();
        this.interestRate = input.getInterestRate();
        this.command = input.getCommand();
    }

    @Override
    public void execute() {
        BankSingleton bank = BankSingleton.getInstance();

        Account acc = bank.findAccountUserByIban(account);

        if (acc.getType().equals("savings")) {
            SavingsAccount savings = (SavingsAccount) acc;
            savings.setInterestRate(interestRate);

            User user = bank.findUserByIban(account);

            if (user != null) {
                Transaction transaction = new InterestRateTransaction(timestamp);
                user.getTransactions().add(transaction);

                acc.getTransactions().add(transaction);
            }
        } else {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode commandOutput = mapper.createObjectNode();
            commandOutput.put("command", command);
            ObjectNode node = mapper.createObjectNode();
            node.put("description", "This is not a savings account");
            node.put("timestamp", timestamp);
            commandOutput.put("output", node);
            commandOutput.put("timestamp", timestamp);
            output.add(commandOutput);
        }
    }
}
