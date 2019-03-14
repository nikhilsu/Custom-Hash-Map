package com.fullstory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.fullstory.LinkedListTestHelper.createLinkedListFromTuples;


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
        doublyLinkedList = new DoublyLinkedList<>(createLinkedListFromTuples(Collections.singletonList(new TupleForTests(key1, value1))));

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
        doublyLinkedList = new DoublyLinkedList<>(createLinkedListFromTuples(Collections.singletonList(new TupleForTests(key, value1))));
        String value2 = "value2";
        List<KeyValuePojo<String, String>> expectedLinkedList = Collections.singletonList(new KeyValuePojo<>(key, value2));

        doublyLinkedList.add(key, value2);

        Assert.assertEquals(currentLinkedList(), expectedLinkedList);
    }

    @Test
    public void shouldRetrieveValueOfNodeWithGivenKeyWhenItExistsInTheList() {
        String key1 = "key1";
        String key2 = "key2";
        String key3 = "key3";
        String value1 = "value1";
        String value2 = "value2";
        String value3 = "value3";
        KeyValueNode<String, String> head = createLinkedListFromTuples(Arrays.asList(new TupleForTests(key1, value1), new TupleForTests(key2, value2), new TupleForTests(key3, value3)));
        doublyLinkedList = new DoublyLinkedList<>(head);
        boolean exceptionOccurred = false;
        String nodeValue = null;

        try {
            nodeValue = doublyLinkedList.fetchValueInNodeWithKey(key2);
        } catch (KeyNotFoundException e) {
            exceptionOccurred = true;
        }

        Assert.assertFalse(exceptionOccurred);
        Assert.assertEquals(value2, nodeValue);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void shouldThrowExceptionWhenTryingToRetrieveValueOfNodeWithGivenAbsentKey() {
        String key1 = "key1";
        String key2 = "key2";
        String value1 = "value1";
        String value2 = "value2";
        String absentKey = "absent key";

        KeyValueNode<String, String> head = createLinkedListFromTuples(Arrays.asList(new TupleForTests(key1, value1), new TupleForTests(key2, value2)));
        doublyLinkedList = new DoublyLinkedList<>(head);
        boolean exceptionOccurred = false;
        String nodeValue = null;

        try {
            nodeValue = doublyLinkedList.fetchValueInNodeWithKey(absentKey);
        } catch (KeyNotFoundException e) {
            exceptionOccurred = true;
        }

        Assert.assertTrue(exceptionOccurred);
        Assert.assertNull(nodeValue);
    }

    @Test
    public void shouldReturnDefaultValueWhenTryingToRetrieveValueOfNodeWithGivenAbsentKey() {
        String key1 = "key1";
        String value1 = "value1";
        String absentKey = "absent key";

        KeyValueNode<String, String> head = createLinkedListFromTuples(Collections.singletonList(new TupleForTests(key1, value1)));
        doublyLinkedList = new DoublyLinkedList<>(head);
        String defaultValue = "default";

        String nodeValue = doublyLinkedList.fetchValueInNodeWithKey(absentKey, defaultValue);

        Assert.assertEquals(defaultValue, nodeValue);
    }

    private List<KeyValuePojo<String, String>> currentLinkedList() {
        return doublyLinkedList.getAllPojo().collect(Collectors.toList());
    }
}
