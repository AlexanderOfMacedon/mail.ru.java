package mail.ru;

import org.json.JSONObject;

import java.util.Scanner;

public class TradeParserEnum implements TradeParser {
    @Override
    public SimpleTrade getTrade(Scanner scanner) {
        JSONObject jsonObject = TradeParser.getTradeFromScanner(scanner);
        TradeType trade = TradeType.valueOf(jsonObject.getString("type"));
        return trade.getTrade(jsonObject.getInt("price"));
    }
}