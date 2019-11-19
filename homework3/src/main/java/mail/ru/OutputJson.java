package mail.ru;

public class OutputJson implements OutputStrategy {

    @Override
    public String outputHead(String name, String tabs) {
        return tabs + "\"" + name + "\": {\n";
    }

    @Override
    public String outputEnd(String name, String tabs) {
        return tabs + "},\n";
    }

    @Override
    public String outputLine(String name, String value, String tabs) {
        return tabs + "\"" + name + "\": " + value + ",\n";
    }

    @Override
    public String outputArrayLine(String value, int index, String tabs) {
        return tabs + value + ",\n";
    }
}
