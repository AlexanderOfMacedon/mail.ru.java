package mail.ru;

import org.junit.Test;

import java.util.Scanner;

import static org.junit.Assert.*;

public class TradeParserEnumTest {

    @Test
    public void getTrade() {
        Scanner scanner = new Scanner("Trade: {\"type\": BOND,\"price\": 1000}");
        TradeParserEnum parserEnum = new TradeParserEnum();
        System.out.println(parserEnum.getTrade(scanner));
    }
}