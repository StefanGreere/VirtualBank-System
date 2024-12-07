package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.accounts.Account;
import org.poo.cards.Card;
import org.poo.cards.CardFactory;
import org.poo.cards.CardFactorySelector;
import org.poo.fileio.CommandInput;
import org.poo.users.BankSingleton;
import org.poo.users.User;
import org.poo.utils.Utils;

public class CreateCardCommand extends AbstractCommand {
    private String command;
    private String account; // the iban
    private String email;
    private int timestamp;

    public CreateCardCommand(CommandInput input) {
        super();
        this.command = input.getCommand();
        this.account = input.getAccount();
        this.email = input.getEmail();
        this.timestamp = input.getTimestamp();
    }

    @Override
    public void execute() {
        BankSingleton bank = BankSingleton.getInstance();

        // generate the card number
        String cardNumber = Utils.generateCardNumber();

        // get the card by iban
        User user = bank.getUsers().get(email);
        if (user == null) {
            return;
        }

        Account acc = user.findAccountByIban(account);
        if (acc != null) {
            acc.addCard(command, cardNumber);   // add the card in the list card
        }
    }
}
