package org.poo.transactions;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CardPaymentTransaction extends Transaction {
    private double amount;
    private String commerciant;

    public CardPaymentTransaction(int timestamp, double amount, String commerciant) {
        super(timestamp, "Card payment");
        this.amount = amount;
        this.commerciant = commerciant;
    }
}
