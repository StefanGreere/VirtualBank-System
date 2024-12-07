package org.poo.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

    public DeleteCardCommand(CommandInput input) {
        super();
        this.cardNumber = input.getCardNumber();
        this.email = input.getEmail();
        this.timestamp = input.getTimestamp();
    }

    @Override
    public void execute() {
        BankSingleton bank = BankSingleton.getInstance();

        User accountOwner = bank.getUsers().get(email);

        for (Account account : accountOwner.getAccounts()) {
            int index = account.findIndexCardByNumber(cardNumber);
            if (index >= 0) {
                account.getCards().remove(index);

                Transaction transaction = new DeleteCardTransaction(timestamp, account.getIban(), cardNumber, email);
                accountOwner.getTransactions().add(transaction);
            }
        }
    }
}
