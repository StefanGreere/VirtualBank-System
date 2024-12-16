package org.poo.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SendMoneyTransaction extends Transaction {
    @JsonProperty("receiverIBAN")
    private String receiverIban;
    @JsonProperty("senderIBAN")
    private String senderIban;
    private String amount;
    private String transferType;

    public SendMoneyTransaction(final int timestamp, final String description,
                                final String receiverIban, final String senderIban,
                                final String amount, final String transferType) {
        super(timestamp, description);
        this.receiverIban = receiverIban;
        this.senderIban = senderIban;
        this.amount = amount;
        this.transferType = transferType;
    }
}
