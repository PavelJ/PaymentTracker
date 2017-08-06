package com.jirapave.cli.config;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * App level configuration options are stored here
 */
public class AppConfig {

    /**
     * Number of seconds after which paymentService will print current currencies status
     */
    public static final int PRINT_SCHEDULER_SECONDS = 60;

    /**
     * Formatting of BigDecimal in stdout output - forcing dot as separator ignoring Locale
     */
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###.##");
    static {
        DecimalFormatSymbols sym = DecimalFormatSymbols.getInstance();
        sym.setDecimalSeparator('.');
        DECIMAL_FORMAT.setDecimalFormatSymbols(sym);
    }

}
