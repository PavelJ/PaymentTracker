# Payment Tracker

## Description
In memory tracker of a current state for a given currency based on an incoming payments. Each minute, current status of currencies is print to stdout. USD exchange rate is also printed if the given currency has exchange rate specified in exchange-rates.properties.

## Instalation
Run `mvn clean package` to produce `payment-tracker-1.0-SNAPSHOT-jar-with-dependencies.jar` file. This file behaves like classic CLI application

## Running
Resulting jar can be run as `java -jar payment-tracker-1.0-SNAPSHOT-jar-with-dependencies.jar`. Several options are available upon start:

* *--file -f*: Specify input file with payments, those payments will be sent into store upon start
* *--help -h*: Show all available commands

Tests can be run separately using `mvn test`

## Input format
Input can be done using CLI or using the init file. Format of a payment is as follows:
`^[A-Z]{3}\s[\-]{0,1}[0-9\.]+$` - for example USD 100.50
Multiple payments can be specified per line, e.g.
`USD 100,BMK -300`

## Exchange rates
You can specify exchange rates in exchange-rates.properties. When printing result, rate to USD is printed only for those currencies, which has the exchange rate specified in properties file.

## Possible improvements
* Better tests - scenarios could cover wider set of scenarios
* Currency was specified to be 3 upper-case characters - but since Currency is basically enumeration, codetable/lookuptable should be used with valid Currrencies
* Historization of payments - task was to focus on state of currency, but payments should sotred and historized as well
* Exchange rates could be specified using CLI param to be externalized
* Exchange rates could be changed in runtime
