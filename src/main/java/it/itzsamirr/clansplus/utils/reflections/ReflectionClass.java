package it.itzsamirr.clansplus.utils.reflections;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public final class ReflectionClass<T> {
    private Class<T> clazz;
    private Map<String, Field> fields = new HashMap<>();

    protected ReflectionClass(Class<T> clazz){
        this.clazz = clazz;
        load();
    }

    public Class<T> getParent() {
        return clazz;
    }

    public void load(){
        for(Field field : clazz.getDeclaredFields()){
            fields.putIfAbsent(field.getName(), field);
        }
    }

    public boolean hasField(String name){
        return fields.containsKey(name);
    }

    public Map<String, Field> getFields() {
        return fields;
    }

    public Field getField(String name){
        return fields.computeIfAbsent(name, s -> {
            try {
                return clazz.getDeclaredField(s);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            return null;
        });
    }
}
