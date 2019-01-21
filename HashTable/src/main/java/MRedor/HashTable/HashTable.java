package MRedor.HashTable;

import java.util.stream.Stream;

public class HashTable {

    private int size = 0;
    private MyList[] table = new MyList[2];


    /**    @return the number of different keys    */
    public int size() {
        return size;
    }

    /**    @return 'true' if hashtable contains the key 'key' and 'false' otherwise    */
    public boolean contains(String key) {
        MyList list = table[ getHash(key) ];
        if (list == null) {
            return false;
        } else {
            return list.contains(key);
        }

    }


    /**    @return the value with the key 'key' in the hashtable or 'null' if there is no such element    */
    public String get(String key) {
        MyList list = table[ getHash(key) ];
        if (list == null) {
            return  null;
        }
        return list.getValue(key);
    }

    /**
     *     puts the element with the key 'key' and the value 'value' in the hashtable (replacing the previous value)
     *
     *     @return the previous value of the key 'key'
     *
     *     */
    public String put(String key, String value) {
        if (table.length == size) {
            rebuild();
        }
        String prev = null;
        if (contains(key)) {
            prev = get(key);
            remove(key);
        }
        int hs = getHash(key);
        if (table[hs] == null) {
            table[hs] = new MyList();
        }
        table[hs].put(key, value);
        size++;

        return prev;
    }


    /**
     *    delete the element with the key 'key' in hashable
     *
     *    @return the deleted value of the key 'key'
     *
     * */
    public String remove(String key) {
        if (!contains(key)) {
            return null;
        }
        int hs = getHash(key);
        String val = table[hs].getValue(key);
        table[hs].remove(key);
        size--;
        return val;
    }


    /**    removes all elements from hashtable    */
    public void clear() {
        size = 0;
        table = new MyList[2];
    }

    /**    calculates hash of string 'key'    */
    private int getHash(String key) {
        return ( (key.hashCode() + table.length) % table.length );
    }


    /**    builds new hashtable with double capacity    */
    private void rebuild() {
        MyList[] old = table;
        table = new MyList[2*size];
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
