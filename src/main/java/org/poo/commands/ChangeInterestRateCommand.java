package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.accounts.Account;
import org.poo.accounts.SavingsAccount;
import org.poo.fileio.CommandInput;
import org.poo.transactions.InterestRateTransaction;
import org.poo.transactions.Transaction;
import org.poo.users.BankSingleton;
import org.poo.users.User;

public class ChangeInterestRateCommand extends AbstractCommand {
    private int timestamp;
    private String account;
    private double interestRate;

    public ChangeInterestRateCommand(ArrayNode output, CommandInput input) {
        super(output);
        this.timestamp = input.getTimestamp();
        this.account = input.getAccount();
        this.interestRate = input.getInterestRate();
    }

    @Override
    public void execute() {
        BankSingleton bank = BankSingleton.getInstance();

        Account acc = bank.findAccountUserByIban(account);

        if (acc.getType().equals("savings")) {
            SavingsAccount savings = (SavingsAccount) acc;
            savings.setInterestRate(interestRate);

            User user = bank.findUserByIban(account);

            if (user != null) {
                Transaction transaction = new InterestRateTransaction(timestamp);
                user.getTransactions().add(transaction);

                acc.getTransactions().add(transaction);
            }
        }
    }
}
