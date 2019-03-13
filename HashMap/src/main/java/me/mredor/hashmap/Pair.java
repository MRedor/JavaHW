package me.mredor.hashmap;

import java.util.Map;

public class Pair<K, V> implements Map.Entry<K, V>{
    private K key;
    private V value;

    public Pair() {
    }

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        V temp = this.value;
        this.value = value;
        return temp;
    }
}