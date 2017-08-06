package com.jirapave.cli.persistence.store;

import com.jirapave.cli.persistence.model.Currency;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentStore {

    Currency getCurrencyState(String currency);

    List<Currency> getAllCurrencyState();

    void setCurrencyState(String currency, BigDecimal amount);

    void setCurrencyState(Currency currency);

}
