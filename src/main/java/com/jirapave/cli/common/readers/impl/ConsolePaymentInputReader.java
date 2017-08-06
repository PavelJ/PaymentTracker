package com.jirapave.cli.common.readers.impl;

import com.jirapave.cli.common.exceptions.ValidatorException;
import com.jirapave.cli.common.readers.PaymentReader;
import com.jirapave.cli.persistence.model.Payment;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

public class ConsolePaymentInputReader implements PaymentReader {

    @Override
    public List<Payment> readPayments(String input) throws ValidatorException {
        return this.readPaymentFromLine(input);
    }

}
