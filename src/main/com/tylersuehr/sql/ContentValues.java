package com.tylersuehr.sql;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Basic wrapping of the Java {@link Map} object.
 *
 * The purpose of doing this is to normalize what types of objects can be inserted into
 * the SQLite database, as it only allows for specific data types.
 *
 * @author Tyler Suehr
 */
public final class ContentValues {
    private final Map<String, Object> data;


    public ContentValues() {
        this.data = new LinkedHashMap<>();
    }

    public ContentValues(final int initialCapacity) {
        this.data = new LinkedHashMap<>(initialCapacity);
    }

    public ContentValues put(final String key, final String value) {
        this.data.put(key, value);
        return this;
    }

    public ContentValues put(final String key, final int value) {
        this.data.put(key, value);
        return this;
    }

    public ContentValues put(final String key, final short value) {
        this.data.put(key, value);
        return this;
    }

    public ContentValues put(final String key, final long value) {
        this.data.put(key, value);
        return this;
    }

    public ContentValues put(final String key, final float value) {
        this.data.put(key, value);
        return this;
    }

    public ContentValues put(final String key, final double value) {
        this.data.put(key, value);
        return this;
    }

    public ContentValues put(final String key, final boolean value) {
        this.data.put(key, value);
        return this;
    }

    public ContentValues put(final String key, final Serializable value) {
        this.data.put(key, value);
        return this;
    }

    public int size() {
        return data.size();
    }

    Object get(final String key) {
        return data.get(key);
    }

    @SuppressWarnings("unchecked")
    <T> T getSerializable(final String key) {
        return (T)data.get(key);
    }

    Collection<Object> getData() {
        return data.values();
    }

    Set<String> getKeys() {
        return data.keySet();
    }
}