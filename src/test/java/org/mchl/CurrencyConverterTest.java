package org.mchl;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CurrencyConverterTest {

    @Test
    public void givenCliInput_validateExtraction(){
        CommandLineInterface cli = new CommandLineInterface();
        Map<String, String> cliInputs = cli.cliInput(new String[]{"-a 1000", "-f EUR", "-t HKD", "-i src/main/resources/eurofxref-daily.xml"});

        assertEquals("1000",cliInputs.get("amount"));
        assertEquals("src/main/resources/eurofxref-daily.xml",cliInputs.get("inputFilePath"));
        assertEquals("EUR",cliInputs.get("fromCurrency"));
        assertEquals("HKD",cliInputs.get("toCurrency"));
    }

    @Test
    @Disabled("Not implemented yet")
    public void givenXML_whenExtracting_thenReturnReferenceRates(){

    }

    @Disabled
    @MethodSource("provideInputsAndExpectedOutputs")
    public void runTestCases(String input, String expectedOutput) {
        assertEquals(expectedOutput, input);
    }

    private static Stream<Arguments> provideInputsAndExpectedOutputs(){
        return Stream.of(
                Arguments.of("123", "abc")
        );
    }
}
