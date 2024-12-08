package org.poo.commands;

import org.poo.accounts.Account;
import org.poo.fileio.CommandInput;
import org.poo.users.BankSingleton;
import org.poo.users.User;
import org.poo.utils.Utils;

public class AddFundsCommand extends AbstractCommand {
    private String account;
    private double amount;
    private int timestamp;

    public AddFundsCommand(CommandInput input) {
        super();
        this.account = input.getAccount();
        this.amount = input.getAmount();
        this.timestamp = input.getTimestamp();
    }

    @Override
    public void execute() {
        BankSingleton bank = BankSingleton.getInstance();

        Account acc = bank.findAccountUserByIban(account);

        if (acc != null) {
            acc.setBalance(acc.getBalance() + amount);
        }
    }
}
