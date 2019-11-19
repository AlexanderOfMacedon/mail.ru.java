package mail.ru;

import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static org.junit.Assert.*;

public class TradeParserSwitchTest {

    @Test
    public void getTrade() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("test.txt"));
        writer.write("Trade: {\"type\": FX_SPOT,\"price\": 25000}");
        writer.close();
        Scanner scanner = new Scanner(new File("test.txt"));
        TradeParserSwitch parserEnum = new TradeParserSwitch();
        System.out.println(parserEnum.getTrade(scanner).getPrice());
    }
}