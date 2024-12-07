package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.fileio.CommandInput;

public class CommandFactory {
    public static Command createCommand (ArrayNode output, CommandInput input) {
        String command = input.getCommand();

        switch (command) {
            case "printUsers":
                return new PrintUserCommand(output, input);
            case "addAccount":
                return new AddAccountCommand(input);
            case "createCard":
                return new CreateCardCommand(input);
            case "createOneTimeCard":
                return new CreateCardCommand(input);
            case "addFunds":
                return new AddFundsCommand(input);
            case "deleteAccount":
                return new DeleteAccountCommand(output, input);
            case "deleteCard":
                return new DeleteCardCommand(input);
            case "setMinBalance":
                return new SetMinBalanceCommand(input);
            case "payOnline":
                return new PayOnlineCommand(output, input);
            case "sendMoney":
                return new SendMoneyCommand(output, input); // aici nu stiu daca o sa trebuiasca out
            case "setAlias":
                return new SetAliasCommand(input);
            default:
                return null;
        }
    }
}
