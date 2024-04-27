package org.mchl;

import org.apache.commons.cli.*;

public class CurrencyConverter {
    public static void main(String[] args) {
        CommandLineInterface cli = new CommandLineInterface();
        cli.parse(args);

    }
}

class CommandLineInterface{
    String amount, inputFilePath, fromCurrency, toCurrency;
    Options options = new Options();
    HelpFormatter helpFormatter = new HelpFormatter();
    CommandLineParser parser = new DefaultParser();
    public CommandLineInterface(){
        //CLI (Step 1/3): Definition Stage
        options.addOption(Option.builder("a")
                .longOpt("amount")
                .hasArg(true)
                .desc("amount")
                .required()
                .build());
        options.addOption(Option.builder("i")
                .longOpt("input")
                .hasArg(true)
                .desc("input file path")
                .required()
                .build());
        options.addOption(Option.builder("f")
                .longOpt("from")
                .hasArg(true)
                .desc("from currency")
                .required()
                .build());
        options.addOption(Option.builder("t")
                .longOpt("to")
                .hasArg(true)
                .desc("to currency")
                .required()
                .build());
    }
    void parse(String[] args){
        try{
            //CLI (Step 2/3): Parsing Stage
            CommandLine cmd = parser.parse(options, args);
            //CLI (Step 3/3): Interrogation Stage
            if (cmd.hasOption("a")){
                amount = cmd.getOptionValue("a");
                System.out.println("amount: " + amount);
            }
            if (cmd.hasOption("f")){
                fromCurrency = cmd.getOptionValue("f");
                System.out.println("from currency: " + fromCurrency);
            }
            if (cmd.hasOption("i")){
                inputFilePath = cmd.getOptionValue("i");
                System.out.println("input file path: " + inputFilePath);
            }
            if (cmd.hasOption("t")){
                toCurrency = cmd.getOptionValue("t");
                System.out.println("to currency: " + toCurrency);
            }

        } catch (ParseException pe){
            System.out.println(pe.getMessage());
            helpFormatter.printHelp("currency-converter", options);
        }
    }
}