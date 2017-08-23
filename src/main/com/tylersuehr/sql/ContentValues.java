package com.tylersuehr.sql;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 8/23/2017.
 *
 * This is used to store data to be inserted into the SQLite database. This basically wraps
 * {@link Map} for the purpose of only allowing specific data-types to be added into the map.
 */
public final class ContentValues {
    private final Map<String, Object> data;


    public ContentValues() {
        this.data = new LinkedHashMap<>();
    }

    public ContentValues(int initialCapacity) {
        this.data = new LinkedHashMap<>(initialCapacity);
    }

    public ContentValues put(String key, String value) {
        this.data.put(key, value);
        return this;
    }

    public ContentValues put(String key, int value) {
        this.data.put(key, value);
        return this;
    }

    public ContentValues put(String key, short value) {
        this.data.put(key, value);
        return this;
    }

    public ContentValues put(String key, long value) {
        this.data.put(key, value);
        return this;
    }

    public ContentValues put(String key, float value) {
        this.data.put(key, value);
        return this;
    }

    public ContentValues put(String key, double value) {
        this.data.put(key, value);
        return this;
    }

    public ContentValues put(String key, boolean value) {
        this.data.put(key, value);
        return this;
    }

    public ContentValues put(String key, Serializable value) {
        this.data.put(key, value);
        return this;
    }

    public int size() {
        return data.size();
    }

    Object get(String key) {
        return data.get(key);
    }

    @SuppressWarnings("unchecked")
    <T> T find(String key) {
        return (T)data.get(key);
    }

    // For testing...
    Collection<Object> getData() {
        return data.values();
    }

    // For testing...
    Set<String> getKeys() {
        return data.keySet();
    }
}