package org.poo.commands;

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

    public AddAccountCommand(final CommandInput input) {
        super();
        this.email = input.getEmail();
        this.currency = input.getCurrency();
        this.accountType = input.getAccountType();
        this.timestamp = input.getTimestamp();
        if (accountType.equals("savings")) {
            this.interestRate = input.getInterestRate();
        }
    }

    /**
     * Executes the operation to add a new account for the user and creates the transaction:
     * find the user by email, adds a new account with the specified details,
     * creates an AddAccountTransaction, and logs it to the user's transaction list and
     * the last account's transaction list
     */
    @Override
    public void execute() {
        // get the instance of the bank with all the users
        BankSingleton bank = BankSingleton.getInstance();

        User user = bank.getUsers().get(email);
        user.addAccount(accountType, currency, interestRate);

        Transaction transaction = new AddAccountTransaction(timestamp);
        user.getTransactions().add(transaction);

        user.getAccounts().getLast().getTransactions().add(transaction);
    }
}
