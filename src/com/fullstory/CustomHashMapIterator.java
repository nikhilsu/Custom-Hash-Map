package com.fullstory;

import java.util.Iterator;

public class CustomHashMapIterator<K, V> implements Iterator<KeyValuePojo<K, V>> {
    final static int DEFAULT_INDEX = -1;
    private final CustomHashMap<K, V> hashMap;
    private final Object hashMapEditLock;
    private int currentNodeIndexInList;
    private int currentBucketIndex;

    // Package-private
    CustomHashMapIterator(CustomHashMap<K, V> hashMap, Object hashMapEditLock) {
        this.hashMap = hashMap;
        this.hashMapEditLock = hashMapEditLock;
        this.currentBucketIndex = 0;
        this.currentNodeIndexInList = DEFAULT_INDEX;
    }

    private void updateNonEmptyBucketIndex() {
        int updatedBucketIndex = this.hashMap.getNextNonEmptyBucketFromIndex(this.currentBucketIndex);

        if (updatedBucketIndex != this.currentBucketIndex) {
            this.currentBucketIndex = updatedBucketIndex;
            this.currentNodeIndexInList = DEFAULT_INDEX;
        } else if (this.currentNodeIndexInList + 1 >= lengthOfListInCurrentBucket()) {
            this.currentBucketIndex = this.hashMap.getNextNonEmptyBucketFromIndex(this.currentBucketIndex + 1);
            this.currentNodeIndexInList = DEFAULT_INDEX;
        }
    }

    private DoublyLinkedList<K, V> getCurrentList() {
        return this.hashMap.getListInBucket(this.currentBucketIndex);
    }

    private int lengthOfListInCurrentBucket() {
        return getCurrentList().length();
    }

    private KeyValuePojo<K, V> getNextPojo() {
        this.currentNodeIndexInList++;
        return getCurrentList().getPojoOfNodeAtIndex(this.currentNodeIndexInList);
    }

    private boolean hasNextUnsafe() {
        updateNonEmptyBucketIndex();
        return this.currentBucketIndex != DEFAULT_INDEX;
    }

    @Override
    public boolean hasNext() {
        synchronized (this.hashMapEditLock) {
            return hasNextUnsafe();
        }
    }

    @Override
    public KeyValuePojo<K, V> next() {
        synchronized (this.hashMapEditLock) {
            if (hasNextUnsafe())
                return getNextPojo();
            return null;
        }
    }
}
