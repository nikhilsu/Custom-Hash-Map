package com.fullstory;

class KeyValuePair<K, V> {
    private final K key;
    private final V value;
    private KeyValuePair<K, V> next;
    private KeyValuePair<K, V> previous;

    KeyValuePair(K key, V value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }

    void next(KeyValuePair<K, V> next) {
        this.next = next;
    }

    KeyValuePair<K, V> next() {
        return this.next;
    }

    void previous(KeyValuePair<K, V> previous) {
        this.previous = previous;
    }

    KeyValuePair<K, V> previous() {
        return this.previous;
    }

    K key() {
        return key;
    }

    V value() {
        return value;
    }
}