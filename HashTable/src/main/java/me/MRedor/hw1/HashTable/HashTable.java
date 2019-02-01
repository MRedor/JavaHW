package me.MRedor.hw1.HashTable;


/**
 *    Collection of pairs (String key, String value).
 *    Access by key.
 * */

public class HashTable {
    private int size = 0;
    private MyList[] table = new MyList[2];

    /**
     *   Gets hashtable's size.
     *
     *   @return the number of different keys
     *   */
    public int size() {
        return size;
    }

    /**
     *    Checks existing of key in hashtable.
     *
     *    @return 'true' if hashtable contains the key 'key' and 'false' otherwise    */
    public boolean contains(String key) {
        MyList list = table[getHash(key)];
        if (list == null) {
            return false;
        } else {
            return list.contains(key);
        }
    }

    /**
     *    Gets value by key.
     *
     *    @return the value with the key 'key' in the hashtable or 'null' if there is no such element    */
    public String get(String key) {
        MyList list = table[ getHash(key) ];
        if (list == null) {
            return  null;
        }
        return list.getValue(key);
    }

    /**
     *     Puts the element with the key 'key' and the value 'value' in the hashtable (replacing the previous value).
     *
     *     @return the previous value of the key 'key'
     *     */
    public String put(String key, String value) {
        if (table.length == size) {
            rebuild();
        }
        String previous = null;
        if (contains(key)) {
            previous = get(key);
            remove(key);
        }
        int hash = getHash(key);
        if (table[hash] == null) {
            table[hash] = new MyList();
        }
        table[hash].put(key, value);
        size++;
        return previous;
    }

    /**
     *    Delete the element with the key 'key' in hashable.
     *
     *    @return the deleted value of the key 'key'
     * */
    public String remove(String key) {
        if (!contains(key)) {
            return null;
        }
        int hash = getHash(key);
        String value = table[hash].getValue(key);
        table[hash].remove(key);
        size--;
        return value;
    }

    /**    Removes all elements from hashtable.    */
    public void clear() {
        size = 0;
        table = new MyList[2];
    }

    /**    Calculates hash of string 'key'.    */
    private int getHash(String key) {
        return (Math.abs(key.hashCode()) % table.length);
    }

    /**    Builds new hashtable with double capacity.    */
    private void rebuild() {
        MyList[] old = table;
        table = new MyList[2 * size];
        size = 0;
        for (MyList list : old) {
            for (int i = 0; i < list.size(); i++) {
                String key = list.getKey(i);
                String value = list.getValue(key);
                put(key, value);
            }
        }
    }

}
