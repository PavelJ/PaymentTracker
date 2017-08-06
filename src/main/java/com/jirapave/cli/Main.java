package com.jirapave.cli;

import com.beust.jcommander.JCommander;
import com.jirapave.cli.common.exceptions.ValidatorException;
import com.jirapave.cli.common.readers.PaymentReader;
import com.jirapave.cli.common.readers.impl.ConsolePaymentInputReader;
import com.jirapave.cli.common.readers.impl.FilePaymentInputReader;
import com.jirapave.cli.config.AppConfig;
import com.jirapave.cli.config.CLIArguments;
import com.jirapave.cli.config.DependencyConfig;
import com.jirapave.cli.persistence.model.Payment;
import com.jirapave.cli.services.PaymentService;
import dagger.ObjectGraph;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Main {

    public static String COMMAND_QUIT = "quit";

    public static void main(String[] args) {
        // DI container initialization
        ObjectGraph container = ObjectGraph.create(new DependencyConfig());
        PaymentService paymentService = container.get(PaymentService.class);

        // Scheduling paymentService to print currency state each minute
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(paymentService::printPaymentStatus, AppConfig.PRINT_SCHEDULER_SECONDS, AppConfig.PRINT_SCHEDULER_SECONDS, TimeUnit.SECONDS);

        // Parse parameters - file input param
        CLIArguments cliArguments = new CLIArguments();
        JCommander.newBuilder().addObject(cliArguments).build().parse(args);

        if (cliArguments.isHelp()) {
            return;
        }

        // Initialize store from file
        if (cliArguments.getInitFile() != null){
            System.out.println("File input was specified, initializing store with data...");
            try {
                PaymentReader reader = new FilePaymentInputReader();
                List<Payment> payments = reader.readPayments(cliArguments.getInitFile());
                paymentService.addAllPayments(payments);
                System.out.println("Store initialized");
            } catch (ValidatorException ex){
                log.error(ex.getMessage(), ex);
                System.out.println("Error during file reading: " + ex.getMessage());
            }
        }

        // Main console reading loop
        Scanner scanner = new Scanner(System.in);
        PaymentReader reader = new ConsolePaymentInputReader();
        try {
            while (true) {
                System.out.println("Please entry new payment:");
                String line = scanner.nextLine();

                if (COMMAND_QUIT.equals(line)){
                    System.exit(0);
                } else {
                    // Reading payments from input
                    try {
                        List<Payment> payments = reader.readPayments(line);
                        paymentService.addAllPayments(payments);
                    } catch (ValidatorException ex) {
                        log.error(ex.getMessage(), ex);
                        System.out.println("Wrong input: " + ex.getMessage());
                    }
                }
            }
        } catch(IllegalStateException | NoSuchElementException e) {
            // System.in has been closed
            System.out.println("System.in was closed; exiting");
        }
    }

}
