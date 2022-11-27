package it.itzsamirr.clansplus.utils.reflections;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class ReflectionUtils {
    private Map<String, ReflectionClass<?>> cache = new HashMap<>();

    public ReflectionClass<?> getClass(String name){
        return cache.computeIfAbsent(name, s -> {
            try {
                return new ReflectionClass<>(Class.forName(s));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    public <T> ReflectionClass<T> getClass(Class<T> clazz){
        return (ReflectionClass<T>) cache.computeIfAbsent(clazz.getName(), s -> new ReflectionClass<>(clazz));
    }
}
