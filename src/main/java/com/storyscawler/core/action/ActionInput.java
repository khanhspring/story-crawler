package com.storyscawler.core.action;

import java.util.*;

public class ActionInput extends HashMap<String, Object> {

    public static ActionInput of(String k, Object v) {
        var input = new ActionInput();
        input.put(k, v);
        return input;
    }

    public <T> List<T> getList(String key, Class<T> tClass) {
        var values = this.get(key);
        if (Objects.isNull(values)) {
            return null;
        }
        if (values instanceof List<?> items) {
            List<T> results = new ArrayList<>();
            for (var item : items) {
                if (item.getClass().isAssignableFrom(tClass)) {
                    T result = tClass.cast(item);
                    results.add(result);
                }
            }
            return results;
        }
        throw new ClassCastException();
    }

    public <T> T getObject(String key, Class<T> tClass) {
        var value = this.get(key);
        if (Objects.isNull(value)) {
            return null;
        }
        if (value.getClass().isAssignableFrom(tClass)) {
            return tClass.cast(value);
        }
        throw new ClassCastException();
    }

    public String getString(String key) {
        return getObject(key, String.class);
    }

    public Boolean getBoolean(String key) {
        return getObject(key, Boolean.class);
    }

    public Integer getInteger(String key) {
        return getObject(key, Integer.class);
    }
}
