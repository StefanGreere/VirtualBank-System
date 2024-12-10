package org.poo.transactions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Commerciant implements Comparable<Commerciant> {
    private String commerciant;
    private double total;
    @JsonIgnore
    private int timestamp;

    public Commerciant(String commerciant, double total, int timestamp) {
        this.commerciant = commerciant;
        this.total = total;
        this.timestamp = timestamp;
    }

    @Override
    public int compareTo(Commerciant other) {
        return this.commerciant.compareTo(other.commerciant);
    }
}
