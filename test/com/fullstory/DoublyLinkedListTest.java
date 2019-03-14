package com.fullstory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DoublyLinkedListTest {
    private DoublyLinkedList<String, String> doublyLinkedList;

    @Before
    @SuppressWarnings("unchecked")
    public void setup() {
        doublyLinkedList = new DoublyLinkedList<>();
    }

    @Test
    public void shouldAddAKeyValueNodeAtTheBeginningWhenTheListIsEmpty() {
        String key = "key";
        String value = "value";
        List<KeyValuePojo<String, String>> expectedLinkedList = Collections.singletonList(new KeyValuePojo<>(key, value));

        doublyLinkedList.add(key, value);

        Assert.assertEquals(currentLinkedList(), expectedLinkedList);
    }

    @Test
    public void shouldAddAKeyValueNodeAtTheBeginningWhenTheListIsNotEmpty() {
        String key1 = "key1";
        String value1 = "value1";
        doublyLinkedList = new DoublyLinkedList<>(new KeyValueNode<>(key1, value1));

        String key2 = "key2";
        String value2 = "value2";
        List<KeyValuePojo<String, String>> expectedLinkedList = Arrays.asList(new KeyValuePojo<>(key2, value2), new KeyValuePojo<>(key1, value1));

        doublyLinkedList.add(key2, value2);

        Assert.assertEquals(currentLinkedList(), expectedLinkedList);
    }

    @Test
    public void shouldHoldTheUpdatedValueWhenAKeyIsAddedMoreThanOnce() {
        String key = "key";
        String value1 = "value1";
        doublyLinkedList = new DoublyLinkedList<>(new KeyValueNode<>(key, value1));
        String value2 = "value2";
        List<KeyValuePojo<String, String>> expectedLinkedList = Collections.singletonList(new KeyValuePojo<>(key, value2));

        doublyLinkedList.add(key, value2);

        Assert.assertEquals(currentLinkedList(), expectedLinkedList);
    }

    private List<KeyValuePojo<String, String>> currentLinkedList() {
        return doublyLinkedList.getAllPojo().collect(Collectors.toList());
    }

}
