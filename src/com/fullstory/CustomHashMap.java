package com.fullstory;

import java.util.Iterator;

import static com.fullstory.CustomHashMapIterator.DEFAULT_INDEX;

public class CustomHashMap<K, V> implements Iterable<KeyValuePojo<K, V>> {
    private static int BUCKET_CAPACITY = 64;
    private DoublyLinkedList<K, V>[] buckets;
    private final Object editLock = new Object();

    @SuppressWarnings("unchecked")
    private CustomHashMap() {
        this.buckets = new DoublyLinkedList[BUCKET_CAPACITY];
    }

    private int hash(K key) {
        return key.hashCode() % BUCKET_CAPACITY;
    }

    private DoublyLinkedList<K, V> fetchListHoldingKey(K key) {
        int index = hash(key);
        if (this.buckets[index] == null)
            this.buckets[index] = new DoublyLinkedList<>();
        return this.buckets[index];
    }

    // Package private and can only be used by the Iterator!
    int getNextNonEmptyBucketFromIndex(int index) {
        if (index >= BUCKET_CAPACITY)
            return DEFAULT_INDEX;
        int i = index;
        while (i < BUCKET_CAPACITY && this.buckets[i] == null)
            i++;
        return i == BUCKET_CAPACITY ? DEFAULT_INDEX : i;
    }

    DoublyLinkedList<K, V> getListInBucket(int index) {
        return this.buckets[index];
    }

    public void put(K key, V value) {
        synchronized (editLock) {
            DoublyLinkedList<K, V> list = fetchListHoldingKey(key);
            list.add(key, value);
        }
    }

    public V get(K key) throws KeyNotFoundException {
        return fetchListHoldingKey(key).fetchValueInNodeWithKey(key);
    }

    public V get(K key, V defaultValue) {
        return fetchListHoldingKey(key).fetchValueInNodeWithKey(key, defaultValue);
    }

    public V getThreadSafe(K key, V defaultValue) {
        synchronized (editLock) {
            return fetchListHoldingKey(key).fetchValueInNodeWithKey(key, defaultValue);
        }
    }

    public boolean remove(K key) {
        synchronized (editLock) {
            DoublyLinkedList<K, V> listContainingNodeToRemove = this.fetchListHoldingKey(key);
            boolean status = listContainingNodeToRemove.removeNodeWithKeyIfExists(key);
            if (listContainingNodeToRemove.length() == 0)
                this.buckets[hash(key)] = null;
            return status;
        }
    }

    public void clear() {
        synchronized (editLock) {
            for (int i = 0; i < BUCKET_CAPACITY; i++) {
                this.buckets[i] = null;
            }
            System.gc();
        }
    }

    @Override
    public Iterator<KeyValuePojo<K, V>> iterator() {
        return new CustomHashMapIterator<>(this, editLock);
    }
}
