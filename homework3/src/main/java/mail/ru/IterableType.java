package mail.ru;

import java.util.*;

public enum IterableType {
    ARRAY {
        @Override
        public Map<?, ?> getValues(Object o) {
            Map<Integer,Object> retMap = new HashMap<>();
            List<?> arrayValues = (List)o;
            for (int i = 0; i < arrayValues.size(); i++) {
                retMap.put(i, arrayValues.get(i));
            }
            return retMap;
        }
    },
    COLLECTION {
        @Override
        public Map<?, ?> getValues(Object o) {
            Map<Integer,Object> retMap = new HashMap<>();
            List<?> arrayValues = Arrays.asList(((Collection) o).toArray());
            for (int i = 0; i < arrayValues.size(); i++) {
                retMap.put(i, arrayValues.get(i));
            }
            return retMap;
        }
    },
    MAP {
        @Override
        public Map<?, ?> getValues(Object o) {
            return (Map)o;
        }
    };

    public static IterableType getIterableType(Object o) {
        if (o instanceof Object[]) return ARRAY;
        if (o instanceof Collection) return COLLECTION;
        if (o instanceof Map) return MAP;
        return null;
    }

    public abstract Map<?, ?> getValues(Object o);
}