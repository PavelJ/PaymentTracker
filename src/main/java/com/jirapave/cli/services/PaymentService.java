package com.jirapave.cli.services;

import com.jirapave.cli.persistence.model.Payment;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public interface PaymentService {

    Properties EXCHANGE_RATES = new Properties();

    /**
     * Adds a payment to he store
     * @param payment Payment to be added
     */
    void addPayment(Payment payment);

    /**
     * Adds all payments to the store
     * @param payments List of payments to be added
     */
    void addAllPayments(List<Payment> payments);

    /**
     * Prints the current state of the currencies. Currencies with amount=0 are not printed.
     * This method is scheduled to be run in interval of N seconds specified in {@link com.jirapave.cli.config.AppConfig#PRINT_SCHEDULER_SECONDS}
     */
    void printPaymentStatus();

    default BigDecimal getUSDExchangeRate(String currency){
        if (EXCHANGE_RATES != null && EXCHANGE_RATES.containsKey(currency)){
            return new BigDecimal(EXCHANGE_RATES.getProperty(currency));
        } else {
            return null;
        }
    }

}
