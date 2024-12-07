package org.poo.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.accounts.Account;
import org.poo.fileio.CommandInput;
import org.poo.users.BankSingleton;
import org.poo.users.User;

public class DeleteAccountCommand extends AbstractCommand {
    private String account;
    private int timestamp;
    private String email;

    public DeleteAccountCommand(ArrayNode output, CommandInput input) {
        super(output);
        this.account = input.getAccount();
        this.timestamp = input.getTimestamp();
        this.email = input.getEmail();
    }

    @Override
    public void execute() {
        BankSingleton bank = BankSingleton.getInstance();

        User accountOwner = bank.getUsers().get(email);

        Account acc = accountOwner.findAccountByIban(account);

        if (acc != null) {
            if (acc.getBalance() == 0) {
                // we can delete this account
                accountOwner.deleteAccount(account);

                ObjectMapper mapper = new ObjectMapper();
                ObjectNode commandOutput = mapper.createObjectNode();
                commandOutput.put("command", "deleteAccount");

                ObjectNode outputNode = mapper.createObjectNode();
                outputNode.put("success", "Account deleted");
                outputNode.put("timestamp", timestamp);

                commandOutput.put("output", outputNode);
                commandOutput.put("timestamp", timestamp);
                output.add(commandOutput);
            }
        }
    }
}
