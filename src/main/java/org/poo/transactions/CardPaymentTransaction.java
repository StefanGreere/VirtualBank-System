package org.poo.transactions;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CardPaymentTransaction extends Transaction {
    private double amount;
    private String commerciant;

    public CardPaymentTransaction(final int timestamp,
                                  final double amount, final String commerciant) {
        super(timestamp, "Card payment");
        this.amount = amount;
        this.commerciant = commerciant;
    }
}
