package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.accounts.Account;
import org.poo.fileio.CommandInput;
import org.poo.rates.ExchangeRateManager;
import org.poo.transactions.SplitPaymentsTransaction;
import org.poo.transactions.Transaction;
import org.poo.users.BankSingleton;
import org.poo.users.User;

import java.util.List;
import java.util.Locale;

public class SplitPaymentCommand extends AbstractCommand {
    private List<String> accounts;
    private double amount;
    private String currency;
    private int timestamp;

    public SplitPaymentCommand(ArrayNode output, CommandInput input) {
        super(output);
        this.accounts = input.getAccounts();
        this.amount = input.getAmount();
        this.currency = input.getCurrency();
        this.timestamp = input.getTimestamp();
    }

    @Override
    public void execute() {
        BankSingleton bank = BankSingleton.getInstance();
        ExchangeRateManager exchangeRates = ExchangeRateManager.getInstance();

        int number = accounts.size();
        double amountDivided = amount / number;

        for (String account : accounts) {
            Account acc = bank.findAccountUserByIban(account);

            // get the corrent rate to convert
            double rate = exchangeRates.getRate(currency, acc.getCurrency());
            double convertAmount = rate * amountDivided;

            if (Double.compare(acc.getBalance(), convertAmount) >= 0) {
                acc.setBalance(acc.getBalance() - convertAmount);

                // locale used for double number . instead of ,
                String result = String.format(Locale.US, "Split payment of %.2f %s", amount, currency);
                Transaction transaction = new SplitPaymentsTransaction(timestamp, result, amountDivided, currency, accounts);

                User user = bank.findUserByIban(account);
                user.getTransactions().add(transaction);

                acc.getTransactions().add(transaction);
            }
        }
    }
}
