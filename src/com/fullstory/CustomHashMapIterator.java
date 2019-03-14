package com.fullstory;

import java.util.Iterator;

public class CustomHashMapIterator<K, V> implements Iterator<KeyValuePojo<K, V>> {
    private final int DEFAULT = -1;
    private final CustomHashMap<K, V> hashMap;
    private final Object hashMapEditLock;
    private int currentNodeInList;
    private int currentListLength;
    private int currentBucketIndex;

    CustomHashMapIterator(CustomHashMap<K, V> hashMap, Object hashMapEditLock) {
        this.hashMap = hashMap;
        this.hashMapEditLock = hashMapEditLock;
        this.currentBucketIndex = DEFAULT;
        this.currentListLength = DEFAULT;
        this.currentNodeInList = DEFAULT;
    }

    @Override
    public boolean hasNext() {
        if (this.currentBucketIndex == DEFAULT) {
            this.currentBucketIndex = this.hashMap.getFirstNonEmptyBucket();
        } else {
            this.currentBucketIndex = this.hashMap.getNextNonEmptyBucketFromIndex(this.currentBucketIndex);
        }
        return this.currentBucketIndex == DEFAULT;
    }

    @Override
    public KeyValuePojo<K, V> next() {
        return null;
    }
}
