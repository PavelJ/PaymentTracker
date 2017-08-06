package com.jirapave.cli.common.readers;

import com.jirapave.cli.common.exceptions.ValidatorException;
import com.jirapave.cli.persistence.model.Payment;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Class is responsible for user input reading and parsing the input into Payments
 */
public interface PaymentReader {

    int CURRENCY_INDEX = 0;
    int AMOUNT_INDEX = 1;

    List<Payment> readPayments(String input) throws ValidatorException;

    /**
     * Reads payment from a given line - it is possible to add mroe than payment on the line when comma is used as a separator, examples:
     * USD 100
     * USD 200,BMK -300
     * @param input Line to be parsed
     * @return List of payments parsed from a line
     * @throws ValidatorException When invalid is detected
     */
    default public List<Payment> readPaymentFromLine(String input) throws ValidatorException {
        String[] inp = input.split(",");
        if (inp == null || inp.length == 0){
            throw new ValidatorException("Input is null or empty");
        }

        List<Payment> payments = new ArrayList<>();
        for (String payment : inp){
            payments.add(parsePayment(payment));
        }

        return payments;
    }

    /**
     * Parse one specific payment from String - validations are done during parsing. Valid input is:
     * USD 100
     * Currrnecy must be 3 uppercased characters folloed by space and than a valid amount.
     * @param input
     * @return Resulting payment
     * @throws ValidatorException When invalid input is detected
     */
    default public Payment parsePayment(String input) throws ValidatorException{
        if (input == null || input.isEmpty()){
            throw new ValidatorException("Input is null or empty");
        }

        String[] inp = input.split("\\s");
        if (inp.length != 2){
            throw new ValidatorException("Payment has wrong format - must be 'CURRENCY AMOUNT', input was: " + input);
        }

        if (inp[CURRENCY_INDEX].length() != 3 || !StringUtils.isAllUpperCase(inp[CURRENCY_INDEX])){
            throw new ValidatorException("Currency has wrong format, must be 3 characters long, all upper case, input was: " + inp[CURRENCY_INDEX]);
        }

        Payment payment = new Payment();
        payment.setCurrency(inp[CURRENCY_INDEX]);

        try {
            BigDecimal amount = new BigDecimal(inp[AMOUNT_INDEX]);
            payment.setAmount(amount);
        } catch (NumberFormatException ex){
            throw new ValidatorException("Amount has wrong format, input was: " + inp[AMOUNT_INDEX]);
        }

        return payment;
    }

}
