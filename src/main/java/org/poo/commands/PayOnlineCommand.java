package org.poo.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.accounts.Account;
import org.poo.cards.Card;
import org.poo.fileio.CommandInput;
import org.poo.rates.ExchangeRateManager;
import org.poo.transactions.*;
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

    public PayOnlineCommand(ArrayNode output, CommandInput input) {
        super(output);
        this.cardNumber = input.getCardNumber();
        this.amount = input.getAmount();
        this.currency = input.getCurrency();
        this.timestamp = input.getTimestamp();
        this.description = input.getDescription();
        this.commerciant = input.getCommerciant();
        this.email = input.getEmail();
    }

    @Override
    public void execute() {
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

            ExchangeRateManager exchangeRates = ExchangeRateManager.getInstance();
            // get the corrent rate to convert
            double rate = exchangeRates.getRate(currency, account.getCurrency());

            double convertAmount = rate * amount;
            // if there is enough money stored in account
            if (Double.compare(account.getBalance(), convertAmount) >= 0) {
                account.setBalance(account.getBalance() - convertAmount);
                if (card.getCardType().equals("oneTimePay")) {
                    card.setCardNumber(Utils.generateCardNumber());
                }

                if (account.getBalance() < account.getMinBalance()) {
                    card.setStatus("frozen");
                } else {
                    Transaction transaction = new CardPaymentTransaction(timestamp, convertAmount, commerciant);
                    user.getTransactions().add(transaction);

                    account.getTransactions().add(transaction);

//                    Commerciant newCommerciant = account.findCommerciantByName(commerciant);
//                    if (newCommerciant != null) {
//                        newCommerciant.setTotal(newCommerciant.getTotal() + convertAmount);
//                    } else {
                    Commerciant newCommerciant = new Commerciant(commerciant, convertAmount, timestamp);
                    account.getCommerciants().add(newCommerciant);
//                    }
                }

            } else {
                // error transaction
                Transaction transaction = new InsufficientFundsTransaction(timestamp);
                user.getTransactions().add(transaction);

                account.getTransactions().add(transaction);
            }
        } else {
            // error
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
