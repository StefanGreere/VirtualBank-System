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

    public SetAliasCommand(final CommandInput input) {
        super();
        this.email = input.getEmail();
        this.account = input.getAccount();
        this.alias = input.getAlias();
        this.timestamp = input.getTimestamp();
    }

    /**
     * Executes the command to set a new alias for the specified account
     * This method updates the alias of the account associated with the provided IBAN
     */
    @Override
    public void execute() {
        // get the instance of the bank with all the users
        BankSingleton bank = BankSingleton.getInstance();

        User user = bank.getUsers().get(email);
        Account acc = user.findAccountByIban(account);
        acc.setAlias(alias);
    }
}
