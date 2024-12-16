package org.poo.rates;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter
public final class ExchangeRateManager {
    private static ExchangeRateManager instance;
    private Map<String, Map<String, Double>> exchangeRates;

    private ExchangeRateManager() {
        this.exchangeRates = new HashMap<>();
    }

    /**
     * Returns the singleton instance of the ExchangeRateManager
     * If the instance does not already exist, it is created
     *
     * @return the singleton instance of ExchangeRateManager
     */
    public static ExchangeRateManager getInstance() {
        if (instance == null) {
            instance = new ExchangeRateManager();
        }
        return instance;
    }

    /**
     * Adds a new exchange rate to the exchangeRates hashmap
     *
     * @param from the currency code representing the 'from' currency
     * @param to the currency code representing the 'to' currency
     * @param rate the exchange rate
     */
    public void addRate(final String from, final String to, final double rate) {
        exchangeRates.putIfAbsent(from, new HashMap<>());
        exchangeRates.putIfAbsent(to, new HashMap<>());

        exchangeRates.get(from).put(to, rate);
        exchangeRates.get(to).put(from, 1.0 / rate); // reverse rate
    }

    /**
     * Calculates indirect exchange rates between all currency pairs.
     * This method iterates through all combinations of currencies stored in exchangeRates
     * The method also updates the exchangeRates hashmap
     */
    public void calculateAllRates() {
        for (String i : exchangeRates.keySet()) {
            for (String j : exchangeRates.keySet()) {
                for (String k : exchangeRates.keySet()) {
                    if (exchangeRates.get(i).containsKey(k)
                            && exchangeRates.get(k).containsKey(j)) {
                        double indirectRate =
                                exchangeRates.get(i).get(k) * exchangeRates.get(k).get(j);
                        exchangeRates.get(i).put(j, indirectRate);
                        exchangeRates.get(j).put(i, 1.0 / indirectRate);
                    }
                }
            }
        }
    }

    /**
     * Retrieves the exchange rate from the specified currency 'from' to the specified currency 'to'
     *
     * @param from the currency code representing the 'from' currency
     * @param to the currency code representing the 'to' currency
     * @return The exchange rate or null if no such rate exists
     */
    public Double getRate(final String from, final String to) {
        return exchangeRates.getOrDefault(from, new HashMap<>()).get(to);
    }

    /**
     * Resets the singleton instance
     */
    public static void resetInstance() {
        instance = null;
    }
}
