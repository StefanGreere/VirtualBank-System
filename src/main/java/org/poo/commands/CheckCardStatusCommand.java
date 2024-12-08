package org.poo.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.accounts.Account;
import org.poo.cards.Card;
import org.poo.fileio.CommandInput;
import org.poo.transactions.Transaction;
import org.poo.transactions.WarningTransaction;
import org.poo.users.BankSingleton;
import org.poo.users.User;

public class CheckCardStatusCommand extends AbstractCommand {
    private String cardNumber;
    private int timestamp;

    public CheckCardStatusCommand(ArrayNode output, CommandInput input) {
        super(output);
        this.cardNumber = input.getCardNumber();
        this.timestamp = input.getTimestamp();
    }

    @Override
    public void execute() {
        BankSingleton bank = BankSingleton.getInstance();

        Card card = bank.findCardByCardNumber(cardNumber);

        if (card != null) {
            // there is a card
            Account account = bank.findAccountByCardNumber(cardNumber);
            User user = bank.findUserByIban(account.getIban());
            if (account.getBalance() <= account.getMinBalance() + 30) {
                card.setStatus("frozen");
                Transaction transaction = new WarningTransaction(timestamp);
                user.getTransactions().add(transaction);
            }
        } else {
            ObjectMapper mapper = new ObjectMapper();

            ObjectNode commandOutput = mapper.createObjectNode();
            commandOutput.put("command", "checkCardStatus");

            ObjectNode node = mapper.createObjectNode();
            node.put("description", "Card not found");
            node.put("timestamp", timestamp);

            commandOutput.put("output", node);
            commandOutput.put("timestamp", timestamp);
            output.add(commandOutput);
        }
    }
}
