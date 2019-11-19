package mail.ru;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public enum IterableType {
    ARRAY,
    COLLECTION,
    MAP;
    static public IterableType getIterableType(Object o){
        if( o.getClass().isArray()){
            return IterableType.ARRAY;
        }
        if(o instanceof Collection){
            return IterableType.COLLECTION;
        }
        if(o instanceof Map){
            return IterableType.MAP;
        }
        return null;
    }
}
