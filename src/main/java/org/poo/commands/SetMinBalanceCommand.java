package org.poo.commands;

import org.poo.accounts.Account;
import org.poo.fileio.CommandInput;
import org.poo.users.BankSingleton;

public class SetMinBalanceCommand extends AbstractCommand {
    private double amount;
    private String account;
    private int timestamp;

    public SetMinBalanceCommand(final CommandInput input) {
        super();
        this.amount = input.getAmount();
        this.account = input.getAccount();
        this.timestamp = input.getTimestamp();
    }

    /**
     * Executes the command to set a new minimum balance for the specified account
     * This method updates the minimum balance of the account associated with the provided IBAN
     */
    @Override
    public void execute() {
        // get the instance of the bank with all the users
        BankSingleton bank = BankSingleton.getInstance();

        Account acc = bank.findAccountUserByIban(account);

        if (acc != null) {
            acc.setMinBalance(amount);
        }
    }
}
