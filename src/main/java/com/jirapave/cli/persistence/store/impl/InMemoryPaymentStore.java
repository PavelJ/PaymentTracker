package com.jirapave.cli.persistence.store.impl;

import com.jirapave.cli.persistence.model.Currency;
import com.jirapave.cli.persistence.store.PaymentStore;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * In memory store of currency status, Map is used to represent
 */
@Slf4j
public class InMemoryPaymentStore implements PaymentStore {

    private Map<String, Currency> store;

    public InMemoryPaymentStore(){
        // we want to maintain insert order
        this.store = new LinkedHashMap<>();
    }

    @Override
    public Currency getCurrencyState(String currency){
        return this.store.get(currency);
    }

    @Override
    public List<Currency> getAllCurrencyState() {
        return new ArrayList<Currency>(store.values());
    }

    @Override
    public void setCurrencyState(String currency, BigDecimal amount) {
        Currency curr = new Currency(currency, amount, LocalDateTime.now());
        log.info("Updating currency state from: " + store.get(curr.getCurrency()) + ", to: " + curr);
        store.put(currency, curr);
    }

    @Override
    public void setCurrencyState(Currency currency) {
        store.put(currency.getCurrency(), currency);
    }
}
