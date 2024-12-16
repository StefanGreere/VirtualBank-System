package org.poo.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private static final int FROZEN_BALANCE = 30;

    public CheckCardStatusCommand(final ArrayNode output, final CommandInput input) {
        super(output);
        this.cardNumber = input.getCardNumber();
        this.timestamp = input.getTimestamp();
    }

    /**
     * Executes the checkCardStatus command
     * Get the card associated with the given card number from the bank singleton
     * If the card exists it checks the associated account's balance
     * If the balance is below the minimum balance plus 30, it sets the card status to 'frozen'
     * and creates a WarningTransaction
     * The transaction is then added to the user's and account's transaction lists
     * If the card is not found it prepares an error message
     */
    @Override
    public void execute() {
        // get the instance of the bank with all the users
        BankSingleton bank = BankSingleton.getInstance();

        Card card = bank.findCardByCardNumber(cardNumber);

        if (card != null) {
            // there is a card
            Account account = bank.findAccountByCardNumber(cardNumber);
            User user = bank.findUserByIban(account.getIban());

            // verify if the card must set on frozen state
            if (account.getBalance() <= account.getMinBalance() + FROZEN_BALANCE) {
                card.setStatus("frozen");

                // add a transaction
                Transaction transaction = new WarningTransaction(timestamp);
                user.getTransactions().add(transaction);

                account.getTransactions().add(transaction);
            }
        } else {
            // error for a card absence
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
