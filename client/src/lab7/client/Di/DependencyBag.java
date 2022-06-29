package lab7.client.Di;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DependencyBag {
    private static final Map<Class<?>, Object> bag = Collections.synchronizedMap(new HashMap<>());

    public static <T> void putSingleton(Class<T> tClass, T singleton) {
        bag.put(tClass, singleton);
    }

    public static <T> T getSingleton(Class<T> tClass) {
        return (T) bag.get(tClass);
    }
}
