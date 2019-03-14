package com.fullstory;

class KeyValueNode<K, V> {
    private final K key;
    private final V value;
    private KeyValueNode<K, V> next;
    private KeyValueNode<K, V> previous;

    KeyValueNode(K key, V value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }

    void next(KeyValueNode<K, V> next) {
        this.next = next;
    }

    KeyValueNode<K, V> next() {
        return this.next;
    }

    void previous(KeyValueNode<K, V> previous) {
        this.previous = previous;
    }

    KeyValueNode<K, V> previous() {
        return this.previous;
    }

    K key() {
        return key;
    }

    V value() {
        return value;
    }

    KeyValuePojo<K, V> pojo() {
        return new KeyValuePojo<>(key, value);
    }
}