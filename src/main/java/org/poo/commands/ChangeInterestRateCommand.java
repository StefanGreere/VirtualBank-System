package org.poo.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.accounts.Account;
import org.poo.accounts.SavingsAccount;
import org.poo.fileio.CommandInput;
import org.poo.transactions.ChangeRateTransaction;
import org.poo.transactions.InterestRateTransaction;
import org.poo.transactions.Transaction;
import org.poo.users.BankSingleton;
import org.poo.users.User;

public class ChangeInterestRateCommand extends AbstractCommand {
    private int timestamp;
    private String account;
    private double interestRate;
    private String command;

    public ChangeInterestRateCommand(final ArrayNode output, final CommandInput input) {
        super(output);
        this.timestamp = input.getTimestamp();
        this.account = input.getAccount();
        this.interestRate = input.getInterestRate();
        this.command = input.getCommand();
    }

    /**
     * Executes the command to change or add the interest rate of a savings account
     * Get the account associated with the given IBAN from the bank singleton
     * If the account is a savings account, it updates its interest rate
     * Depending on the command, it creates a corresponding transaction and adds it
     * to the user's and account's transaction lists
     * If the account is not a savings account, it prepares an error message in the output
     */
    @Override
    public void execute() {
        // get the instance of the bank with all the users
        BankSingleton bank = BankSingleton.getInstance();

        Account acc = bank.findAccountUserByIban(account);

        if (acc.getType().equals("savings")) {
            // set the interest rate
            SavingsAccount savings = (SavingsAccount) acc;
            savings.setInterestRate(interestRate);

            // get the account owner in order to add a transaction
            User user = bank.findUserByIban(account);

            if (user != null) {
                // add a transaction depending on the command type
                if (command.equals("addInterest")) {
                    Transaction transaction = new InterestRateTransaction(timestamp);
                    user.getTransactions().add(transaction);

                    acc.getTransactions().add(transaction);
                } else {
                    Transaction transaction = new ChangeRateTransaction(timestamp, interestRate);

                    user.getTransactions().add(transaction);
                    acc.getTransactions().add(transaction);
                }
            }
        } else {
            // the error for an incorrect type of account
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode commandOutput = mapper.createObjectNode();
            commandOutput.put("command", command);
            ObjectNode node = mapper.createObjectNode();
            node.put("description", "This is not a savings account");
            node.put("timestamp", timestamp);
            commandOutput.put("output", node);
            commandOutput.put("timestamp", timestamp);
            output.add(commandOutput);
        }
    }
}
