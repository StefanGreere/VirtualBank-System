package org.poo.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.accounts.Account;
import org.poo.cards.Card;
import org.poo.fileio.CommandInput;
import org.poo.rates.ExchangeRateManager;
import org.poo.transactions.CardPaymentTransaction;
import org.poo.transactions.Commerciant;
import org.poo.transactions.CreateCardTransaction;
import org.poo.transactions.DestroyCardTransaction;
import org.poo.transactions.FrozenCardTransaction;
import org.poo.transactions.InsufficientFundsTransaction;
import org.poo.transactions.Transaction;
import org.poo.users.BankSingleton;
import org.poo.users.User;
import org.poo.utils.Utils;

public class PayOnlineCommand extends AbstractCommand {
    private String cardNumber;
    private double amount;
    private String currency;
    private int timestamp;
    private String description;
    private String commerciant;
    private String email;

    public PayOnlineCommand(final ArrayNode output, final CommandInput input) {
        super(output);
        this.cardNumber = input.getCardNumber();
        this.amount = input.getAmount();
        this.currency = input.getCurrency();
        this.timestamp = input.getTimestamp();
        this.description = input.getDescription();
        this.commerciant = input.getCommerciant();
        this.email = input.getEmail();
    }

    /**
     * Executes the command for making an online payment
     * Searches for the account associated with the card number provided in the command
     * If the card is frozen, it is an error
     * Find the amount from the account and if there are sufficient funds
     * Generate appropriate transactions depending on the outcome
     */
    @Override
    public void execute() {
        // get the instance of the bank with all the users
        BankSingleton bank = BankSingleton.getInstance();

        User user = bank.getUsers().get(email);
        Account account = user.findAccountByCardNumber(cardNumber);

        if (account != null) {
            int index = account.findIndexCardByNumber(cardNumber);
            Card card = account.getCards().get(index);

            // if the card is frozen, the pay online can't work
            if (card.getStatus().equals("frozen")) {
                Transaction transaction = new FrozenCardTransaction(timestamp);
                user.getTransactions().add(transaction);

                account.getTransactions().add(transaction);
                return;
            }

            // get the exchange rates
            ExchangeRateManager exchangeRates = ExchangeRateManager.getInstance();
            // get the correct rate to convert
            double rate = exchangeRates.getRate(currency, account.getCurrency());

            // calculate the convert amount
            double convertAmount = rate * amount;
            // if there is enough money stored in account
            if (Double.compare(account.getBalance(), convertAmount) >= 0) {
                account.setBalance(account.getBalance() - convertAmount);

                if (account.getBalance() < account.getMinBalance()) {
                    card.setStatus("frozen");
                    return;
                } else {
                    // add the transaction and the commerciant
                    Transaction transaction = new CardPaymentTransaction(timestamp,
                                                    convertAmount, commerciant);
                    user.getTransactions().add(transaction);

                    account.getTransactions().add(transaction);

                    Commerciant newCommerciant = new Commerciant(commerciant,
                                                    convertAmount, timestamp);
                    account.getCommerciants().add(newCommerciant);
                }

                // if the payment was made with a one time pay card, change it
                if (card.getCardType().equals("oneTimePay")) {
                    Transaction transaction = new DestroyCardTransaction(timestamp,
                            account.getIban(), cardNumber, email);
                    user.getTransactions().add(transaction);
                    account.getTransactions().add(transaction);

                    // change the card
                    card.setCardNumber(Utils.generateCardNumber());

                    Transaction newCard = new CreateCardTransaction(timestamp,
                            card.getCardNumber(), email, account.getIban());
                    user.getTransactions().add(newCard);
                    account.getTransactions().add(newCard);
                }
            } else {
                // error transaction
                Transaction transaction = new InsufficientFundsTransaction(timestamp);
                user.getTransactions().add(transaction);

                account.getTransactions().add(transaction);
            }
        } else {
            // error for a card absence
            ObjectMapper mapper = new ObjectMapper();

            ObjectNode outputCommand = mapper.createObjectNode();
            outputCommand.put("command", "payOnline");

            ObjectNode error = mapper.createObjectNode();
            error.put("timestamp", timestamp);
            error.put("description", "Card not found");

            outputCommand.put("output", error);
            outputCommand.put("timestamp", timestamp);
            output.add(outputCommand);
        }
    }
}
