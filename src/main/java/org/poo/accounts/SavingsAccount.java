package org.poo.accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SavingsAccount extends Account {
    @JsonIgnore
    private double interestRate;

    public SavingsAccount(final String currency, final String type, final double interestRate) {
        super(currency, type);
        this.interestRate = interestRate;
    }
}
