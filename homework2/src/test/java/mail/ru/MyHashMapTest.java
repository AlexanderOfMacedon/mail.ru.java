package mail.ru;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.Assert.*;

public class MyHashMapTest {

    @Test
    public void stressTest() {
        MyHashMap<String, Integer> myMap = new MyHashMap<>();
        for (int i = 0; i < 100; i++) {
            myMap.put("key" + i % 23, i);
        }
        HashMap<String, Integer> stdMap = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            stdMap.put("key" + i % 23, i);
        }
//       сравниваем реализованный и стандартный hashmap
        for (int i = 0; i < 100; i++) {
            Assert.assertEquals(myMap.get("key" + i % 23), stdMap.get("key" + i % 23));
        }
        for (int i = 0; i < 10; i++) {
            myMap.remove("key" + i % 23);
            stdMap.remove("key" + i % 23);
        }
        for (int i = 0; i < 100; i++) {
            Assert.assertEquals(myMap.get("key" + i % 23), stdMap.get("key" + i % 23));
        }
    }

    @Test
    public void containsTest() {
        MyHashMap<String, Integer> myMap = new MyHashMap<>();
        for (int i = 0; i < 100; i++) {
            myMap.put("key" + i % 23, i);
        }
        for (int i = 0; i < 100; i++) {
            Assert.assertTrue(myMap.contains("key" + i % 23));
        }
    }

    @Test
    public void keySet() {
        HashSet<String> actualSet = new HashSet<>();
        HashSet<String> mySet;
        MyHashMap<String, Integer> myMap = new MyHashMap<>();
        for (int i = 0; i < 100; i++) {
            myMap.put("key" + i % 23, i);
            actualSet.add("key" + i % 23);
        }
        mySet = (HashSet<String>) myMap.keySet();
        Assert.assertEquals(mySet, actualSet);
    }

    @Test
    public void values() {
        HashMap<String, Integer> stdMap = new HashMap<>();
        MyHashMap<String, Integer> myMap = new MyHashMap<>();
        for (int i = 0; i < 100; i++) {
            myMap.put("key" + i % 23, i);
            stdMap.put("key" + i % 23, i);
        }
        for (Integer value : myMap.values()) {
            Assert.assertTrue(stdMap.containsValue(value));
        }
    }
}