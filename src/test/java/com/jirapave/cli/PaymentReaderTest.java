package com.jirapave.cli;

import com.jirapave.cli.common.exceptions.ValidatorException;
import com.jirapave.cli.common.readers.PaymentReader;
import com.jirapave.cli.common.readers.impl.ConsolePaymentInputReader;
import com.jirapave.cli.persistence.model.Payment;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class PaymentReaderTest {

    PaymentReader consoleReader;

    @Before
    public void initEach(){
        consoleReader = new ConsolePaymentInputReader();
    }

    @Test
    public void testInputs() throws ValidatorException {
        List<Payment> payment = consoleReader.readPayments("USD 100");
        Assert.assertTrue("Print should print all valid values ignoring zero amount",
                payment != null && payment.size() == 1
                        && payment.get(0).getCurrency().equals("USD")
                        && payment.get(0).getAmount().equals(new BigDecimal("100")));

        payment = consoleReader.readPayments("USD 100,RMB 200");
        Assert.assertTrue("Print should print all valid values ignoring zero amount",
                payment != null && payment.size() == 2
                        && payment.get(0).getCurrency().equals("USD")
                        && payment.get(0).getAmount().equals(new BigDecimal("100"))
                        && payment.get(1).getCurrency().equals("RMB")
                        && payment.get(1).getAmount().equals(new BigDecimal("200")));

        // Wrong inputs
        Assertions.assertThatThrownBy(() -> {
            consoleReader.readPayments("uSD 100");
        }).isInstanceOf(ValidatorException.class);

        Assertions.assertThatThrownBy(() -> {
            consoleReader.readPayments("USD  100");
        }).isInstanceOf(ValidatorException.class);

        Assertions.assertThatThrownBy(() -> {
            consoleReader.readPayments("uSD 100a");
        }).isInstanceOf(ValidatorException.class);

        Assertions.assertThatThrownBy(() -> {
            consoleReader.readPayments("uSD 2 100");
        }).isInstanceOf(ValidatorException.class);

        Assertions.assertThatThrownBy(() -> {
            consoleReader.readPayments("uSD a 100");
        }).isInstanceOf(ValidatorException.class);

        Assertions.assertThatThrownBy(() -> {
            consoleReader.readPayments("USD 100,RmB 300");
        }).isInstanceOf(ValidatorException.class);

        Assertions.assertThatThrownBy(() -> {
            consoleReader.readPayments("100 100");
        }).isInstanceOf(ValidatorException.class);
    }

}
