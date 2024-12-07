package org.poo.rates;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter
public class ExchangeRateManager {
    private static ExchangeRateManager instance;
    private Map<String, Map<String, Double>> exchangeRates;

    private ExchangeRateManager() {
        this.exchangeRates = new HashMap<>();
    }

    public static ExchangeRateManager getInstance() {
        if (instance == null) {
            instance = new ExchangeRateManager();
        }
        return instance;
    }

    public void addRate(String from, String to, double rate) {
        exchangeRates.putIfAbsent(from, new HashMap<>());
        exchangeRates.putIfAbsent(to, new HashMap<>());

        exchangeRates.get(from).put(to, rate);
        exchangeRates.get(to).put(from, 1.0 / rate); // Reverse rate
    }

    public void calculateAllRates() {
        for (String k : exchangeRates.keySet()) {
            for (String i : exchangeRates.keySet()) {
                for (String j : exchangeRates.keySet()) {
                    if (exchangeRates.get(i).containsKey(k) && exchangeRates.get(k).containsKey(j)) {
                        double indirectRate = exchangeRates.get(i).get(k) * exchangeRates.get(k).get(j);
                        exchangeRates.get(i).put(j, indirectRate);
                        exchangeRates.get(j).put(i, 1.0 / indirectRate);
                    }
                }
            }
        }
    }

    public Double getRate(String from, String to) {
        return exchangeRates.getOrDefault(from, new HashMap<>()).get(to);
    }

    public static void resetInstance() {
        instance = null;
    }
}
