package com.fullstory;

class DoublyLinkedList<K, V> {
    private KeyValuePair<K, V> head = null;

    private void insertAtBeginning(KeyValuePair<K, V> newKeyValuePair) {
        if (this.head != null) {
            newKeyValuePair.next(this.head.next());
            this.head.previous(newKeyValuePair);
        }
        this.head = newKeyValuePair;
    }

    private KeyValuePair<K, V> fetchNodeWithKey(K key) {
        KeyValuePair<K, V> tempPointer = this.head;
        while (tempPointer != null) {
            if (tempPointer.key().equals(key))
                return tempPointer;
            tempPointer = tempPointer.next();
        }
        return null;
    }

    private void deleteNode(KeyValuePair<K, V> node) {
        KeyValuePair<K, V> previousPointer = node.previous();
        KeyValuePair<K, V> nextPointer = node.next();
        node.next(null);
        node.previous(null);
        previousPointer.next(nextPointer);
        nextPointer.previous(previousPointer);
    }

    KeyValuePair<K, V> getEntireList() {
        return this.head;
    }

    void add(K key, V value) {
        KeyValuePair<K, V> newKeyValuePair = new KeyValuePair<>(key, value);
        removeNodeWithKeyIfExists(key);
        insertAtBeginning(newKeyValuePair);
    }

    V fetchValueOfNodeWithKey(K key, V defaultValue) {
        KeyValuePair<K, V> node = fetchNodeWithKey(key);
        if (node == null)
            return defaultValue;
        return node.value();
    }

    V fetchValueOfNodeWithKey(K key) throws KeyNotFoundException {
        KeyValuePair<K, V> node = fetchNodeWithKey(key);
        if (node == null)
            throw new KeyNotFoundException(key.toString());
        return node.value();
    }

    boolean removeNodeWithKeyIfExists(K key) {
        KeyValuePair<K, V> nodeToDelete = this.fetchNodeWithKey(key);
        if (nodeToDelete == null)
            return false;

        deleteNode(nodeToDelete);
        return true;
    }
}
