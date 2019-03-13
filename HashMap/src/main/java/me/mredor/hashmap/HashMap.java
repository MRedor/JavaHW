package me.mredor.hashmap;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Map collecting pairs (K key, V value)
 *
 * @param <K> a key type
 * @param <V> a value type
 */
public class HashMap<K, V> implements Map<K,V> {
    private int size = 0;
    private static final int CAPACITY = 2;
    private int capacity;
    private MyList<K,V>[] map;
    private MyList<K,V> order;
    private MySet<K> keySet;
    private MyCollection<V> valueCollection;


    /** Creates a HashTable, base capacity is 4 */
    public HashMap() {
        keySet = new MySet<K>();
        valueCollection = new MyCollection<V>();
        capacity = CAPACITY;
        map = new MyList[capacity];
        order = new MyList<>();
    }


    /** Gets size of map */
    @Override
    public int size() {
        return size;
    }

    /** Checks if map is empty */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /** Checks if map contains the element with the key 'key' */
    @Override
    public boolean containsKey(Object key) {
        return map[getHash(key)] != null && map[getHash(key)].contains((K) key);
    }

    /**  Checks if map contains the element with the value 'value' */
    @Override
    public boolean containsValue(Object value) {
        for (var current : map) {
            if (current != null && current.containsValue((V) value)) {
                return true;
            }
        }
        return false;
    }

    /** Gets the element with the key 'key' */
    @Override
    public V get(Object key) {
        var hash = getHash(key);
        if (map[hash] == null) {
            return null;
        }

        Pair<K,V> found = map[hash].get((K) key);
        return found == null ? null : found.getValue();
    }

    /** Puts the element with the key 'key' and the value 'value' */
    @Override
    public V put(K key, V value) {
        var hash = getHash(key);
        if (map[hash] == null) {
            map[hash] = new MyList<K, V>();
        }
        var current = map[hash];
        var previous = current.remove(key);
        current.put(key, value);
        if (previous == null) {
            size++;
            keySet.add(key);
            valueCollection.add(value);
        }
        if (size * CAPACITY >= capacity) {
            rebuild();
        }
        order.put(key, value);
        return previous == null ? null : previous.getValue();
    }

    /** Removes the element from map */
    @Override
    public V remove(Object key) {
        var hash = getHash(key);
        if (map[hash] == null) {
            return null;
        }
        Pair<K, V> element = map[hash].remove((K) key);
        if (element != null) {
            size--;
            keySet.remove(key);
            valueCollection.remove(element.getValue());
        }
        return element == null ? null : element.getValue();
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        for (Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    /** Clears the map */
    @Override
    public void clear() {
        keySet = new MySet<K>();
        for (MyList<K, V> list : map) {
            if (list == null) {
                continue;
            }
            list.clear();
        }
        size = 0;
    }

    /** Gets set of keys */
    @Override
    public Set<K> keySet() {
        return keySet;
    }

    /** Gets the collection of values */
    @Override
    public Collection<V> values() {
        return valueCollection;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return new Set<Entry<K, V>>() {
            @Override
            public int size() {
                return size;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<Entry<K, V>> iterator() {
                return new HashMapIterator();
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(Entry<K, V> kvEntry) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends Entry<K, V>> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }
        };
    }

    /**    Calculates hash of string 'key'.    */
    private int getHash(Object key) {
        return (Math.abs(key.hashCode()) % capacity);
    }

    private class HashMapIterator implements Iterator<Entry<K, V>> {
        MyList<K,V>.Node current = order.begin;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Entry<K, V> next() {
            Pair<K, V> element = current.getData();
            current = current.getNext();
            if (!get(element.getKey()).equals(element.getValue())) {
                return next();
            }
            return element;
        }
    }

    private void rebuild() {
        MyList<K, V>[] old = map;
        map = new MyList[capacity * CAPACITY];
        capacity = map.length;

        for (var current : old) {
            if (current == null) {
                continue;
            }
            for (var i = current.begin; i != null; i = i.getNext()) {
                Pair<K, V> element = i.getData();
                int hash = getHash(element.getKey());
                if (map[hash] == null) {
                    map[hash] = new MyList<K, V>();
                }
                map[hash].put(element.getKey(), element.getValue());
            }
        }
    }

}
