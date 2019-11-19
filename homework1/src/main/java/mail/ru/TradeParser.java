package mail.ru;

import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

public interface TradeParser {
    SimpleTrade getTrade(Scanner scanner);

    static JSONObject getTradeFromScanner(Scanner scanner) {
        StringBuilder data = new StringBuilder();
        String line;
        while (scanner.hasNext()) {
            line = scanner.next();
            data.append(line);
            if (line.contains("}") || line.contains("##")) {
                break;
            }
        }
        return new JSONObject(data.substring(6));
    }
}