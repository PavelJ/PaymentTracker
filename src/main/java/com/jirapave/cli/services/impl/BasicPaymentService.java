package com.jirapave.cli.services.impl;

import com.jirapave.cli.Main;
import com.jirapave.cli.config.AppConfig;
import com.jirapave.cli.persistence.model.Currency;
import com.jirapave.cli.persistence.model.Payment;
import com.jirapave.cli.persistence.store.PaymentStore;
import com.jirapave.cli.services.PaymentService;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
public class BasicPaymentService implements PaymentService{

    private PaymentStore store;

    @Inject
    public BasicPaymentService(PaymentStore store){
        this.store = store;

        try {
            EXCHANGE_RATES.load(Main.class.getClassLoader().getResourceAsStream("exchange-rates.properties"));
        }
        catch (IOException ex) {
            log.error("Error loading exchange rates.", ex);
        }
    }

    @Override
    public synchronized void addPayment(Payment payment) {
        Currency curr = store.getCurrencyState(payment.getCurrency());

        if (curr != null) {
            curr.setAmount(curr.getAmount().add(payment.getAmount()));
        } else {
            curr = new Currency(payment.getCurrency(), payment.getAmount(), LocalDateTime.now());
        }
        store.setCurrencyState(curr);
    }

    @Override
    public synchronized void addAllPayments(List<Payment> payments) {
        if (payments != null) {
            payments.forEach(payment -> {
                addPayment(payment);
            });
        }
    }

    @Override
    public synchronized void printPaymentStatus() {
        List<Currency> currencies = store.getAllCurrencyState();
        if (currencies != null && currencies.size() > 0) {
            currencies.forEach(curr -> {
                // zero amount currencies cannot be printed
                if (curr.getAmount() != null && curr.getAmount().compareTo(BigDecimal.ZERO) != 0) {
                    BigDecimal exchangeRate = this.getUSDExchangeRate(curr.getCurrency());
                    // StringBuilder could be used here for optimalization, but in this example clarity is more important
                    // BigDecimal.setScale could be susing when creating currency records, but i prefer to explicitly format output
                    if (exchangeRate != null){
                        System.out.println(curr.getCurrency() + " "
                                + AppConfig.DECIMAL_FORMAT.format(curr.getAmount())
                                + " (USD "
                                + AppConfig.DECIMAL_FORMAT.format(curr.getAmount().multiply(exchangeRate))
                                + ")");
                    } else {
                        System.out.println(curr.getCurrency() + " " + AppConfig.DECIMAL_FORMAT.format(curr.getAmount()));
                    }
                }
            });
        }
    }
}
