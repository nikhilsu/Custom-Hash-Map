package com.fullstory;

import java.util.ArrayList;
import java.util.stream.Stream;

class DoublyLinkedList<K, V> {
    private KeyValueNode<K, V> head;
    private int numberOfNodes;

    DoublyLinkedList() {
        this.head = null;
        this.numberOfNodes = 0;
    }

    private void insertAtBeginning(KeyValueNode<K, V> newKeyValueNode) {
        if (this.head != null) {
            newKeyValueNode.next(this.head.next());
            this.head.previous(newKeyValueNode);
        }
        this.head = newKeyValueNode;
        numberOfNodes++;
    }

    private KeyValueNode<K, V> fetchNodeWithKey(K key) {
        KeyValueNode<K, V> tempPointer = this.head;
        while (tempPointer != null) {
            if (tempPointer.key().equals(key))
                return tempPointer;
            tempPointer = tempPointer.next();
        }
        return null;
    }

    private void deleteNode(KeyValueNode<K, V> node) {
        numberOfNodes--;
        KeyValueNode<K, V> previousPointer = node.previous();
        KeyValueNode<K, V> nextPointer = node.next();
        if (previousPointer == null) {
            this.head = node.next();
        } else if (nextPointer == null) {
            previousPointer.next(null);
        } else {
            node.next(null);
            node.previous(null);
            previousPointer.next(nextPointer);
            nextPointer.previous(previousPointer);
        }
    }

    private Stream<KeyValueNode<K, V>> allNodesStream() {
        ArrayList<KeyValueNode<K, V>> nodes = new ArrayList<>();
        KeyValueNode<K, V> tempPointer = this.head;
        while (tempPointer != null) {
            nodes.add(tempPointer);
            tempPointer = tempPointer.next();
        }
        return nodes.stream();
    }

    void add(K key, V value) {
        KeyValueNode<K, V> newKeyValueNode = new KeyValueNode<>(key, value);
        removeNodeWithKeyIfExists(key);
        insertAtBeginning(newKeyValueNode);
    }

    V fetchValueInNodeWithKey(K key, V defaultValue) {
        KeyValueNode<K, V> node = fetchNodeWithKey(key);
        if (node == null)
            return defaultValue;
        return node.value();
    }

    V fetchValueInNodeWithKey(K key) throws KeyNotFoundException {
        KeyValueNode<K, V> node = fetchNodeWithKey(key);
        if (node == null)
            throw new KeyNotFoundException(key.toString());
        return node.value();
    }

    boolean removeNodeWithKeyIfExists(K key) {
        KeyValueNode<K, V> nodeToDelete = this.fetchNodeWithKey(key);
        if (nodeToDelete == null)
            return false;

        deleteNode(nodeToDelete);
        return true;
    }

    KeyValuePojo<K, V> getPojoOfNodeAtIndex(int index) {
        if (index >= this.length())
            return null;
        KeyValueNode<K, V> tempPointer = this.head;
        for (int i = 0; i < index; i++) {
            tempPointer = tempPointer.next();
        }
        return tempPointer.pojo();
    }

    int length() {
        return numberOfNodes;
    }

    boolean isEmpty() {
        return this.length() == 0;
    }

    Stream<K> getAllKeys() {
        return allNodesStream().map(KeyValueNode::key);
    }

    Stream<V> getAllValues() {
        return allNodesStream().map(KeyValueNode::value);
    }

    Stream<KeyValuePojo<K, V>> getAllPojo() {
        return allNodesStream().map(node -> new KeyValuePojo<>(node.key(), node.value()));
    }
}
