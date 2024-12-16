package org.poo.commands;

import org.poo.accounts.Account;
import org.poo.fileio.CommandInput;
import org.poo.rates.ExchangeRateManager;
import org.poo.transactions.InsufficientFundsTransaction;
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

    public SendMoneyCommand(final CommandInput input) {
        super();
        this.account = input.getAccount();
        this.receiver = input.getReceiver();
        this.amount = input.getAmount();
        this.timestamp = input.getTimestamp();
        this.email = input.getEmail();
        this.description = input.getDescription();
    }

    /**
     * Executes the command to transfer funds from one account to another
     * The transfer includes converting the amount based on exchange rates
     * If the payer's account has sufficient balance the transfer will be ok,
     * and the transactions will be added
     * If the payer's account does not have sufficient funds an insufficient funds
     * a transaction will be added
     */
    @Override
    public void execute() {
        // get the instance of the bank with all the users
        BankSingleton bank = BankSingleton.getInstance();

        User payer = bank.getUsers().get(email);
        Account accountFromPay = payer.findAccountByIban(account);
        Account accountToPay = bank.findAccountUserByIban(receiver);

        if (accountFromPay != null && accountToPay != null) {
            // get the exchange rates instance
            ExchangeRateManager exchangeRates = ExchangeRateManager.getInstance();

            // get the correct rate to convert
            double rate = exchangeRates.getRate(accountFromPay.getCurrency(),
                                                accountToPay.getCurrency());

            // calculate the convert amount
            double convertAmount = rate * amount;
            // if there is enough money stored in account
            if (Double.compare(accountFromPay.getBalance(), amount) >= 0) {
                // set the new balances
                accountFromPay.setBalance(accountFromPay.getBalance() - amount);
                accountToPay.setBalance(accountToPay.getBalance() + convertAmount);

                // create and add the transaction
                StringBuilder builder = new StringBuilder();
                builder.append(amount).append(" " + accountFromPay.getCurrency());
                String result = builder.toString();
                Transaction transaction = new SendMoneyTransaction(timestamp,
                        description, receiver, account, result, "sent");
                payer.getTransactions().add(transaction);

                accountFromPay.getTransactions().add(transaction);

                // create and add the transaction
                StringBuilder helper = new StringBuilder();
                helper.append(convertAmount).append(" " + accountToPay.getCurrency());
                String newResult = helper.toString();
                User reveiverUser = bank.findUserByIban(receiver);
                Transaction receive = new SendMoneyTransaction(timestamp,
                        description, receiver, account, newResult, "received");
                reveiverUser.getTransactions().add(receive);

                accountToPay.getTransactions().add(receive);
            } else {
                // add a transaction if there is insufficient funds
                Transaction transaction = new InsufficientFundsTransaction(timestamp);
                payer.getTransactions().add(transaction);

                accountFromPay.getTransactions().add(transaction);
            }
        }
    }
}
