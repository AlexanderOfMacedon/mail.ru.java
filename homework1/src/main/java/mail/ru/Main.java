package mail.ru;


import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        TradeParserEnum parserEnum = new TradeParserEnum();
        TradeParserSwitch parserSwitch = new TradeParserSwitch();

//        using console
//        System.out.println(parserEnum.getTrade(new Scanner(System.in)));


//        using file
        Scanner scanner = new Scanner(System.in);
        System.out.println(parserSwitch.getTrade(new Scanner(new File(scanner.next()))).getPrice());
        scanner.close();
    }
}