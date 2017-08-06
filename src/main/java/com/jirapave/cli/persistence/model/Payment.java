package com.jirapave.cli.persistence.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Payment VO representing change in currency status
 */
@Data
public class Payment {

    public Payment(){

    }

    public Payment(String currency, BigDecimal amount) {
        this.currency = currency;
        this.amount = amount;
    }

    /**
     * Currency identificator - 3 upper cased characters, corresponds with {@link Currency#currency}
     */
    private String currency;

    /**
     * Amount for the given currency, corresponds with {@link Currency#amount}
     */
    private BigDecimal amount;
}
