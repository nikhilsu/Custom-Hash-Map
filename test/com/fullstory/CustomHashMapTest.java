package com.fullstory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

public class CustomHashMapTest {

    private CustomHashMap<String, String> customHashMap;
    private DoublyLinkedList<String, String> bucketZeroMock;
    private DoublyLinkedList<String, String> bucketOneMock;
    private final String keyWithHashZero = "key1";
    private final String keyWithHashOne = "key2";

    @Before
    @SuppressWarnings("unchecked")
    public void setup() {
        int BUCKET_CAPACITY = 2;
        DoublyLinkedList<String, String>[] buckets = new DoublyLinkedList[BUCKET_CAPACITY];
        bucketZeroMock = mock(DoublyLinkedList.class);
        bucketOneMock = mock(DoublyLinkedList.class);
        buckets[0] = bucketZeroMock;
        buckets[1] = bucketOneMock;

        customHashMap = new CustomHashMap<>(buckets, BUCKET_CAPACITY);

        Assert.assertEquals(keyWithHashZero.hashCode() % BUCKET_CAPACITY, 0);
        Assert.assertEquals(keyWithHashOne.hashCode() % BUCKET_CAPACITY, 1);
    }

    @Test
    public void shouldAddKeyAndItsValueToHashMapWhenThePutMethodIsInvoked() {
        String value = "value";

        customHashMap.put(keyWithHashOne, value);

        verify(bucketOneMock, times(1)).add(keyWithHashOne, value);
        verify(bucketZeroMock, never()).add(any(), any());
    }

    @Test
    public void shouldRetrieveValueGivenKeyInHashMapWhenTheGetMethodIsInvoked() throws KeyNotFoundException {
        String expectedValue = "Some value";
        when(bucketZeroMock.fetchValueInNodeWithKey(keyWithHashZero)).thenReturn(expectedValue);

        String actualValue = customHashMap.get(keyWithHashZero);

        Assert.assertEquals(expectedValue, actualValue);
        verify(bucketZeroMock, times(1)).fetchValueInNodeWithKey(keyWithHashZero);
        verifyNoMoreInteractions(bucketOneMock);
    }

    @Test
    public void shouldRetrieveDefaultalueGivenKeyNotInHashMapWhenTheGetMethodIsInvoked() {
        String defaultValue = "Some value";
        when(bucketOneMock.fetchValueInNodeWithKey(keyWithHashOne, defaultValue)).thenReturn(defaultValue);

        String actualValue = customHashMap.get(keyWithHashOne, defaultValue);

        Assert.assertEquals(defaultValue, actualValue);
        verify(bucketOneMock, times(1)).fetchValueInNodeWithKey(keyWithHashOne, defaultValue);
        verifyNoMoreInteractions(bucketZeroMock);
    }

    @Test
    public void shouldRemoveAKeyGivenKeyInHashMapWhenTheRemoveMethodIsInvoked() {
        when(bucketZeroMock.removeNodeWithKeyIfExists(keyWithHashZero)).thenReturn(true);
        boolean exceptionThrown = false;

        try {
            customHashMap.remove(keyWithHashZero);
        } catch (KeyNotFoundException e) {
            exceptionThrown = true;
        }

        Assert.assertFalse(exceptionThrown);
        verify(bucketZeroMock, times(1)).removeNodeWithKeyIfExists(keyWithHashZero);
        verifyNoMoreInteractions(bucketOneMock);
    }

    @Test
    public void shouldThrownExceptionWhenRemovingAKeyNotInHashMapWhenTheRemoveMethodIsInvoked() {
        when(bucketZeroMock.removeNodeWithKeyIfExists(keyWithHashZero)).thenReturn(false);
        boolean exceptionThrown = false;

        try {
            customHashMap.remove(keyWithHashZero);
        } catch (KeyNotFoundException e) {
            exceptionThrown = true;
        }

        Assert.assertTrue(exceptionThrown);
        verify(bucketZeroMock, times(1)).removeNodeWithKeyIfExists(keyWithHashZero);
        verifyNoMoreInteractions(bucketOneMock);
    }

    @Test
    public void shouldReturnTrueWhenAKeyIsPresentInHashMap() {
        when(bucketOneMock.isEmpty()).thenReturn(false);

        boolean keyPresent = customHashMap.isPresent(keyWithHashOne);

        Assert.assertTrue(keyPresent);
    }

    @Test
    public void shouldReturnFalseWhenAKeyIsNotPresentInHashMap() {
        when(bucketOneMock.isEmpty()).thenReturn(true);

        boolean keyPresent = customHashMap.isPresent(keyWithHashOne);

        Assert.assertFalse(keyPresent);
    }

    @Test
    public void shouldReturnSetOfAllKeysInTheHashMap() {
        when(bucketZeroMock.isEmpty()).thenReturn(false);
        when(bucketZeroMock.getAllKeys()).thenReturn(Stream.of(keyWithHashZero));
        when(bucketOneMock.isEmpty()).thenReturn(false);
        when(bucketOneMock.getAllKeys()).thenReturn(Stream.of(keyWithHashOne));
        HashSet<String> expectedKeySet = new HashSet<>(Arrays.asList(keyWithHashOne, keyWithHashZero));

        Set<String> allKeys = customHashMap.keys();

        Assert.assertEquals(expectedKeySet, allKeys);
    }

    @Test
    public void shouldReturnListOfAllValuesInTheHashMap() {
        String value1 = "value1";
        String value2 = "value2";
        when(bucketZeroMock.isEmpty()).thenReturn(false);
        when(bucketZeroMock.getAllValues()).thenReturn(Stream.of(value1));
        when(bucketOneMock.isEmpty()).thenReturn(false);
        when(bucketOneMock.getAllValues()).thenReturn(Stream.of(value2));
        List<String> expectedListOfValues = Arrays.asList(value1, value2);

        List<String> allValues = customHashMap.values();

        Assert.assertEquals(expectedListOfValues, allValues);
    }

    @Test
    public void shouldReturnAllKeyValuesInTheHashMapAsListOfPojo() {
        KeyValuePojo<String, String> pojo1 = new KeyValuePojo<>(keyWithHashZero, "value1");
        KeyValuePojo<String, String> pojo2 = new KeyValuePojo<>(keyWithHashOne, "value2");
        when(bucketZeroMock.isEmpty()).thenReturn(false);
        when(bucketZeroMock.getAllPojo()).thenReturn(Stream.of(pojo1));
        when(bucketOneMock.isEmpty()).thenReturn(false);
        when(bucketOneMock.getAllPojo()).thenReturn(Stream.of(pojo2));
        List<KeyValuePojo<String, String>> expectedListOfPojo = Arrays.asList(pojo1, pojo2);

        List<KeyValuePojo<String, String>> keyValuePojo = customHashMap.keyValuePojo();

        Assert.assertEquals(expectedListOfPojo, keyValuePojo);
    }
}
