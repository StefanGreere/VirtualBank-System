package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.accounts.Account;
import org.poo.fileio.CommandInput;
import org.poo.rates.ExchangeRateManager;
import org.poo.transactions.SendMoneyTransaction;
import org.poo.transactions.Transaction;
import org.poo.users.BankSingleton;
import org.poo.users.User;

public class SendMoneyCommand extends AbstractCommand {
    private String account;
    private String receiver;
    private double amount;
    private int timestamp;
    private String email;
    private String description;

    public SendMoneyCommand(ArrayNode output, CommandInput input) {
        super(output);
        this.account = input.getAccount();
        this.receiver = input.getReceiver();
        this.amount = input.getAmount();
        this.timestamp = input.getTimestamp();
        this.email = input.getEmail();
        this.description = input.getDescription();
    }

    @Override
    public void execute() {
        BankSingleton bank = BankSingleton.getInstance();

        User payer = bank.getUsers().get(email);
        Account accountFromPay = payer.findAccountByIban(account);
        Account accountToPay = bank.findAccountUserByIban(receiver);

        if (accountFromPay != null && accountToPay != null) {
            ExchangeRateManager exchangeRates = ExchangeRateManager.getInstance();

            // get the corrent rate to convert
            double rate = exchangeRates.getRate(accountFromPay.getCurrency(), accountToPay.getCurrency());

            double convertAmount = rate * amount;
            // if there is enough money stored in account
            if (Double.compare(accountFromPay.getBalance(), amount) >= 0) {
                accountFromPay.setBalance(accountFromPay.getBalance() - amount);
                accountToPay.setBalance(accountToPay.getBalance() + convertAmount);

                // create and add the transaction
                StringBuilder builder = new StringBuilder();
                builder.append(amount).append(" " + accountFromPay.getCurrency());
                String result = builder.toString();
                Transaction transaction = new SendMoneyTransaction(timestamp,
                        description, receiver, account, result, "sent");
                payer.getTransactions().add(transaction);
            } else {
                // error transaction
            }
        } else {
            // error
        }
    }
}
