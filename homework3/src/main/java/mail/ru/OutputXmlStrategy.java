package mail.ru;

public class OutputXmlStrategy implements OutputStrategy {

    @Override
    public String outputHead(String name) {
        return "<" + name + ">";
    }

    @Override
    public String outputEnd(String name) {
        return "</" + name + ">";
    }

    @Override
    public String outputLine(String name, String value) {
        return outputHead(name) + value + outputEnd(name);
    }

    @Override
    public String outputArrayLine(String value, int index) {
        return outputLine(String.valueOf(index), value);
    }
}
