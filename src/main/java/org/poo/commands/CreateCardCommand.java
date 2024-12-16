package org.poo.commands;

import org.poo.accounts.Account;
import org.poo.fileio.CommandInput;
import org.poo.transactions.CreateCardTransaction;
import org.poo.transactions.Transaction;
import org.poo.users.BankSingleton;
import org.poo.users.User;
import org.poo.utils.Utils;

public class CreateCardCommand extends AbstractCommand {
    private String command;
    private String account;
    private String email;
    private int timestamp;

    public CreateCardCommand(final CommandInput input) {
        super();
        this.command = input.getCommand();
        this.account = input.getAccount();
        this.email = input.getEmail();
        this.timestamp = input.getTimestamp();
    }

    /**
     * Executes the command to create a new card for the specified account
     * Generates a card number, adds the card to the user's account and creates the transaction
     */
    @Override
    public void execute() {
        // get the instance of the bank with all the users
        BankSingleton bank = BankSingleton.getInstance();

        // get the card by iban
        User user = bank.getUsers().get(email);
        if (user == null) {
            return;
        }

        // generate the card number
        String cardNumber = Utils.generateCardNumber();

        Account acc = user.findAccountByIban(account);
        if (acc != null) {
            acc.addCard(command, cardNumber);   // add the card in the list card

            // add the transaction
            Transaction transaction = new CreateCardTransaction(timestamp,
                                        cardNumber, email, acc.getIban());
            user.getTransactions().add(transaction);

            acc.getTransactions().add(transaction);
        }
    }
}
