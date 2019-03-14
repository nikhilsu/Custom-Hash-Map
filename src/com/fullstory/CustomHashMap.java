package com.fullstory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.fullstory.CustomHashMapIterator.DEFAULT_INDEX;

@SuppressWarnings({"WeakerAccess", "unused"})
public class CustomHashMap<K, V> implements Iterable<KeyValuePojo<K, V>> {
    private static int BUCKET_CAPACITY = 64;
    private DoublyLinkedList<K, V>[] buckets;
    private final Object editLock = new Object();

    // For Injecting Mocks from Unit Tests! Package-private
    CustomHashMap(DoublyLinkedList<K, V>[] buckets, int capacity) {
        this.buckets = buckets;
        BUCKET_CAPACITY = capacity;
    }

    // For Shipping.
    public CustomHashMap() {
        init();
    }

    @SuppressWarnings("unchecked")
    private void init() {
        this.buckets = new DoublyLinkedList[BUCKET_CAPACITY];
        for (int i = 0; i < BUCKET_CAPACITY; i++) {
            this.buckets[i] = new DoublyLinkedList<>();
        }
    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % BUCKET_CAPACITY;
    }

    private DoublyLinkedList<K, V> fetchListHoldingKey(K key) {
        int index = hash(key);
        return this.buckets[index];
    }

    private Stream<DoublyLinkedList<K, V>> nonEmptyBucketsStream() {
        return Arrays.stream(this.buckets).filter(bucket -> !bucket.isEmpty());
    }

    int getNextNonEmptyBucketFromIndex(int index) {
        if (index >= BUCKET_CAPACITY)
            return DEFAULT_INDEX;
        int i = index;
        while (i < BUCKET_CAPACITY && this.buckets[i].isEmpty())
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

    public void remove(K key) throws KeyNotFoundException {
        synchronized (editLock) {
            DoublyLinkedList<K, V> listContainingNodeToRemove = this.fetchListHoldingKey(key);
            if (!listContainingNodeToRemove.removeNodeWithKeyIfExists(key))
                throw new KeyNotFoundException(key.toString());
        }
    }

    public void clear() {
        synchronized (editLock) {
            init();
            System.gc();
        }
    }

    public Set<K> keys() {
        return nonEmptyBucketsStream().flatMap(DoublyLinkedList::getAllKeys).collect(Collectors.toSet());
    }

    public List<V> values() {
        return nonEmptyBucketsStream().flatMap(DoublyLinkedList::getAllValues).collect(Collectors.toList());
    }

    public List<KeyValuePojo<K, V>> keyValuePojo() {
        return nonEmptyBucketsStream().flatMap(DoublyLinkedList::getAllPojo).collect(Collectors.toList());
    }

    public boolean isPresent(K key) {
        return !fetchListHoldingKey(key).isEmpty();
    }

    @Override
    public Iterator<KeyValuePojo<K, V>> iterator() {
        return new CustomHashMapIterator<>(this, editLock);
    }
}
