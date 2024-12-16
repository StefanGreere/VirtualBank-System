package org.poo.commands;

import org.poo.accounts.Account;
import org.poo.fileio.CommandInput;
import org.poo.transactions.DeleteCardTransaction;
import org.poo.transactions.Transaction;
import org.poo.users.BankSingleton;
import org.poo.users.User;

public class DeleteCardCommand extends AbstractCommand {
    private String cardNumber;
    private String email;
    private int timestamp;

    public DeleteCardCommand(final CommandInput input) {
        super();
        this.cardNumber = input.getCardNumber();
        this.email = input.getEmail();
        this.timestamp = input.getTimestamp();
    }

    /**
     * Executes the command to delete a card from the user's accounts
     * Searches through all accounts of the user to find the card by its number
     * If found the card is removed and a transaction will be created
     */
    @Override
    public void execute() {
        // get the instance of the bank with all the users
        BankSingleton bank = BankSingleton.getInstance();

        User accountOwner = bank.getUsers().get(email);

        for (Account account : accountOwner.getAccounts()) {
            int index = account.findIndexCardByNumber(cardNumber);
            // if card exists
            if (index >= 0) {
                account.getCards().remove(index);

                // add the transaction
                Transaction transaction = new DeleteCardTransaction(timestamp,
                                        account.getIban(), cardNumber, email);
                accountOwner.getTransactions().add(transaction);

                account.getTransactions().add(transaction);

                // finish the command
                return;
            }
        }
    }
}
