package mail.ru;

public class OutputXml implements OutputStrategy {

    @Override
    public String outputHead(String name, String tabs) {
        return tabs + "<" + name + ">\n";
    }

    @Override
    public String outputEnd(String name, String tabs) {
        return tabs + "</" + name + ">\n";
    }

    @Override
    public String outputLine(String name, String value, String tabs) {
        return tabs + "<" + name + ">" + value + "</" + name + ">\n";
    }

    @Override
    public String outputArrayLine(String value, int index, String tabs) {
        return tabs + "<" + index + ">" + value + "</" + index + ">\n";
    }
}
