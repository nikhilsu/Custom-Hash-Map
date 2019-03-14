package com.fullstory;

import java.util.Iterator;

public class CustomHashMapIterator<K, V> implements Iterator<KeyValuePojo<K, V>> {
    private final DoublyLinkedList<K, V>[] hashMapBuckets;
    private final Object hashMapEditLock;
    private int currentNodeInList;
    private int currentListLength;
    private int currentBucketIndex;

    CustomHashMapIterator(DoublyLinkedList<K, V>[] buckets, Object hashMapEditLock) {
        this.hashMapBuckets = buckets;
        this.hashMapEditLock = hashMapEditLock;
        this.currentBucketIndex = 0;
        this.currentListLength = 0;
        this.currentNodeInList = 0;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public KeyValuePojo<K, V> next() {
        return null;
    }
}
