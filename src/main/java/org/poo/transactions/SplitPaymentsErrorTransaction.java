package org.poo.transactions;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class SplitPaymentsErrorTransaction extends SplitPaymentsTransaction {
    private String error;

    public SplitPaymentsErrorTransaction(final int timestamp, final String description,
                                         final double amount, final String currency,
                                         final List<String> involvedAccounts,
                                         final String account) {
        super(timestamp, description, amount, currency, involvedAccounts);
        this.error = "Account " + account + " has insufficient funds for a split payment.";
    }
}
