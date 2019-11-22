package mail.ru;

public class OutputJsonStrategy implements OutputStrategy {

    @Override
    public String outputHead(String name) {
        return outputName(name) + "{";
    }

    @Override
    public String outputEnd(String name) {
        return "},";
    }

    @Override
    public String outputLine(String name, String value) {
        return outputName(name) + value + ",";
    }

    @Override
    public String outputArrayLine(String value, int index) {
        return value + ",";
    }

    private String outputName(String name){
        return "\"" + name + "\": ";
    }
}