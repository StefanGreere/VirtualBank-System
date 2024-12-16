package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.accounts.Account;
import org.poo.fileio.CommandInput;
import org.poo.rates.ExchangeRateManager;
import org.poo.transactions.SplitPaymentsErrorTransaction;
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

    public SplitPaymentCommand(final ArrayNode output, final CommandInput input) {
        super(output);
        this.accounts = input.getAccounts();
        this.amount = input.getAmount();
        this.currency = input.getCurrency();
        this.timestamp = input.getTimestamp();
    }

    /**
     * Executes the split payment operation across multiple accounts
     * This method divides the specified payment amount equally among all provided accounts
     * It checks each account's balance to verify if there is enough money available
     * If any account fails due to insufficient funds an error transaction is generated
     * Otherwise, the payment will be done for all accounts with a specific transaction
     */
    @Override
    public void execute() {
        // get the instance of the bank with all the users
        BankSingleton bank = BankSingleton.getInstance();
        // get the exchange rates instance
        ExchangeRateManager exchangeRates = ExchangeRateManager.getInstance();

        int number = accounts.size();
        double amountDivided = amount / number;

        boolean haveMoney = true; // verify if the accounts have enough money
        String accountFail = null; // account that has not enough money
        for (String account : accounts) {
            Account acc = bank.findAccountUserByIban(account);

            // get the corrent rate to convert
            double rate = exchangeRates.getRate(currency, acc.getCurrency());
            double convertAmount = rate * amountDivided;

            if (Double.compare(acc.getBalance(), convertAmount) < 0) {
                accountFail = account;
                haveMoney = false;
            }
        }

        // if there is not enough money, generate the specific transaction
        if (!haveMoney) {
            // locale used for double number . instead of ,
            String result = String.format(Locale.US, "Split payment of %.2f %s", amount, currency);
            Transaction transaction = new SplitPaymentsErrorTransaction(timestamp, result,
                    amountDivided, currency, accounts, accountFail);

            for (String account : accounts) {
                Account acc = bank.findAccountUserByIban(account);

                User user = bank.findUserByIban(account);
                user.getTransactions().add(transaction);

                acc.getTransactions().add(transaction);
            }
            return;
        }

        // otherwise, split the payment to all the accounts involved
        for (String account : accounts) {
            Account acc = bank.findAccountUserByIban(account);

            // get the corrent rate to convert
            double rate = exchangeRates.getRate(currency, acc.getCurrency());
            double convertAmount = rate * amountDivided;

            if (Double.compare(acc.getBalance(), convertAmount) >= 0) {
                acc.setBalance(acc.getBalance() - convertAmount);

                // locale used for double number . instead of ,
                String result = String.format(Locale.US,
                        "Split payment of %.2f %s", amount, currency);
                Transaction transaction = new SplitPaymentsTransaction(timestamp,
                                        result, amountDivided, currency, accounts);

                User user = bank.findUserByIban(account);
                user.getTransactions().add(transaction);

                acc.getTransactions().add(transaction);
            }
        }
    }
}
