package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.fileio.CommandInput;
import org.poo.transactions.AddAccountTransaction;
import org.poo.transactions.Transaction;
import org.poo.users.BankSingleton;
import org.poo.users.User;

public class AddAccountCommand extends AbstractCommand {
    private final String email;
    private String currency;
    private final String accountType;
    private int timestamp;
    private double interestRate;

    public AddAccountCommand(CommandInput input) {
        super();
        this.email = input.getEmail();
        this.currency = input.getCurrency();
        this.accountType = input.getAccountType();
        this.timestamp = input.getTimestamp();
        if (accountType.equals("savings")) {
            this.interestRate = input.getInterestRate();
        }
    }

    @Override
    public void execute() {
        BankSingleton bank = BankSingleton.getInstance();

        User user = bank.getUsers().get(email);
        user.addAccount(accountType, currency, interestRate);

        Transaction transaction = new AddAccountTransaction(timestamp);
        user.getTransactions().add(transaction);

        user.getAccounts().getLast().getTransactions().add(transaction);
    }
}
