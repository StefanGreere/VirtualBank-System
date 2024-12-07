package org.poo.transactions;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateCardTransaction extends Transaction {
    private String card;
    private String cardHolder;
    private String account;

    public CreateCardTransaction(int timestamp, String card, String cardHolder, String account) {
        super(timestamp, "New card created");
        this.card = card;
        this.cardHolder = cardHolder;
        this.account = account;
    }
}
