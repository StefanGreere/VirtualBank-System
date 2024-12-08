package org.poo.transactions;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class SplitPaymentsTransaction extends Transaction {
    private double amount;
    private String currency;
    private List<String> involvedAccounts;

    public SplitPaymentsTransaction (int timestamp, String description, double amount,
                                     String currency, List<String> involvedAccounts) {
        super(timestamp, description);
        this.amount = amount;
        this.currency = currency;
        this.involvedAccounts = involvedAccounts;
    }
}
