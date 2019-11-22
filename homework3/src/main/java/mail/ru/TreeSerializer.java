package mail.ru;

import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;
import java.util.*;

public class TreeSerializer implements Serializer {
    private final int shift = 3;
    private final OutputStrategy outputStrategy;

    public TreeSerializer(OutputStrategy outputOptions) {
        this.outputStrategy = outputOptions;
    }

    @Override
    public String serialize(Object o) {
        try {
            return serializeManage(o, o.getClass().getSimpleName(), 0);
        } catch (IllegalAccessException | InaccessibleObjectException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private String serializeParser(Object o, int numTabs) throws IllegalAccessException {
        StringBuilder ret = new StringBuilder();
        Field[] declaredFields = o.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            if (field.isSynthetic()) {
//                для внутренник классов, чтобы не было зацикливания
                continue;
            }
            if (field.getType().isPrimitive() || field.get(o).getClass() == String.class) {
                ret.append(tabsCreate(numTabs) +
                        outputStrategy.outputLine(field.getName(), field.get(o).toString()) + "\n");
            } else {
                ret.append(serializeManage(field.get(o), field.getName(), numTabs));
            }
        }
        return ret.toString();
    }

    private String serializeManage(Object o, String oName, int numTabs) throws IllegalAccessException, InaccessibleObjectException {
        StringBuilder ret = new StringBuilder();
        if (o == null) {
            ret.append(tabsCreate(numTabs) + outputStrategy.outputLine(oName, "null") + "\n");
            return ret.toString();
        }
        if (o instanceof Object[] || o instanceof Collection || o instanceof Map) {
            ret.append(iterableSerialize(oName, o, numTabs) + "\n");
            return ret.toString();
        }
        ret.append(tabsCreate(numTabs) + outputStrategy.outputHead(oName) + "\n");
        ret.append(serializeParser(o, numTabs + shift));
        ret.append(tabsCreate(numTabs) + outputStrategy.outputEnd(oName) + "\n");
        return ret.toString();
    }

    private String tabsCreate(int numTabs) {
        return " ".repeat(numTabs);
    }

    private String mapSerialize(Map<?,?> map, int numTabs){
        StringBuilder ret = new StringBuilder();
        Set<?> set = map.keySet();
        for(Object key : set){
            ret.append(tabsCreate(numTabs) + outputStrategy.outputLine(key.toString(), map.get(key).toString()) + "\n");
        }
        return ret.toString();
    }

    private String collectionSerialize(Collection<?> collection, int numTabs){
        StringBuilder ret = new StringBuilder();
        List<?> list = (List)collection;
        for(int i=0;i<list.size();i++){
            ret.append(tabsCreate(numTabs) + outputStrategy.outputArrayLine(list.get(i).toString(), i + 1) + "\n");
        }
        return ret.toString();
    }

    private String iterableSerialize(String name, Object o, int numTabs) {
        StringBuilder ret = new StringBuilder();
        ret.append(tabsCreate(numTabs) + outputStrategy.outputHead(name) + "\n");
        IterableType iterableType= IterableType.getIterableType(o);
        Map<?,?> map = iterableType.getValues(o);
        if(iterableType == IterableType.MAP){
            ret.append(mapSerialize(map, numTabs + shift));
        } else {
            System.out.println(map.values().getClass().getName());
            ret.append(collectionSerialize(new ArrayList<>(map.values()), numTabs + shift));
        }
        ret.append(tabsCreate(numTabs) + outputStrategy.outputEnd(name));
        return ret.toString();
    }

    public static void main(String[] args) throws IllegalAccessException, InaccessibleObjectException {
        TreeSerializer serializer = new TreeSerializer(new OutputXmlStrategy());
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("key", "value");
        System.out.println(serializer.serialize(hashMap));
    }
}
