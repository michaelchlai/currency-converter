# currency-converter
---
This project implements a currency converter application using Java.

- Programming Language: Java

## Components
---
### Main Program

The entry point of the program allows users to enter properties such as:
- amount
- from currency
- to currency
- input file path

This project uses the _commons-cli_ library to manage command line interface inputs.
A help message will be displayed when any of the properties are missing.

### Exchange rate extractor
A class was created to extract exchange rates from the user-provided XML file using the _javax.xml.parsers_ library.

### Exchange rate converter
Given the source and target currency provided by the user, converts the exchange rates.

### Principal calculator
A class to calculate the resulting principal given the computed currency rate and original amount.
