package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.fileio.CommandInput;

public final class CommandFactory {
    /**
     * Creates a specific Command object based on the command type specified in the input
     *
     * @param output the ArrayNode to which the command output will be added
     * @param input the CommandInput containing the command type and associated parameters
     * @return a new instance of the appropriate Command implementation or null
     */
    public static Command createCommand(final ArrayNode output, final CommandInput input) {
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
                return new SendMoneyCommand(input);
            case "setAlias":
                return new SetAliasCommand(input);
            case "printTransactions":
                return new PrintTransactionsCommand(output, input);
            case "checkCardStatus":
                return new CheckCardStatusCommand(output, input);
            case "changeInterestRate":
                return new ChangeInterestRateCommand(output, input);
            case "addInterest":
                return new ChangeInterestRateCommand(output, input);
            case "splitPayment":
                return new SplitPaymentCommand(output, input);
            case "report":
                return new ReportCommand(output, input);
            case "spendingsReport":
                return new SpendingsReportCommand(output, input);
            default:
                return null;
        }
    }

    private CommandFactory() {
    }
}
