package mail.ru;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;
import java.util.*;

public class TreeSerializer implements Serializer {
    private final int shift = 3;
    private OutputStrategy outputStrategy;

    public TreeSerializer(OutputStrategy outputOptions) {
        this.outputStrategy = outputOptions;
    }

    public void setOutputStrategy(OutputStrategy outputStrategy) {
        this.outputStrategy = outputStrategy;
    }

    @Override
    public String serialize(Object o) throws IllegalAccessException, InaccessibleObjectException {
        return serializeRe(o, o.getClass().getSimpleName(), 0);
    }

    private String serializeRe(Object o, String oName, int num_tabs) throws IllegalAccessException, InaccessibleObjectException {
        StringBuilder ret = new StringBuilder();
        if (o == null) {
            ret.append(outputStrategy.outputLine(oName, "null", tabsCreate(num_tabs)));
            return ret.toString();
        }
        if (o.getClass().isArray() || o instanceof Collection || o instanceof Map) {
            ret.append(iterableSerialize(oName, o, num_tabs));
            return ret.toString();
        }
        ret.append(outputStrategy.outputHead(oName, tabsCreate(num_tabs)));
        Field[] declaredFieds = o.getClass().getDeclaredFields();
        for (Field field : declaredFieds) {
            field.setAccessible(true);
            if (field.getName().equals("this$0")) {
//                для внутренник классов, чтобы не было зацикливания
                continue;
            }
            if (field.getType().isPrimitive() || field.get(o).getClass().isInstance("String")) {
                ret.append(outputStrategy.outputLine(field.getName(), field.get(o).toString(),
                        tabsCreate(num_tabs + shift)));
            } else {
                ret.append(serializeRe(field.get(o), field.getName(), num_tabs + shift));
            }
        }
        ret.append(outputStrategy.outputEnd(oName, tabsCreate(num_tabs)));
        return ret.toString();
    }

    private String tabsCreate(int num_tabs) {
        char[] charsTabs = new char[num_tabs];
        Arrays.fill(charsTabs, ' ');
        return new String(charsTabs);
    }

    private String arraySerialize(String name, Object array, int num_tabs) {
        StringBuilder ret = new StringBuilder();
        ret.append(outputStrategy.outputHead(name, tabsCreate(num_tabs)));
        if (Array.getLength(array) == 0) {
            ret.append(tabsCreate(num_tabs + shift) + "EMPTY ARRAY\n");
        }
        for (int i = 1; i <= Array.getLength(array); i++) {
            Object o = Array.get(array, i - 1);
            String oValue;
            if (o == null) {
                oValue = "null";
            } else {
                oValue = o.toString();
            }
            ret.append(outputStrategy.outputArrayLine(oValue, i, tabsCreate(num_tabs + shift)));
        }
        ret.append(outputStrategy.outputEnd(name, tabsCreate(num_tabs)));
        return ret.toString();
    }

    private String collectionSerialize(String name, Collection collection, int num_tab) {
        return arraySerialize(name, collection.toArray(), num_tab);
    }

    private String mapSerialize(String name, Map map, int num_tabs) {
        Object[] arrayKeys = map.keySet().toArray();
        StringBuilder ret = new StringBuilder();
        ret.append(outputStrategy.outputHead(name, tabsCreate(num_tabs)));
        if (Array.getLength(arrayKeys) == 0) {
            ret.append(tabsCreate(num_tabs + shift) + "EMPTY ARRAY\n");
        }
        for (int i = 1; i <= Array.getLength(arrayKeys); i++) {
            Object o = map.get(Array.get(arrayKeys, i - 1));
            String oValue;
            if (o == null) {
                oValue = "null";
            } else {
                oValue = o.toString();
            }
            ret.append(outputStrategy.outputLine(Array.get(arrayKeys, i - 1).toString(), oValue,
                    tabsCreate(num_tabs + shift)));
        }
        ret.append(outputStrategy.outputEnd(name, tabsCreate(num_tabs)));
        return ret.toString();
    }
    
    private String listsSerialize(List arrayKeys, List arrayValues, int num_tabs){
        if(arrayKeys != null && arrayKeys.size() != arrayValues.size()){
            return "BAD LISTS";
        }
        StringBuilder ret = new StringBuilder();
        for(int index = 1;index<=arrayValues.size();index++){
            String oValue;
            if (arrayValues.get(index - 1) == null) {
                oValue = "null";
            } else {
                oValue = arrayValues.get(index - 1).toString();
            }
            if(arrayKeys != null){
                ret.append(outputStrategy.outputLine(arrayKeys.get(index - 1).toString(), oValue,
                        tabsCreate(num_tabs + shift)));
            } else {
                ret.append(outputStrategy.outputArrayLine(oValue, index, tabsCreate(num_tabs + shift)));
            }
        }
        return ret.toString();
    }
    
    private String iterableSerialize(String name, Object o, int num_tabs){
        StringBuilder ret = new StringBuilder();
        ret.append(outputStrategy.outputHead(name, tabsCreate(num_tabs)));
        List arrayKeys;
        List arrayValues = new LinkedList<>();
        switch (IterableType.getIterableType(o)){
            case MAP:
                Map map = (Map)o;
                arrayKeys = Arrays.asList(map.keySet().toArray());
                for(Object key : arrayKeys){
                    arrayValues.add(map.get(key));
                }
                break;
            case ARRAY:
                arrayKeys = null;
                arrayValues = (List)o;
                break;
            case COLLECTION:
                arrayKeys = null;
                arrayValues = Arrays.asList(((Collection) o).toArray());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + IterableType.getIterableType(o));
        }
        ret.append(listsSerialize(arrayKeys, arrayValues, num_tabs + shift));
        ret.append(outputStrategy.outputEnd(name, tabsCreate(num_tabs)));
        return ret.toString();
    }

    public static void main(String[] args) throws IllegalAccessException, InaccessibleObjectException {
        TreeSerializer serializer = new TreeSerializer(new OutputXml());
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("key", "value");
        System.out.println(serializer.serialize(hashMap));
    }
}
