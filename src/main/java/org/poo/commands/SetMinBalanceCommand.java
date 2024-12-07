package org.poo.commands;

import org.poo.accounts.Account;
import org.poo.fileio.CommandInput;
import org.poo.users.BankSingleton;
import org.poo.users.User;

public class SetMinBalanceCommand extends AbstractCommand {
    private double amount;
    private String account;
    private int timestamp;

    public SetMinBalanceCommand(CommandInput input) {
        super();
        this.amount = input.getAmount();
        this.account = input.getAccount();
        this.timestamp = input.getTimestamp();
    }

    @Override
    public void execute() {
        BankSingleton bank = BankSingleton.getInstance();

        Account acc = bank.findAccountUserByIban(account);

        if (acc != null) {
            acc.setMinBalance(amount);
        } else {
            // eroare ca nu exista un cont asociat
        }
    }
}
