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

    public Commerciant(final String commerciant, final double total, final int timestamp) {
        this.commerciant = commerciant;
        this.total = total;
        this.timestamp = timestamp;
    }

    /**
     * Compares this commerciant with another based on their names
     *
     * @param other the other commerciant
     */
    @Override
    public int compareTo(final Commerciant other) {
        return this.commerciant.compareTo(other.commerciant);
    }
}
