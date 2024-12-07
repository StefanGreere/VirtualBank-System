package org.poo.commands;

import org.poo.accounts.Account;
import org.poo.fileio.CommandInput;
import org.poo.users.BankSingleton;
import org.poo.users.User;

public class SetAliasCommand extends AbstractCommand {
    private String email;
    private String account;
    private String alias;
    private int timestamp;

    public SetAliasCommand(CommandInput input) {
        super();
        this.email = input.getEmail();
        this.account = input.getAccount();
        this.alias = input.getAlias();
        this.timestamp = input.getTimestamp();
    }

    @Override
    public void execute() {
        BankSingleton bank = BankSingleton.getInstance();

        User user = bank.getUsers().get(email);
        Account acc = user.findAccountByIban(account);
        acc.setAlias(alias);
    }
}
