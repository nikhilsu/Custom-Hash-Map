package com.fullstory;

import java.util.Objects;

public class KeyValuePojo<K, V> {
    private final K key;
    private final V value;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeyValuePojo<?, ?> that = (KeyValuePojo<?, ?>) o;
        return Objects.equals(key, that.key) &&
                Objects.equals(value, that.value);
    }
}
