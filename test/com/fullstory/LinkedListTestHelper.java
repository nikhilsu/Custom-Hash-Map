package com.fullstory;

import java.util.List;

class TupleForTests {
    private final String key;
    private final String value;

    TupleForTests(String key, String value) {
        this.key = key;
        this.value = value;
    }

    String getKey() {
        return key;
    }

    String getValue() {
        return value;
    }
}

class LinkedListTestHelper {
    static KeyValueNode<String, String> createLinkedListFromTuples(List<TupleForTests> tuples) {
        KeyValueNode<String, String> head = null;

        for (int i = 0; i < tuples.size(); i++) {
            TupleForTests tuple = tuples.get(i);
            KeyValueNode<String, String> node = new KeyValueNode<>(tuple.getKey(), tuple.getValue());
            if (i == 0) {
                head = node;
                continue;
            }
            node.next(head);
            head.previous(node);
            head = node;
        }
        return head;
    }
}
