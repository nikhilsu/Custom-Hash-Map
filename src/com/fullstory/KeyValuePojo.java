package com.fullstory;

public class KeyValuePojo<K, V> {
    private final K key;
    private final V value;

    // Package private
    KeyValuePojo(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K key() {
        return key;
    }

    public V value() {
        return value;
    }
}
