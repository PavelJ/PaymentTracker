package com.jirapave.cli.common.readers.impl;

import com.jirapave.cli.common.exceptions.ValidatorException;
import com.jirapave.cli.common.readers.PaymentReader;
import com.jirapave.cli.persistence.model.Payment;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FilePaymentInputReader implements PaymentReader {

    @Override
    public List<Payment> readPayments(String input) throws ValidatorException {
        if (input == null){
            System.out.println("Input file path is null");
            return new ArrayList<>();
        }

        List<Payment> payments = new ArrayList<>();

        // We don;t want to use java 8 streams here because of exception handling - we are throwing custom exception so we cannot use lambda
        // without bothersome exception handling
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get(input), StandardCharsets.UTF_8);
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                payments.addAll(this.readPaymentFromLine(line));
            }
        } catch (IOException e) {
            log.error("Error reading file: " + e.getMessage(), e);
            System.out.println("Error reading input: " + input + ", " + e.getMessage());
        }

        return payments;
    }

}
