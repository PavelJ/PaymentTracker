package com.jirapave.cli;

import com.jirapave.cli.common.exceptions.ValidatorException;
import com.jirapave.cli.persistence.model.Payment;
import com.jirapave.cli.persistence.store.PaymentStore;
import com.jirapave.cli.persistence.store.impl.InMemoryPaymentStore;
import com.jirapave.cli.services.PaymentService;
import com.jirapave.cli.services.impl.BasicPaymentService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;

public class BasicPaymentServiceTest {

    PaymentStore store;
    PaymentService paymentService;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void initEach(){
        store = new InMemoryPaymentStore();
        paymentService = new BasicPaymentService(store);
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }

    @Test
    public void testAddPayment(){
        paymentService.addPayment(new Payment("USD", new BigDecimal("1000")));
        paymentService.addPayment(new Payment("RMB", new BigDecimal("500.55")));
        paymentService.addPayment(new Payment("USD", new BigDecimal("-499.20")));
        paymentService.addPayment(new Payment("USD", new BigDecimal("10000000")));
        Assert.assertTrue("Substracting and Plus operations should work as expected", store.getCurrencyState("USD").getAmount().equals(new BigDecimal("10000500.80")));
        paymentService.addPayment(new Payment("USD", new BigDecimal("-10000000")));
        paymentService.addPayment(new Payment("USD", new BigDecimal("-5000")));
        Assert.assertTrue("Handling below zero amount", store.getCurrencyState("USD").getAmount().equals(new BigDecimal("-4499.20")));
    }

    @Test
    public void testPrintPaymentStatus(){
        // Initialize data
        paymentService.addPayment(new Payment("USD", new BigDecimal("500")));
        paymentService.addPayment(new Payment("RMB", new BigDecimal("-200.20")));
        paymentService.addPayment(new Payment("HKD", new BigDecimal("1000")));
        paymentService.addPayment(new Payment("HKD", new BigDecimal("-1000")));
        paymentService.printPaymentStatus();
        String expectedResult = "USD 500\r\nRMB -200.2 (USD -4044.04)\r\n";
        Assert.assertTrue("Print should print all valid values ignoring zero amount", expectedResult.equals(outContent.toString()));
    }

}
