package mail.ru;

import java.lang.reflect.Array;
import java.util.*;

public class MyHashMap<K, V> implements SimpleMap<K, V> {

    private Node<K, V>[] table;
    private int size;

    static class Node<K, V> implements Map.Entry<K, V> {
        final int hash;
        final K key;
        V value;
        Node<K, V> next;

        public Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
            this.hash = key.hashCode();
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V value) {
            this.value = value;
            return value;
        }
    }

    @Override
    public V put(K key, V value) {
        if (size == 0) {
            this.table = (Node<K, V>[]) Array.newInstance(Node.class, 10);
            size = 10;
        }
        if (this.keySet().size() > this.size * 3 / 2) {
            this.resize_();
        }
        return this.targetPut_(key, value);
    }

    @Override
    public V get(K key) {
        int index = key.hashCode() & (size - 1);
        Node<K, V> temp = table[index];
        if (temp == null) {
            return null;
        }
        while (temp != null) {
            if (temp.hash == key.hashCode() && (temp.key == key || temp.key.equals(key))) {
                return temp.value;
            }
            temp = temp.next;
        }
        return null;
    }

    @Override
    public V remove(K key) {
        int index = key.hashCode() & (this.size - 1);
        Node<K, V> temp = this.table[index];
        V ret;
        if (temp == null) {
            return null;
        }
        if (temp.hash == key.hashCode() && (temp.key == key || temp.key.equals(key))) {
            ret = temp.value;
            this.table[index] = temp.next;
            return ret;
        }
        do {
            if (temp.next.hash == key.hashCode() && (temp.next.key == key || temp.next.key.equals(key))) {
                ret = temp.next.value;
                temp.next = temp.next.next;
                return ret;
            }
            temp = temp.next;
        }
        while (temp.next != null);
        return null;
    }

    @Override
    public boolean contains(K key) {
        int index = key.hashCode() & (size - 1);
        Node<K, V> temp = table[index];
        if (temp == null) {
            return false;
        }
        while (temp != null) {
            if (temp.hash == key.hashCode() && (temp.key == key || temp.key.equals(key))) {
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Set<K> keySet() {
        Set<K> ret = new HashSet<>();
        Node<K, V> temp;
        for (int index = 0; index < size; index++) {
            temp = table[index];
            while (temp != null) {
                ret.add(temp.key);
                temp = temp.next;
            }
        }
        return ret;
    }

    @Override
    public Set<V> values() {
        Set<V> ret = new HashSet<>();
        Node<K, V> temp;
        for (int index = 0; index < size; index++) {
            temp = table[index];
            while (temp != null) {
                ret.add(temp.value);
                temp = temp.next;
            }
        }
        return ret;
    }

    public Set<Node<K, V>> nodes() {
        Set<Node<K, V>> ret = new HashSet<>();
        Node<K, V> temp;
        for (int index = 0; index < size; index++) {
            temp = table[index];
            while (temp != null) {
                ret.add(temp);
                temp = temp.next;
            }
        }
        return ret;
    }

    private V targetPut_(K key, V value) {
        Node<K, V> newNode = new Node<>(key, value, null);
        int index = newNode.hash & (size - 1);
        Node<K, V> temp = this.table[index];
        if (temp == null) {
            this.table[index] = newNode;
            return value;
        }
        while (temp.next != null) {
            if (temp.hash == newNode.hash && (temp.key == newNode.key || temp.key.equals(newNode.key))) {
                temp.value = newNode.value;
                return value;
            }
            temp = temp.next;
        }
        if (temp.hash == newNode.hash && (temp.key == newNode.key || temp.key.equals(newNode.key))) {
            temp.value = newNode.value;
            return value;
        }
        temp.next = newNode;
        return value;
    }

    private void resize_() {
        Set<Node<K, V>> allNodes = this.nodes();
        this.table = (Node<K, V>[]) Array.newInstance(Node.class, this.size * 3 / 2);
        this.size = this.size * 3 / 2;
        for (Node<K, V> node : allNodes
        ) {
            this.targetPut_(node.key, node.value);
        }
    }

    //  Для себя писал
    public void print() {
        Node<K, V> temp;
        for (int index = 0; index < size; index++) {
            temp = table[index];
            if (temp != null) {
                System.out.print("index " + index + ": \n");
                while (temp != null) {
                    System.out.print(temp.key + " ");
                    temp = temp.next;
                }
                System.out.println();
            }
        }
    }
}
