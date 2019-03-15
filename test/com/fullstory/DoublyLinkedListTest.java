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
    private final String key1 = "key1";
    private final String key2 = "key2";
    private final String key3 = "key3";
    private final String value1 = "value1";
    private final String value2 = "value2";
    private final String value3 = "value3";

    @Before
    @SuppressWarnings("unchecked")
    public void setup() {
        doublyLinkedList = new DoublyLinkedList<>();
    }

    @Test
    public void shouldAddAKeyValueNodeAtTheBeginningWhenTheListIsEmpty() {
        List<KeyValuePojo<String, String>> expectedLinkedList = Collections.singletonList(new KeyValuePojo<>(key1, value1));

        doublyLinkedList.add(key1, value1);

        Assert.assertEquals(expectedLinkedList, currentLinkedList());
    }

    @Test
    public void shouldAddAKeyValueNodeAtTheBeginningWhenTheListIsNotEmpty() {
        doublyLinkedList = linkedListOfSize(1);
        List<KeyValuePojo<String, String>> expectedLinkedList = Arrays.asList(new KeyValuePojo<>(key2, value2), new KeyValuePojo<>(key1, value1));

        doublyLinkedList.add(key2, value2);

        Assert.assertEquals(expectedLinkedList, currentLinkedList());
    }

    @Test
    public void shouldHoldTheUpdatedValueWhenAKeyIsAddedMoreThanOnce() {
        doublyLinkedList = linkedListOfSize(1);
        List<KeyValuePojo<String, String>> expectedLinkedList = Collections.singletonList(new KeyValuePojo<>(key1, value2));

        doublyLinkedList.add(key1, value2);

        Assert.assertEquals(expectedLinkedList, currentLinkedList());
    }

    @Test
    public void shouldRetrieveValueOfNodeWithGivenKeyWhenItExistsInTheList() {
        doublyLinkedList = linkedListOfSize(3);
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
        doublyLinkedList = linkedListOfSize(2);
        String absentKey = "absent key";
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
        doublyLinkedList = linkedListOfSize(1);
        String absentKey = "absent key";
        String defaultValue = "default";

        String nodeValue = doublyLinkedList.fetchValueInNodeWithKey(absentKey, defaultValue);

        Assert.assertEquals(defaultValue, nodeValue);
    }

    @Test
    public void shouldRemoveANodeWhenItExistsInTheList() {
        List<KeyValuePojo<String, String>> expectedPojoLoist = Arrays.asList(new KeyValuePojo<>(key3, value3),
                new KeyValuePojo<>(key1, value1));
        doublyLinkedList = linkedListOfSize(3);

        boolean removedSuccessfully = doublyLinkedList.removeNodeWithKeyIfExists(key2);

        Assert.assertEquals(expectedPojoLoist, currentLinkedList());
        Assert.assertTrue(removedSuccessfully);
    }

    @Test
    public void shouldReturnFalseWhenTryingToRemoveANodeThatIsNotInTheList() {
        doublyLinkedList = linkedListOfSize(3);
        String absentKey = "absentKey";

        boolean removedSuccessfully = doublyLinkedList.removeNodeWithKeyIfExists(absentKey);

        Assert.assertFalse(removedSuccessfully);
    }

    @Test
    public void shouldReturnThePojoOfNodeGivenIndex() {
        doublyLinkedList = linkedListOfSize(3);
        KeyValuePojo<String, String> expectedPojo = new KeyValuePojo<>(key2, value2);

        KeyValuePojo<String, String> actualPojo = doublyLinkedList.getPojoOfNodeAtIndex(1);

        Assert.assertEquals(expectedPojo, actualPojo);
    }

    @Test
    public void shouldReturnNullWhenNoNodeExistsAtGivenIndex() {
        doublyLinkedList = linkedListOfSize(2);

        KeyValuePojo<String, String> actualPojo = doublyLinkedList.getPojoOfNodeAtIndex(5);

        Assert.assertNull(actualPojo);
    }

    private List<KeyValuePojo<String, String>> currentLinkedList() {
        return doublyLinkedList.getAllPojo().collect(Collectors.toList());
    }

    private DoublyLinkedList<String, String> linkedListOfSize(int size) {
        KeyValueNode<String, String> head = null;
        switch (size) {
            case 1:
                head = createLinkedListFromTuples(Collections.singletonList(
                        new TupleForTests(key1, value1)));
                break;
            case 2:
                head = createLinkedListFromTuples(Arrays.asList(
                        new TupleForTests(key1, value1),
                        new TupleForTests(key2, value2)));
                break;
            case 3:
                head = createLinkedListFromTuples(Arrays.asList(
                        new TupleForTests(key1, value1),
                        new TupleForTests(key2, value2),
                        new TupleForTests(key3, value3)));
                break;
        }
        return new DoublyLinkedList<>(head, size);
    }
}
