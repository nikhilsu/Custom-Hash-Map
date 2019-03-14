package com.fullstory;

import java.util.Iterator;

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

    int getFirstNonEmptyBucket() {
        return getNextNonEmptyBucketFromIndex(0);
    }

    // Package private and can only be used by the Iterator!
    int getNextNonEmptyBucketFromIndex(int index) {
        synchronized (editLock) {
            if (index >= BUCKET_CAPACITY)
                return -1;
            int i = index;
            while (i < BUCKET_CAPACITY && this.buckets[i] == null)
                i++;
            return i == BUCKET_CAPACITY ? -1 : i;
        }
    }

    public void put(K key, V value) {
        synchronized (editLock) {
            DoublyLinkedList<K, V> list = fetchListHoldingKey(key);
            list.add(key, value);
        }
    }

    public V get(K key) throws KeyNotFoundException {
        return fetchListHoldingKey(key).fetchValueOfNodeWithKey(key);
    }

    public V get(K key, V defaultValue) {
        return fetchListHoldingKey(key).fetchValueOfNodeWithKey(key, defaultValue);
    }

    public V getThreadSafe(K key, V defaultValue) {
        synchronized (editLock) {
            return fetchListHoldingKey(key).fetchValueOfNodeWithKey(key, defaultValue);
        }
    }

    public boolean remove(K key) {
        synchronized (editLock) {
            return this.fetchListHoldingKey(key).removeNodeWithKeyIfExists(key);
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
