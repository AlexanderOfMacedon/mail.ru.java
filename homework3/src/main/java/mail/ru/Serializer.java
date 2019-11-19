package mail.ru;

import java.lang.reflect.InaccessibleObjectException;

public interface Serializer {
    String serialize(Object o) throws IllegalAccessException, InaccessibleObjectException;
}
