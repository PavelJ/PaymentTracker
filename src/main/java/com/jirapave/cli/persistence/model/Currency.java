package com.jirapave.cli.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Currency VO
 */
@Data
@AllArgsConstructor
public class Currency {

    /**
     * Currency identificator - 3 upper cased characters
     */
    private String currency;

    /**
     * Amount for the given currency
     */
    private BigDecimal amount;

    /**
     * Last modification for logging purposes
     */
    private LocalDateTime lastModified;
}
