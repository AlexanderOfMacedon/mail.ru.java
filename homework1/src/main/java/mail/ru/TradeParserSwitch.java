package mail.ru;

import org.json.JSONObject;

import java.util.Scanner;

public class TradeParserSwitch implements TradeParser {
    @Override
    public SimpleTrade getTrade(Scanner scanner) {
        JSONObject jsonObject = TradeParser.getTradeFromScanner(scanner);
        SimpleTrade object;
        switch (jsonObject.getString("type")) {
            case "FX_SPOT":
                object = new Fx_spot(jsonObject.getInt("price"));
                return object;
            case "BOND":
                object = new Bond(jsonObject.getInt("price"));
                return object;
            default:
                System.out.println("Unexpected value of type: " + jsonObject.getString("type"));
                return null;
        }
    }
}