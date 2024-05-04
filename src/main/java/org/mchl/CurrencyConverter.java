package org.mchl;

import org.apache.commons.cli.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class CurrencyConverter {
    public static void main(String[] args){
        //(Step 1/4) Get user inputs from CLI
        CommandLineInterface cli = new CommandLineInterface();
        Map<String, String> cliInputs = cli.cliInput(args);

        //(Step 2/4) Get currency:rate pairs from user-defined XML file
        XmlParser xmlParser = new XmlParser();
        Map<String, BigDecimal> currencyRatePairs = xmlParser.parseXml(cli.getInputFilePath());

        //(Step 3/4) Exchange rate converter
        //(Step 4/4) Principal calculator
        Calculator principalCalculator = new Calculator();
        BigDecimal principal = principalCalculator.compute(currencyRatePairs.get(cliInputs.get("fromCurrency")),currencyRatePairs.get(cliInputs.get("toCurrency")),BigDecimal.valueOf(Long.parseLong(cliInputs.get("amount"))));

        System.out.println("From: "+ cliInputs.get("fromCurrency")+ " | To: "+ cliInputs.get("toCurrency") + " | Amount: " +cliInputs.get("amount")+" | Result: "+ cliInputs.get("toCurrency") + " " +principal);
    }
}

class CommandLineInterface{
    private String amount, inputFilePath, fromCurrency, toCurrency;
    public String getAmount(){return amount;}
    public void setAmount(String amount){this.amount = amount;}
    public String getInputFilePath(){return inputFilePath;}
    public void setInputFilePath(String inputFilePath){this.inputFilePath = inputFilePath;}
    public String getFromCurrency(){return fromCurrency;}
    public void setFromCurrency(String fromCurrency){this.fromCurrency = fromCurrency;}
    public String getToCurrency(){return toCurrency;}
    public void setToCurrency(String toCurrency){this.toCurrency = toCurrency;}
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
    Map<String, String> cliInput(String[] args){
        try{
            //CLI (Step 2/3): Parsing Stage
            CommandLine cmd = parser.parse(options, args);
            //CLI (Step 3/3): Interrogation Stage
            if (cmd.hasOption("a")){
                setAmount(cmd.getOptionValue("a").trim());
            }
            if (cmd.hasOption("f")){
                setFromCurrency(cmd.getOptionValue("f").trim());
            }
            if (cmd.hasOption("i")){
                setInputFilePath(cmd.getOptionValue("i").trim());
            }
            if (cmd.hasOption("t")){
                setToCurrency(cmd.getOptionValue("t").trim());
            }
        } catch (ParseException pe){
            System.out.println(pe.getMessage());
            helpFormatter.printHelp("currency-converter", options);
        }
        return new HashMap<>(){{
            put("amount",getAmount());
            put("fromCurrency",getFromCurrency());
            put("inputFilePath",getInputFilePath());
            put("toCurrency",getToCurrency());
        }};
    }
}

class XmlParser{
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    Map<String, BigDecimal> parseXml(String inputFilePath){
        Map<String, BigDecimal> keyValuePairs = new HashMap<>(){{put("EUR", BigDecimal.valueOf(1.000000));}};
        try {
            // use the factory to create a documentbuilder
            DocumentBuilder builder = factory.newDocumentBuilder();

            // create a new document from input source
            FileInputStream fis = new FileInputStream(inputFilePath);
            InputSource is = new InputSource(fis);
            Document doc = builder.parse(is);

            NodeList nodeList = doc.getElementsByTagName("Cube");

            for (int i = 2; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                keyValuePairs.put(
                        element.getAttribute("currency"),
                        new BigDecimal(element.getAttribute("rate")).setScale(6, RoundingMode.HALF_EVEN)
                );
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return keyValuePairs;
    }
}

class Calculator{
    private BigDecimal exchangeRateConverter, outputPrincipal;
    public BigDecimal compute(BigDecimal fromCurrency, BigDecimal toCurrency, BigDecimal principal) {
        try {
            exchangeRateConverter = toCurrency.divide(fromCurrency, 6,RoundingMode.HALF_EVEN);
            outputPrincipal = principal.multiply(exchangeRateConverter).setScale(2,RoundingMode.HALF_EVEN);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return outputPrincipal;
    }
}