package org.poo.commands;

import org.poo.accounts.Account;
import org.poo.fileio.CommandInput;
import org.poo.users.BankSingleton;

public class AddFundsCommand extends AbstractCommand {
    private String account;
    private double amount;
    private int timestamp;

    public AddFundsCommand(final CommandInput input) {
        super();
        this.account = input.getAccount();
        this.amount = input.getAmount();
        this.timestamp = input.getTimestamp();
    }

    /**
     * Executes the command to add funds to a specified bank account
     * Get the account associated with the given IBAN from the bank singleton
     * If the account exists, it adds the specified amount to its balance
     */
    @Override
    public void execute() {
        // get the instance of the bank with all the users
        BankSingleton bank = BankSingleton.getInstance();

        Account acc = bank.findAccountUserByIban(account);

        if (acc != null) {
            acc.setBalance(acc.getBalance() + amount);
        }
    }
}
