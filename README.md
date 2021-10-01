# Meter Read Processor Application

This is a spring boot application that processes simple NEM12 csv files that are in the DROPBOX folder. A scheduled service, which will run every minute, scans the DROPBOX folder and consumes each file in the folder. All the files that are successfully processed will then be moved to the PROCESSED folder. 

## HOW TO COMPILE

mvn clean install

## HOW TO RUN PRE-COMPILED APPLICATION

java -jar .\deploy\nem12reader-0.0.1.jar

## HOW TO RUN PRE-COMPILED TEST HARNESS

java -jar .\deploy\nem12readerTestHarness-0.0.1.jar .\Dropbox\SimpleNem12.csv
