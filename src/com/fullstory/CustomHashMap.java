package com.fullstory;

public class CustomHashMap<K, V> {
    private static int INDEX_LIMIT = 10;
    private final DoublyLinkedList<K, V>[] arrayOfLinkedList;

    @SuppressWarnings("unchecked")
    private CustomHashMap() {
        this.arrayOfLinkedList = new DoublyLinkedList[INDEX_LIMIT];
    }

    private int hash(K key) {
        return key.hashCode() % INDEX_LIMIT;
    }

    private DoublyLinkedList<K, V> fetchListHoldingKey(K key) {
        int index = hash(key);
        if (this.arrayOfLinkedList[index] == null)
            this.arrayOfLinkedList[index] = new DoublyLinkedList<>();
        return this.arrayOfLinkedList[index];
    }

    public void put(K key, V value) {
        DoublyLinkedList<K, V> list = fetchListHoldingKey(key);
        list.add(key, value);
    }

    public V get(K key) throws KeyNotFoundException {
        return fetchListHoldingKey(key).fetchValueOfNodeWithKey(key);
    }

    public V get(K key, V defaultValue) {
        return fetchListHoldingKey(key).fetchValueOfNodeWithKey(key, defaultValue);
    }

    public boolean remove(K key) {
        return this.fetchListHoldingKey(key).removeNodeWithKeyIfExists(key);
    }
}
