package mail.ru;

public interface OutputStrategy {
    String outputHead(String name, String tabs);

    String outputEnd(String name, String tabs);

    String outputLine(String name, String value, String tabs);

    String outputArrayLine(String value, int index, String tabs);
}
